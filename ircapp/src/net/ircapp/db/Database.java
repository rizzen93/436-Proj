package net.ircapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class Database  
{

	// db stuff
	private static final String DATABASE_NAME = "ircservers.db";
	private static final int DATABASE_VERSION = 1;

	// table names
	public static final String SERVERLIST_TABLE  = "serverlist";
	public static final String CHANNELLIST_TABLE = "channellist";

	// serverlist attribute names // example
	public static final String SERVERS_TITLE = "title"; // Freenode
	public static final String SERVERS_ADDRESS = "hostname"; // irc.freenode.net
	public static final String SERVERS_PORT = "port"; // 6667
	public static final String SERVERS_PASSWORD = "password"; // optional, usually blank
	public static final String SERVERS_NICK = "nickname"; // rizzen
	public static final String SERVERS_AUTOCONNECT = "autoconnect"; // yes? no?

	// channellist attribute names
	public static final String CHANNELS_SERVER = "servertitle"; // means this channel belongs to whatever server was designated as being 'Freenode'
	public static final String CHANNELS_NAME = "channelname"; // #android
	public static final String CHANNELS_PASSWORD = "password"; 

	// keys
	public static final String KEY_ID = "_id";

    //private DatabaseHelper mDbHelper;
    private SQLiteDatabase db;
    private Context context;

	public Database(Context context)
	{
		this.context = context;
	}

	public Database open() throws SQLException
	{
		DatabaseHelper dbHelper = new DatabaseHelper(this.context);
		this.db = dbHelper.getWritableDatabase();

		return this;
	}

	public void close()
	{
		this.db.close();
	}

	/**
	 * Adds a server to SERVERLIST_TABLE
	 * @param title
	 * @param hostname
	 * @param port
	 * @param password
	 * @param nickname
	 * @param autoConnect
	 * @return
	 */
	public long addServer(String title, String hostname, int port, String password, String nickname, boolean autoConnect)
	{
		ContentValues values = new ContentValues();
		values.put(SERVERS_TITLE, title);
		values.put(SERVERS_ADDRESS, hostname);
		values.put(SERVERS_PORT, port);
		values.put(SERVERS_PASSWORD, password);
		values.put(SERVERS_NICK, nickname);
		values.put(SERVERS_AUTOCONNECT, autoConnect);

		return this.db.insert(SERVERLIST_TABLE, null, values);
	}

	/**
	 * Gets a cursor containing the server we want, I hope
	 * @param servertitle
	 * @return
	 */
	public Cursor getServer(String servertitle)
	{
		return db.query(SERVERLIST_TABLE, new String[] {SERVERS_TITLE, SERVERS_ADDRESS, SERVERS_PORT, SERVERS_PASSWORD, SERVERS_NICK}, 
				SERVERS_TITLE + "=" + servertitle, null, null, null, null);
	}

	/**
	 * Gets a cursor containing the channel we want
	 * @param serverTitle
	 * @param channelName
	 * @return
	 */
	public Cursor getChannel(String serverTitle, String channelName)
	{
		return db.query(CHANNELLIST_TABLE, new String[] {CHANNELS_SERVER, CHANNELS_NAME, CHANNELS_PASSWORD}, 
				CHANNELS_SERVER + "=" + serverTitle + ", " + CHANNELS_NAME + "=" + channelName, null, null, null, null);
	}

	/**
	 * Adds a channel to CHANNELLIST_TABLE
	 * @param serverTitle
	 * @param channelName
	 * @param channelPassword
	 * @return
	 */
	public long addChannel(String serverTitle, String channelName, String channelPassword)
	{
		ContentValues values = new ContentValues();
		values.put(CHANNELS_SERVER, serverTitle);
		values.put(CHANNELS_NAME, channelName);
		values.put(CHANNELS_PASSWORD, channelPassword);
		
		return this.db.insert(CHANNELLIST_TABLE, null, values);

	}

	public void updateChannel()
	{

	}

	public void updateServer()
	{

	}

	/**
	 * Returns a cursor containing all entries in the serverlist table
	 * @return
	 */
	public Cursor getServerList() 
	{
		return this.db.query(SERVERLIST_TABLE, new String[] {KEY_ID, SERVERS_TITLE, SERVERS_ADDRESS, SERVERS_PORT, SERVERS_PASSWORD, SERVERS_NICK, SERVERS_AUTOCONNECT}, 
				null, null, null, null, null);
	}

	/**
	 * Returns a cursor containing all entries in the channellist table
	 * @return
	 */
	public Cursor getChannelList()
	{
		return this.db.query(CHANNELLIST_TABLE, new String[] {KEY_ID, CHANNELS_SERVER, CHANNELS_NAME, CHANNELS_PASSWORD}, 
				null, null, null, null, null);
	}
	
	/**
	 * Returns a cursor containing all channels for a given server title
	 * @param serverTitle
	 * @return
	 */
	public Cursor getChannelsOnServer(String serverTitle)
	{
		return this.db.query(CHANNELLIST_TABLE, new String[] {CHANNELS_SERVER, CHANNELS_NAME, CHANNELS_PASSWORD}, 
				CHANNELS_SERVER + "=" + serverTitle, null, null, null, null);
	}
	
	/**
	 * Inner class for use with DB creation & upgrades
	 * @author ryan
	 *
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
	    {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    /**
	     * Create Initial tables
	     */
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// create serverlist table
			db.execSQL("CREATE TABLE " + SERVERLIST_TABLE + " ( "
					+ (String) BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ SERVERS_TITLE + " TEXT NOT NULL, "
					+ SERVERS_ADDRESS + " TEXT NOT NULL, "
					+ SERVERS_PORT + " INTEGER, "
					+ SERVERS_PASSWORD + " TEXT, "
					+ SERVERS_NICK + " TEXT NOT NULL, "
					+ SERVERS_AUTOCONNECT + " BOOLEAN );"
					);

			
			// create channellist table
			db.execSQL("CREATE TABLE " + CHANNELLIST_TABLE + " ( "
					+ (String) BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CHANNELS_NAME + " TEXT NOT NULL, "
					+ CHANNELS_PASSWORD + " TEXT, " 
					+ CHANNELS_SERVER + " INTEGER );"
					);
			// nickname table?

		}

		/**
		 * On Upgrading the DB
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{	

		}
	}

}