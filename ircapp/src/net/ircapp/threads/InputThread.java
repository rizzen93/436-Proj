package net.ircapp.threads;

import java.io.BufferedReader;
import java.io.IOException;

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
		
		System.out.println(message.length);
		
	}
	
}
