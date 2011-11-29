package ircapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.irceapp.R;

public class AddChannelActivity extends Activity implements OnClickListener
{
	
	private Button joinButton;
	private EditText channelName;
	private EditText channelPassword;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinchannel);
		
		this.joinButton = (Button) findViewById(R.id.joinChannelButton);
		this.channelName = (EditText) findViewById(R.id.nameText);
		this.channelPassword = (EditText) findViewById(R.id.passwordText);
	}
	
	public void onClick(View arg0)
	{
		
	}
	
}