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
	
	/**
	 * Constructor
	 */
	public IRCApp()
	{
		this.servers = new ArrayList<Server>();
		//this.globalDB = new Database(this);
	}
	
	/**
	 * Gets a global instance of the app
	 * @return
	 */
	public static IRCApp getInstance()
	{
		if(instance == null)
		{
			instance = new IRCApp();
		}
		
		return instance;
	}
	
	/**
	 * Initializes the DB
	 * @param context
	 */
	public void initDB(Context context)
	{
		this.globalDB = new Database(context);
		this.globalDB.open();
	}
	
	/**
	 * Gets DB for writing
	 * @return
	 */
	public Database getDB()
	{
		return this.globalDB;
	}
	
	/**
	 * Closes the writable db.
	 */
	public void closeDB()
	{
		this.globalDB.close();
	}
	
	/**
	 * Finds and returns a Server with the specified id
	 * @param id
	 * @return
	 */
	public Server getServerFromID(int id)
	{
		for(Server s : this.servers)
		{
			if(s.getServerID() == id)
			{
				return s;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Server getServerFromCursor(Cursor c)
	{
		for(Server s : this.servers)
		{
			if(s.getServerID() == c.getInt(c.getColumnIndex(Database.KEY_ID)))
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
	
	/**
	 * Gets the total number of servers in the list.
	 * @return
	 */
	public int getNumServers()
	{
		return this.servers.size();
	}
}
