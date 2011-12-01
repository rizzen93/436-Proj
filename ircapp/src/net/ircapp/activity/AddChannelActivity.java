package net.ircapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import net.ircapp.R;

public class AddChannelActivity extends Activity implements OnClickListener
{
	
	private Button joinButton;
	private EditText channelName;
	private EditText channelPassword;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinchannel);
		
		this.joinButton = (Button) findViewById(R.id.joinchannel_joinButton);
		this.channelName = (EditText) findViewById(R.id.joinchannel_channelName);
		this.channelPassword = (EditText) findViewById(R.id.joinchannel_channelPassword);
	}
	
	public void onClick(View arg0)
	{
		
	}
	
}
