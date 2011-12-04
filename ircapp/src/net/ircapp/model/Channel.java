package net.ircapp.model;

import java.util.ArrayList;

public class Channel 
{
	// type of channel
	private int channelType;
	
	// db stuffs
	private String channelName;
	private String channelPassword;
	
	// for handling chat type stuffs
	private Chat chat;
	
	// server info
	private int serverID;
	private Server server;
	private String nickname;
	
	public Channel(int servID, String channelName, String password)
	{
		this.serverID = servID;
		this.channelName = channelName;
		this.channelPassword = password;
		
		this.chat = new Chat(this);
	}
	
	public String getNick()
	{
		return this.nickname;
	}
	
	public void setNick(String nickname)
	{
		this.nickname = nickname;
	}
	
	public void setParentServer(Server s)
	{
		this.server = s;
	}
	
	public Server getParentServer()
	{
		return this.server;
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
	
	public void setID(int id)
	{
		this.serverID = id;
	}
	
	public int getID()
	{
		return this.serverID;
	}
}
