package net.ircapp.model;

import java.util.ArrayList;

public class Channel 
{
	// type of channel
	private int channelType;
	
	// db stuffs
	private long channelID;
	private String channelName;
	private String channelPassword;
	
	
	private String server;
	
	private Chat chat;
	
	public Channel(String name, String password, String serverTitle)
	{
		this.channelName = name;
		this.server = serverTitle;
		this.channelPassword = password;
		
		this.chat = new Chat(this);
	}
	
	public void setChannelType(int type)
	{
		// check if valid (0-2) here
		this.channelType = type;
	}
	
	public int getChannelType()
	{
		return this.channelType;
	}
	
	public String getChannelName()
	{
		return this.channelName;
	}
	
	public String getChannelPassword()
	{
		return this.channelPassword;
	}
	
	public String getServerTitle()
	{
		return this.server;
	}
	
	public void setID(long id)
	{
		this.channelID = id;
	}
	
	public long getID()
	{
		return this.channelID;
	}
}
