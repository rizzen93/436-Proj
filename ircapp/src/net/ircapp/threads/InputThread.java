package net.ircapp.threads;

import java.io.BufferedReader;
import java.io.IOException;

import net.ircapp.IRCApp;
import net.ircapp.commands.Constants;
import net.ircapp.model.Server;

public class InputThread extends Thread
{

	private Server server;
	private BufferedReader breader;
	
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
		}
		
		String[] message = line.split(" ");
		
		/**
		 * ITS PARSING TIME!
		 */
		if(message.length == 4)
		{
			String stuff = message[0];
			String command = message[1];
			String target = message[2];
			String msg = message[3];
			
			// to deal with the sender's info
			String senderNick;
			String senderLogin;
			String senderHost;
			int ex = stuff.indexOf("!");
			int at = stuff.indexOf("@");
			
			// make sure the command is all proper
			command = command.toUpperCase();
			
			// deal with the sender info
			if(stuff.startsWith(":"))
			{
				senderNick = stuff.substring(1, ex);
				senderLogin = stuff.substring(ex + 1, at);
				senderHost = stuff.substring(at + 1);
				System.out.println(senderNick +" - " + senderLogin + " - " + senderHost);
			}
			

		}
		
	}
	
}
