package net.ircapp.activity;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.commands.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChatActivity extends Activity
{
	
	private EditText chatLog;
	private EditText chatLine;
	private Button sendButton;
	
	/**
	 * On Create
	 */
	 public void onCreate(Bundle savedInstanceState)
	 {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.channelview);
		 
		 Bundle extras = this.getIntent().getExtras();
		 
		 if(extras != null)
			 System.out.println("WE GOT EXTRAS HERE");
		 
		 this.chatLog = (EditText) findViewById(R.id.channelview_chatLog);
		 this.chatLine = (EditText) findViewById(R.id.channelview_chatLine);
		 this.sendButton = (Button) findViewById(R.id.channelview_sendButton);
		 
		 
		 this.sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				String text = chatLine.getText().toString().trim();
				System.out.println("Got text: " + text);
				
				chatLine.setText("");
				chatLog.append(text);
				
				sendText(text);
			}
		 });
	 }
	 
	 /**
	  * Sends the specified text to the server
	  * @param text
	  */
	 public void sendText(String text)
	 {
		 //IRCApp.getInstance().sendText(servID, Constants.PRIVMSG, channelName, text);
	 }
}

