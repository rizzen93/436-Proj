package net.ircapp.model;

import java.util.ArrayList;

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
	
	// channel stuffs
	private ArrayList<Channel> channels;
	
	// autojoin + connect commands?
	
	public Server()
	{
		this.channels = new ArrayList<Channel>();
		
		serverID = 1;
		serverTitle = "Freenode";
		serverHostname = "irc.freenode.net";
		serverPort = 6667;
		password = "";
		autoConnect = false;
		nickname = "rizztesting";
	}
	
	/*private Socket sock;
	
	public Server(String hostname, int port)
	{
		//Socket sock = new Socket(hostname, port);
	}*/
	
	public void setTitle(String t)
	{
		this.serverTitle = t;
	}
	
	// getters
	public int getServerID()
	{
		return this.serverID;
	}
	
	public String getServerTitle()
	{
		return this.serverTitle;
	}
	
	public String getServerHostname()
	{
		return this.getServerHostname();
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
	
	
	
}
