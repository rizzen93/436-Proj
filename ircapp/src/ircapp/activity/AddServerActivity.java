package ircapp.activity;

import com.irceapp.R;

import ircapp.model.Server;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AddServerActivity extends Activity implements OnClickListener
{

	private Server server;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.addserver);
	}
	
	@Override
	public void onClick(View arg0) 
	{
		
	}

}
