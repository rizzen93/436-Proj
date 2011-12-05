package net.ircapp.activity;

import java.io.IOException;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.model.Server;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChatActivity extends Activity
{
	//private Handler mHandler;
	private EditText chatLog;
	private EditText chatLine;
	private Button sendButton;
	
	//private String nick;
	private int servID;
	private String channelName = "";
	
	/**
	 * On Create
	 */
	 public void onCreate(Bundle savedInstanceState)
	 {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.channelview);
		 
		 this.chatLog = (EditText) findViewById(R.id.channelview_chatLog);
		 this.chatLine = (EditText) findViewById(R.id.channelview_chatLine);
		 this.sendButton = (Button) findViewById(R.id.channelview_sendButton);
		 
		 Bundle extras = this.getIntent().getExtras();
		 
		 if(extras != null)
		 {
			 //String nick = extras.getString("nick");
			 String name = extras.getString("channelName");
			 servID = extras.getInt("serverID");
		
			 Server s = IRCApp.getInstance().getConnectedServer(servID);
			 
			 if(s == null)
				 finish();
			 
			 setTitle("IRCApp -- " + name);
			 this.channelName = name;
			 
			 for(String m : s.getMessages())
			 {
				 this.chatLog.append(m + "\n");
			 }
			 s.clearMessageQueue();
			 
		 }		 
		 
		 
		 // set up the buttonlistener
		 this.sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				String text = chatLine.getText().toString().trim();
				//System.out.println("Got text: " + text);
				
				chatLine.setText("");
				chatLog.append("me: " + text + "\n");
				
				try
				{
					sendText(text);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		 });
	 }
	 
	 /**
	  * Sends the specified text to the server
	  * @param text
	  * @throws IOException 
	  */
	 public void sendText(String text) throws IOException
	 {
		 IRCApp.getInstance().sendText(servID, this.channelName, text);
	 }
	 
	 public void updateLog(Message msg)
	 {
		 //EditText chatLog = (EditText) findViewById(R.id.channelview_chatLog);
		 chatLog.setText((String) msg.obj);
	 }
	 
}

