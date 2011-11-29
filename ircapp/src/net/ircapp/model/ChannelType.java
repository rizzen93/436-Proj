package net.ircapp.model;

/**
 * a channel can be for server messages, private messages, or an actual channel
 * this class will help us to differentiate between them
 * @author ryan
 *
 */
public class ChannelType 
{

	public static final int CHAT_CHANNEL = 0;
	public static final int SERVER_MSG = 1;
	public static final int PRIVATE_MSG = 2;
	
}
