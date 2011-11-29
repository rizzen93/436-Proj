package ircapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Database extends SQLiteOpenHelper 
{

	// db stuff
	private static final String DATABASE_NAME = "ircservers.db";
	private static final int DATABASE_VERSION = 1;
	
	// table names
	private static final String SERVERLIST_TABLE  = "serverlist";
	private static final String CHANNELLIST_TABLE = "channellist";
	
	// serverlist attribute names // example
	private static final String SERVERS_TITLE = "title"; // Freenode
	private static final String SERVERS_ADDRESS = "hostname"; // irc.freenode.net
	private static final String SERVERS_PORT = "port"; // 6667
	private static final String SERVERS_PASSWORD = "password"; // optional, usually blank
	private static final String SERVERS_NICK = "nickname"; // rizzen
	private static final String SERVERS_AUTOCONNECT = "autoconnect"; // yes? no?
	
	
	// channellist attribute names
	private static final String CHANNELS_SERVER = "servertitle"; // means this channel belongs to whatever server was designated as being 'Freenode'
	private static final String CHANNELS_NAME = "channelname"; // #android
	private static final String CHANNELS_PASSWORD = "password"; 
			
	public Database(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * create tables
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// create serverlist table
		db.execSQL("CREATE TABLE " + this.SERVERLIST_TABLE + " ( "
				+ (String) BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ this.SERVERS_TITLE + " TEXT NOT NULL, "
				+ this.SERVERS_ADDRESS + " TEXT NOT NULL, "
				+ this.SERVERS_PORT + " INTEGER, "
				+ this.SERVERS_PASSWORD + " TEXT, "
				+ this.SERVERS_NICK + " TEXT NOT NULL, "
				+ this.SERVERS_AUTOCONNECT + " BOOLEAN );"
				);
		
		// create channellist table
        db.execSQL("CREATE TABLE " + this.CHANNELLIST_TABLE + " ( "
        		+ (String) BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
        		+ this.CHANNELS_NAME + " TEXT NOT NULL, "
        		+ this.CHANNELS_PASSWORD + " TEXT, " 
        		+ this.CHANNELS_SERVER + " INTEGER );"
        		);
	
        // nickname table?
        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		// nothing here yet...
	}
	
	public void addServer()
	{
		
	}
	
	public void updateServer()
	{
		
	}
	
	public void addChannel()
	{
		
	}
	
	public void updateChannel()
	{
		
	}
	
}