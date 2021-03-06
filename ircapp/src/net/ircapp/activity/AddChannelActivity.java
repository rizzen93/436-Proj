package net.ircapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.model.Channel;

public class AddChannelActivity extends Activity implements OnClickListener
{
	
	private Button joinButton;
	private EditText channelName;
	private EditText channelPassword;

	private int serverID;
	/**
	 * On Create
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinchannel);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null)
			this.serverID = extras.getInt("serverID", serverID);
		
		this.joinButton = (Button) findViewById(R.id.joinchannel_joinButton);
		this.channelName = (EditText) findViewById(R.id.joinchannel_channelName);
		this.channelPassword = (EditText) findViewById(R.id.joinchannel_channelPassword);
		
		this.joinButton.setOnClickListener(this);
	}
	
	/**
	 * On Click
	 */
	public void onClick(View v)
	{
		this.addChannel();
		finish();
	}
	
	/**
	 * Add the channel to where it needs to be
	 * @param c
	 */
	public void addChannel()
	{
		// construct the channel from the ui components
		Channel c = this.getChannelFromActivity();
		
		// add it to the db
		IRCApp.getInstance().getDB().addChannel(this.serverID, c.getChannelName(), c.getChannelPassword());
		
	}

	/**
	 * Construct and return the channel from UI components
	 * @return
	 */
	public Channel getChannelFromActivity()
	{
		String name = ((EditText) findViewById(R.id.joinchannel_channelName)).getText().toString().trim();
		String password = ((EditText) findViewById(R.id.joinchannel_channelPassword)).getText().toString().trim();
		
		return new Channel(serverID, name, password);
	}
	
}
