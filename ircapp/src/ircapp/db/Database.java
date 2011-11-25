package ircapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper 
{

	private static final String DATABASE_NAME = "ircservers.db";
	private static final int DATABASE_VERSION = 1;
	
	public Database(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// create serverlist table
		// name of entry
		// server address ie: irc.freenode.net
		// server port
		// server password?
		// preferred nick
		
		// create channellist table
		// name of entry
		// channel ie: #android
		// password
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		// nothing here yet...
	}
	
}
