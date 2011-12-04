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
	private Database globalDB;
	
	public IRCApp()
	{
		this.servers = new ArrayList<Server>();
		//this.globalDB = new Database(this);
	}
	
	public static IRCApp getInstance()
	{
		if(instance == null)
		{
			instance = new IRCApp();
		}
		
		return instance;
	}
	
	public void initDB(Context context)
	{
		this.globalDB = new Database(context);
		this.globalDB.open();
	}
	
	public Database getDB()
	{
		return this.globalDB;
	}
	
	public void closeDB()
	{
		this.globalDB.close();
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
		System.out.println("Removing: " + s);
		this.servers.remove(s);
		this.globalDB.removeServer(s.getServerID());
	}
	
	/**
	 * Get the list of servers.
	 * @return
	 */
	public ArrayList<Server> getServerList()
	{	
		return this.servers;
	}
	
	public int getNumServers()
	{
		return this.servers.size();
	}
}
