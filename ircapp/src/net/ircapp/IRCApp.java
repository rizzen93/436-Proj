package net.ircapp;


import java.util.ArrayList;

import net.ircapp.db.Database;
import net.ircapp.model.Server;
import android.content.Context;
import android.database.Cursor;

public class IRCApp 
{

	public static IRCApp instance;
	
	private ArrayList<Server> servers;
	private Cursor serversCursor;
	
	public IRCApp()
	{
		this.servers = new ArrayList<Server>();
	}
	
	/*public void loadServersFromDB(Context context)
	{
		Database db = new Database(context);
		this.serversCursor = db.getServerList();
		db.close();
	}*/
	
	public static IRCApp getInstance()
	{
		if(instance == null)
		{
			instance = new IRCApp();
		}
		
		return instance;
	}
	
	public Server getServer(Cursor c)
	{
		for(Server s : this.servers)
		{
			if(s.getServerTitle().equals(c.getString(c.getColumnIndex(Database.SERVERS_TITLE))))
			{
				return s;
			}
		}
		
		return null;
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
	public ArrayList<Server> getConnectedServersList()
	{	
		return this.servers;
	}
	
	public int getNumConnectedServers()
	{
		return this.servers.size();
	}
}
