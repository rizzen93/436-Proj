package net.ircapp.activity;

import net.ircapp.db.Database;
import net.ircapp.model.Server;

import net.ircapp.IRCApp;
import net.ircapp.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddServerActivity extends Activity implements OnClickListener
{
	// gui stuff
	private EditText serverTitle;
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
		
		this.serverTitle = (EditText) findViewById(R.id.addserver_serverTitle);
		this.serverAddress = (EditText) findViewById(R.id.addserver_serverHostname);
		this.serverPort = (EditText) findViewById(R.id.addserver_serverPort);
		this.serverPassword = (EditText) findViewById(R.id.addserver_serverPassword);
		this.nick = (EditText) findViewById(R.id.addserver_serverNickname);
		this.addServerButton = (Button) findViewById(R.id.addserver_addButton);
		
		this.addServerButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		this.addServer();
		finish();
	}
	
	public void addServer()
	{
		// get the constructed server from the view components
		Server newServer = this.getServerFromActivity();
		
		// add it to the databse and our serverlist
		this.addServerToDB(newServer);
		//IRCApp.getInstance().addServer(newServer);
	}
	
	public void addServerToDB(Server s)
	{
		// get the database & open it
		Database db = new Database(this);	
		db.open();
		// get the id while putting it into the db
		db.addServer(s.getServerID(), s.getServerTitle(), s.getServerHostname(), s.getServerPort(), s.getServerPassword(), s.getNickname(), false);
		db.close();
		
		// add to global list
		IRCApp.getInstance().addServer(s);
		
	}
	
	private Server getServerFromActivity()
	{
		String title = ((EditText) findViewById(R.id.addserver_serverTitle)).getText().toString().trim();
		String hostname = ((EditText) findViewById(R.id.addserver_serverHostname)).getText().toString().trim();
		int port = Integer.parseInt(((EditText) findViewById(R.id.addserver_serverPort)).getText().toString().trim());
		String password = ((EditText) findViewById(R.id.addserver_serverPassword)).getText().toString().trim();
		String nickname = ((EditText) findViewById(R.id.addserver_serverNickname)).getText().toString().trim();
		boolean autoConnect = false;
		
		return new Server(IRCApp.getInstance().getNumServers(), title, hostname, port, password, nickname);
	}
	
}
