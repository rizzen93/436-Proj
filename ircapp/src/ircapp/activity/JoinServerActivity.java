package ircapp.activity;

import com.irceapp.R;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class JoinServerActivity extends ListActivity
{

	private Button addServer;
	private Button connectToServer;
	private Spinner serverSelector;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		// dont understand this quite yet
		super.onCreate(savedInstanceBundle);
		
		// get the gui set up via main.xml
		setContentView(R.layout.main);
		
		// get our gui objects
		this.addServer = (Button) findViewById(R.id.addServerButton);
		this.connectToServer = (Button) findViewById(R.id.connectToServerButton);
		this.serverSelector = (Spinner) findViewById(R.id.serverSelectionSpinner);
		
		
	}

}
