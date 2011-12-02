package net.ircapp.model;

import java.util.ArrayList;

public class Chat 
{

	// chat stuff
	private String topic;
	private ArrayList<Message> buffer;
	private ArrayList<Message> history;
	private boolean newMessages;
	
	private Channel parent;
	
	public Chat(Channel c)
	{
		this.parent = c;
		this.buffer = new ArrayList<Message>();
		this.history = new ArrayList<Message>();
		
		this.topic = "";
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
	
	
}
