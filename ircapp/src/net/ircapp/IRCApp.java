package net.ircapp;


import java.util.ArrayList;

import net.ircapp.db.Database;
import net.ircapp.model.Server;

import android.content.Context;

public class IRCApp 
{

	public static IRCApp instance;
	
	private ArrayList<Server> servers;
	
	public IRCApp()
	{
		this.servers = new ArrayList<Server>();
	}
	
	public void loadServersFromDB(Context context)
	{
		Database db = new Database(context);
		this.servers = db.getServerList();
		db.close();
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