package net.ircapp;


import java.io.IOException;
import java.util.ArrayList;

import net.ircapp.db.Database;
import net.ircapp.model.Server;
import android.content.Context;
import android.database.Cursor;

public class IRCApp 
{

	public static IRCApp instance;
	
	private ArrayList<Server> connectedServers;
	private Database globalDB;
	private boolean dbInitialized = false;
	
	/**
	 * Constructor
	 */
	public IRCApp()
	{
		this.connectedServers = new ArrayList<Server>();
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
		this.dbInitialized = true;
	}
	
	public boolean isDBInitialized()
	{
		return this.dbInitialized;
	}
	
	public void setDBInit(boolean b)
	{
		this.dbInitialized = b;
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
		Cursor c = this.globalDB.getServer(id);
		
		Server s = new Server(c.getInt(c.getColumnIndex(Database.KEY_ID)),
				c.getString(c.getColumnIndex(Database.SERVERS_TITLE)),
				c.getString(c.getColumnIndex(Database.SERVERS_ADDRESS)),
				c.getInt(c.getColumnIndex(Database.SERVERS_PORT)),
				c.getString(c.getColumnIndex(Database.SERVERS_PASSWORD)),
				c.getString(c.getColumnIndex(Database.SERVERS_NICK)));

		return s;
	}
	
	public void removeServerByID(int id)
	{
		this.globalDB.removeServer(id);
	}
	

	/**
	 * Add server to list.
	 * @param s
	 */
	public void addConnectedServer(Server s)
	{
		this.connectedServers.add(s);
	}
	
	/**
	 * Remove server from list.
	 * @param s
	 */
	public void removeConnectedServer(Server s)
	{
		System.out.println("Removing: " + s);
		this.connectedServers.remove(s);
		this.globalDB.removeServer(s.getServerID());
	}
	
	/**
	 * Get the list of servers.
	 * @return
	 */
	public ArrayList<Server> getConnectedServerList()
	{	
		return this.connectedServers;
	}
	
	/**
	 * Gets the total number of servers we're connected to
	 * @return
	 */
	public int getNumConnectedServers()
	{
		return this.connectedServers.size();
	}
	
	/**
	 * Gets the total number of servers in the serverlist table
	 * @return
	 */
	public long getTotalNumServers()
	{
		return this.globalDB.getNumRows(Database.SERVERLIST_TABLE);
	}

	/**
	 * Sends a message to a specific channel on a specific server
	 * @param servID
	 * @param channelName
	 * @param text
	 * @throws IOException
	 */
	public void sendText(int servID, String channelName, String text) throws IOException 
	{
		//Server s = this.getServerFromID(servID);
		Server s = this.getConnectedServer(servID);
		
		// null check
		
		if(s.isConnected())
		{
			s.sendTextToChannel(channelName, text);
		}
	}
	
	/**
	 * Gets a specific server that we're connected to via it's id
	 * @param serverid
	 * @return
	 */
	public Server getConnectedServer(int serverid)
	{
		for(Server s: this.connectedServers)
		{
			if(s.getServerID() == serverid+1)
				return s;
		}
		
		return null;
	}
}
