package net.ircapp.db;

import net.ircapp.model.Channel;
import net.ircapp.model.Server;
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
	public static final String CHANNELS_NAME = "channelname"; // #android
	public static final String CHANNELS_PASSWORD = "password"; 

	// keys
	public static final String KEY_ID = "_id";

    //private DatabaseHelper mDbHelper;
    private SQLiteDatabase db;
    private Context context;

	public Database(Context context)
	{
		System.out.println("New Database created");
		this.context = context;
	}

	public Database open() throws SQLException
	{
		DatabaseHelper dbHelper = new DatabaseHelper(this.context);
		this.db = dbHelper.getWritableDatabase();

		System.out.println("Database opened for writing");
		
		return this;
	}

	public void close()
	{
		System.out.println("Database closed");
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
	public long addServer(int id, String title, String hostname, int port, String password, String nickname, boolean autoConnect)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(SERVERS_TITLE, title);
		values.put(SERVERS_ADDRESS, hostname);
		values.put(SERVERS_PORT, port);
		values.put(SERVERS_PASSWORD, password);
		values.put(SERVERS_NICK, nickname);
		values.put(SERVERS_AUTOCONNECT, autoConnect);

		System.out.println("Adding server to the DB: " + title);
		
		return this.db.insert(SERVERLIST_TABLE, null, values);
	}
	
	public Server getServerFromCursor(Cursor c)
	{
		Server s = new Server(c.getInt(c.getColumnIndex(Database.KEY_ID)),
				c.getString(c.getColumnIndex(Database.SERVERS_TITLE)),
				c.getString(c.getColumnIndex(Database.SERVERS_ADDRESS)),
				c.getInt(c.getColumnIndex(Database.SERVERS_PORT)),
				c.getString(c.getColumnIndex(Database.SERVERS_PASSWORD)),
				c.getString(c.getColumnIndex(Database.SERVERS_NICK)));

		return s;
	}
	
	public Channel getChannelFromCursor(Cursor c)
	{
		//Channel c = new Channel()
		return null;
	}

	public void removeServer(int id)
	{
		
	}
	
	/**
	 * Gets a cursor containing the server we want, via server id
	 * @param id
	 * @return
	 */
	public Cursor getServer(int id)
	{
		return null;
	}
	
	/**
	 * Gets a cursor containing the server we want, via server title
	 * @param servertitle
	 * @return
	 */
	public Cursor getServer(String servertitle)
	{
		System.out.println("DB Query: GET " + servertitle);
		return db.query(SERVERLIST_TABLE, new String[] {SERVERS_TITLE, SERVERS_ADDRESS, SERVERS_PORT, SERVERS_PASSWORD, SERVERS_NICK}, 
				SERVERS_TITLE + "=" + servertitle, null, null, null, null);
	}

	/**
	 * Gets a cursor containing the channel we want
	 * @param serverTitle
	 * @param channelName
	 * @return
	 */
	public Cursor getChannel(int serverID, String channelName)
	{
		System.out.println("DB Query: GET " + channelName);
		return db.query(CHANNELLIST_TABLE, new String[] { CHANNELS_NAME, CHANNELS_PASSWORD}, 
				KEY_ID+ "=" + serverID + ", " + CHANNELS_NAME + "=" + channelName, null, null, null, null);
	}

	/**
	 * Adds a channel to CHANNELLIST_TABLE
	 * @param serverTitle
	 * @param channelName
	 * @param channelPassword
	 * @return
	 */
	public long addChannel(int id, String channelName, String channelPassword)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(CHANNELS_NAME, channelName);
		values.put(CHANNELS_PASSWORD, channelPassword);
	
		System.out.println("Adding new channel to DB: " + channelName);
		
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
		System.out.println("Getting ServerList from DB");
		return this.db.query(SERVERLIST_TABLE, new String[] {KEY_ID, SERVERS_TITLE, SERVERS_ADDRESS, SERVERS_PORT, SERVERS_PASSWORD, SERVERS_NICK, SERVERS_AUTOCONNECT}, 
				null, null, null, null, null);
	}

	/**
	 * Returns a cursor containing all entries in the channellist table
	 * @return
	 */
	public Cursor getChannelList()
	{
		System.out.println("Getting ChannelList from DB");
		return this.db.query(CHANNELLIST_TABLE, new String[] {KEY_ID, CHANNELS_NAME, CHANNELS_PASSWORD}, 
				null, null, null, null, null);
	}
	
	/**
	 * Returns a cursor containing all channels for a given server title
	 * @param serverTitle
	 * @return
	 */
	public Cursor getChannelsOnServer(int serverID)
	{
		System.out.println("Getting channels on server: " + serverID);
		return this.db.query(CHANNELLIST_TABLE, new String[] {KEY_ID, CHANNELS_NAME, CHANNELS_PASSWORD}, 
				KEY_ID + "=" + serverID, null, null, null, null);
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
			System.out.println("Create serverlist table");
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

			System.out.println("Create channellist table");
			// create channellist table
			db.execSQL("CREATE TABLE " + CHANNELLIST_TABLE + " ( "
					+ (String) BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CHANNELS_NAME + " TEXT NOT NULL, "
					+ CHANNELS_PASSWORD + " TEXT );" 
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