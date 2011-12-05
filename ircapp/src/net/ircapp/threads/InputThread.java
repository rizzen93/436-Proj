package net.ircapp.threads;

import java.io.BufferedReader;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;

import net.ircapp.IRCApp;
import net.ircapp.commands.Constants;
import net.ircapp.model.Server;

public class InputThread extends Thread
{

	private Server server;
	private BufferedReader breader;
	private Handler mHandler = new Handler();
	
	public InputThread(Server s)
	{
		System.out.println("New InputThread for: " + s.getServerTitle());
		this.server = s;
		this.breader = s.getReader();
		
		
		this.start();
	}
	
	/**
	 * listen for input
	 */
	public void run()
	{
		try
		{
			System.out.println("Now listening for input from: " + this.server.getServerHostname());
			// while we got input
			String line = null;
			while((line = this.breader.readLine()) != null)
			{
				// deal with it
				this.handleLine(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the line we've received, to send to where it needs to go
	 * @param line
	 * @throws IOException
	 */
	public void handleLine(String line) throws IOException
	{
		System.out.println(line);
		
		// ping pong stuff
		if(line.startsWith(Constants.server_ping))
		{
			this.server.sendPong(Constants.server_pong + line.substring(5));
			return;
		}
		
		String[] message = line.split(" ");
		
		/**
		 * ITS PARSING TIME!
		 */
		if(message.length > 3)
		{
			String senderInfo = message[0];
			String command = message[1];
			String target = message[2];
			String msg = "";
			
			// deal with the colons
			if(target.startsWith(":"))
				target = target.substring(1);
			
			if(message[3].startsWith(":"))
				message[3] = message[3].substring(1);
			
			// reconstruct the message
			for(int i = 3; i < message.length; i++)
				msg += message[i] + " ";
			
			
			// to deal with the sender's info
			String[] senderNick;
			String senderLogin;
			String senderHost;
			
			// characters we need to know about
			int ex = senderInfo.indexOf("!");
			int at = senderInfo.indexOf("@");
				
			// make sure the command is all proper
			command = command.toUpperCase();
				
			// deal with the sender info
			if(senderInfo.startsWith(":"))
			{
				senderNick = senderInfo.split("!");
				String nick = senderNick[0];
				//senderLogin = senderInfo.substring(ex + 1, at);
				//senderHost = senderInfo.substring(at + 1);
				//System.out.println(senderNick +" - " + senderLogin + " - " + senderHost);
					
				// remove the crap from the beginning of the strings.
				if(nick.startsWith(":"))
					nick = nick.substring(1);
			}
			
			// deal with the commands
			this.dealWithIt(command, target, msg);
		}
		
		
	}
	
	public void dealWithIt(String command, String target, String msg)
	{
		if(command.equals("PRIVMSG"))
		{
			
			// we've got a message to display
			System.out.println(command + " " + target + " " + msg);
			//IRCApp.getInstance().getCurrentChatActivity().updateLog(msg);
			Message m = new Message();
			m.obj = msg;
			
			mHandler.sendMessage(m);
			
			//mHandler.sendMessage(msg);
			
		}
		else if(command.equals("JOIN"))
		{
			
		}
		else if(command.equals("PART"))
		{
			
		}
	}
		
}

