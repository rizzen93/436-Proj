package ircapp;

import ircapp.model.Server;

import java.util.ArrayList;

public class IRCApp 
{

	public static IRCApp instance;
	
	private ArrayList<Server> servers;
	
	public IRCApp()
	{
		this.servers = new ArrayList<Server>();
	}
	
	/**
	 * Add server to list.
	 * @param s
	 */
	public void addServer(Server s)
	{
		this.servers.add(s);
	}
	
	/**
	 * Remove server from list.
	 * @param s
	 */
	public void removeServer(Server s)
	{
		this.servers.remove(s);
	}
	
	/**
	 * Get the list of servers.
	 * @return
	 */
	public ArrayList<Server> getServerList()
	{
		return this.servers;
	}
}
