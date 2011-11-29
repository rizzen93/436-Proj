package ircapp.activity;

import com.irceapp.R;

import ircapp.model.Server;
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
		
		this.serverAddress = (EditText) findViewById(R.id.serverAddressEditText);
		this.serverPort = (EditText) findViewById(R.id.portEditText);
		this.serverPassword = (EditText) findViewById(R.id.passwordEditText);
		this.nick = (EditText) findViewById(R.id.nickEditText);
		this.addServerButton = (Button) findViewById(R.id.addServerButton);
	}
	
}
