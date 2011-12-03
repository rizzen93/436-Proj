package net.ircapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import net.ircapp.R;
import net.ircapp.db.Database;
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
		
		// add it to the db, and the serverlist...
		addChannelToDB(c);
		
	}
	
	/**
	 * Add the channel to the table in the DB
	 * @param c
	 */
	private void addChannelToDB(Channel c)
	{
		// get and open the db
		Database db = new Database(this);
		db.open();
		
		// insert the channel, and get it's id
		db.addChannel(this.serverID, c.getChannelName(), c.getChannelPassword());
		
		db.close();
		
		// give the channel it'd id
		//IRCApp.getInstance().getServer(c.getServer())
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
