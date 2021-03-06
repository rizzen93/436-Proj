package net.ircapp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import net.ircapp.commands.Constants;
import net.ircapp.threads.InputThread;

public class Server 
{

	// server attributes
	private int serverID;
	private String serverTitle;
	private String serverHostname;
	private int serverPort;
	private String password;
	private boolean autoConnect;
	private String nickname;
	
	private boolean isConnected;
	
	// channel stuffs?
	private ArrayList<String> channels;
	
	// connection stuffs
	private Socket socket;
	private BufferedReader breader;
	private BufferedWriter bwriter;
	private String end = "\n\r";
	
	private InputThread inputThread;
	private ArrayList<String> messages;
	
	// autojoin + connect commands?
	
	/**
	 * Server Constructor
	 * @param id Server ID
	 * @param title Title of the server object
	 * @param hostname Hostname for the server.
	 * @param port Port to connect the server on (6666-8)
	 * @param password password for when connecting?
	 * @param nick default nickname for the server
	 */
	public Server(int id, String title, String hostname, int port, String password, String nick)
	{
		this.channels = new ArrayList<String>();
		this.messages = new ArrayList<String>();
		
		this.serverID = id;
		this.serverTitle = title;
		this.serverHostname = hostname;
		this.serverPort = port;
		this.password = password;
		autoConnect = false;
		this.nickname = nick;
		this.isConnected = false;
		
		System.out.println("New Server: " + this.toString());
	}
	
	/**
	 * Connect to the server.
	 * @return
	 * @throws IOException
	 */
	public boolean connect() throws IOException
	{
		this.socket = new Socket(this.serverHostname, this.serverPort);
	
		System.out.println("Connecting over: " + this.socket);
		
		InputStreamReader in = new InputStreamReader(this.socket.getInputStream());
		OutputStreamWriter out = new OutputStreamWriter(this.socket.getOutputStream());
		
		this.breader = new BufferedReader(in);
		this.bwriter = new BufferedWriter(out);
		
		// NICK <username>\n\r
		this.bwriter.write(Constants.server_nick + this.nickname + this.end);
		
		// USER <username> <hostname> <servername> :<realname>\n\r
		this.bwriter.write(Constants.server_user + this.nickname + " 8 * :" + this.nickname + this.end);
		this.bwriter.flush();
		
		// find out if we're connected!
		String line = null;
		while((line = breader.readLine()) != null)
		{
			//System.out.println(line);
			if(line.indexOf("004") >= 0)
			{
				// means we're 'logged in' and connected to the server
				this.isConnected = true;
				break;
			}
			
			if(line.startsWith(Constants.server_ping))
			{
				// reply PONG
				this.sendPong(Constants.server_pong + line.substring(5));
				
				/*
				 * for servers that require you to pong back before accepting any more input
				 */
				
				// NICK <username>\n\r
				this.bwriter.write(Constants.server_nick + this.nickname + this.end);
				
				// USER <username> <hostname> <servername> :<realname>\n\r
				this.bwriter.write(Constants.server_user + this.nickname + " 8 * :" + this.nickname + this.end);
				this.bwriter.flush();
			}
			
			// checks here for nick in use, etc
			// return false
		}
		
		this.inputThread = new InputThread(this);
		
		this.isConnected = true;
		
		return true;
	}
	
	public void addMessage(String msg)
	{
		this.messages.add(msg);
	}
	
	public void removeMessage(String msg)
	{
		this.messages.remove(msg);
	}
	
	public ArrayList<String> getMessages()
	{
		return this.messages;
	}
	
	public boolean isConnected()
	{
		return this.isConnected;
	}
	
	public void sendTextToChannel(String channelName, String text) throws IOException
	{
		//System.out.println("Sending: " + Constants.send_message + " " + channelName + " :" + text);
		this.bwriter.write(Constants.send_message + " " + channelName + " :" + text + this.end);
		this.bwriter.flush();
	}
	
	public void disconnect() throws IOException
	{
		//System.out.println("removing connection to: " + this.socket);
			
		this.bwriter.write(Constants.server_quit + " :Disconnecting " + this.end);
		this.socket.shutdownInput();
		this.socket.shutdownOutput();
			
		this.bwriter.close();
		this.breader.close();
		//this.socket.close();
		
		this.isConnected = false;
	}
	
	public void joinChannel(String channel) throws IOException
	{
		//System.out.println("SERVER: " + this.serverID + " joining "+ channel);
		this.bwriter.write(Constants.join_channel + channel + this.end);
		this.bwriter.flush();
	}
	
	// part channel
	public void leaveChannel(String channel) throws IOException
	{
		//System.out.println("leaving channel: " + channel);
		this.bwriter.write(Constants.part_channel + channel + this.end);
		this.bwriter.flush();
	}
	
	public void addChannel(String channelName)
	{
		this.channels.add(channelName);
	}
	
	public void removeChannel(String channelName)
	{
		this.channels.remove(channelName);
	}
	
	public ArrayList<String> getChannels()
	{
		return this.channels;
	}
	
	public void sendPong(String pong) throws IOException
	{
		this.bwriter.write(pong);
		this.bwriter.flush();
	}
	
	public BufferedReader getReader()
	{
		return this.breader;
	}
	
	public void setTitle(String t)
	{
		this.serverTitle = t;
	}
	
	public int getServerID()
	{
		return this.serverID;
	}
	
	public void setID(int id)
	{
		this.serverID = id;
	}
	
	public String getServerTitle()
	{
		return this.serverTitle;
	}
	
	public String getServerHostname()
	{
		return this.serverHostname;
	}
	
	public int getServerPort()
	{
		return this.serverPort;
	}
	
	public String getServerPassword()
	{
		return this.password;
	}
	
	public boolean getAutoConnect()
	{
		return this.autoConnect;
	}
	
	public String getNickname()
	{
		return this.nickname;
	}
	
	public void setNickname(String newNick)
	{
		this.nickname = newNick;
	}
	
	public String toString()
	{
		return "[ " + this.serverID + " ] --- [ server title - " + this.serverTitle + " ] --- [ server host - " + this.serverHostname + " ] --- [ server port - " 
				+ this.serverPort +	" ] --- [ server pass - " + this.password + " ] --- [ nickname - " + this.nickname + " ]";
	}

	public void clearMessageQueue() 
	{
		this.messages.clear();
	}
}
