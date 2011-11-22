package ircapp.model;

import java.util.Date;

/**
 * Default class to hold a message from server/user/whatever
 * @author ryan
 *
 */
public class Message 
{

	private final String sender;
	private final String text;
	private final long timestamp;
	
	public Message(String sender, String text)
	{
		this.text = text;
		this.sender = sender;
		this.timestamp = new Date().getTime();
	}
	
	public String getSender()
	{
		return this.sender;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public long getTimestamp()
	{
		return this.timestamp;
	}
	
	
}
