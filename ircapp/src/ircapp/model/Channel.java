package ircapp.model;

import java.util.ArrayList;

public class Channel 
{
	
	// type of channel
	private int channelType;
	
	// db stuffs
	private int channelID;
	private String channelName;
	private String channelPassword;
	
	// chat stuff
	private String topic;
	private ArrayList<Message> buffer;
	private ArrayList<Message> history;
	private boolean newMessages;
	
	public Channel(String name)
	{
		this.buffer = new ArrayList<Message>();
		this.history = new ArrayList<Message>();
		
		this.topic = "";
		this.channelName = name.toLowerCase();
	}
	
	public void addMessage(Message msg)
	{
		// we want this newest message to appear first
		this.buffer.add(0, msg);
		this.history.add(msg);
		
		// msg overflow check here (dont want too many of the damn things)
	}
	
	public ArrayList<Message> getBuffer()
	{
		return this.buffer;
	}
	
	public ArrayList<Message> getHistory()
	{
		return this.history;
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
}
