package net.ircapp.activity;

import net.ircapp.model.Server;

import net.ircapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddServerActivity extends Activity // implements OnClickListener
{

	private Server server;
	
	// gui stuff
	private EditText serverAddress;
	private EditText serverPort;
	private EditText serverPassword;
	private EditText nick;
	private Button addServerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addserver);
		
		this.serverAddress = (EditText) findViewById(R.id.addserver_serverHostname);
		this.serverPort = (EditText) findViewById(R.id.addserver_serverPort);
		this.serverPassword = (EditText) findViewById(R.id.addserver_serverPassword);
		this.nick = (EditText) findViewById(R.id.addserver_serverNickname);
		this.addServerButton = (Button) findViewById(R.id.addserver_addButton);
	}
	
}
