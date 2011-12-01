package net.ircapp.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import net.ircapp.R;
import net.ircapp.adapters.ServerListAdapter;
import net.ircapp.commands.BaseCommands;
import net.ircapp.model.Server;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListServersActivity extends ListActivity implements OnItemLongClickListener
{
	
	private ServerListAdapter serverListAdapter;
	private ListView listview;
	private ArrayList<Server> servers;
	
	private String[] fucksakes;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serverlist);
		
		this.serverListAdapter = new ServerListAdapter(this);
		setListAdapter(this.serverListAdapter);
		
		this.listview = getListView();
		this.listview.setOnItemLongClickListener(this);

		try
		{
			
			Socket s = new Socket("irc.replayirc.com", 6668);
			System.out.println(s);

			InputStreamReader din = new InputStreamReader(s.getInputStream());
			OutputStreamWriter dout = new OutputStreamWriter(s.getOutputStream());
			
			BufferedReader breader = new BufferedReader(din);
			BufferedWriter bwriter = new BufferedWriter(dout);
			
			bwriter.write("NICK rizzentest\n\r");			
			bwriter.write("USER rizzentest 8 * :rizzentest\n\r");		
			bwriter.flush();
			
			System.out.println("Sent: NICK rizzentest");
			System.out.println("Sent: USER rizzentest 8 * :rizzentest");
			
			String nextLine = breader.readLine();
			/*if(nextLine.startsWith("PING"))
			{
				bwriter.write(BaseCommands.server_pong + nextLine.substring(5) + "\n\r");
				System.out.println(BaseCommands.server_pong + nextLine.substring(5));
				bwriter.flush();
			}*/
			
	
			
			while((nextLine = breader.readLine()) != null)
			{
				System.out.println(nextLine);
				if (nextLine.indexOf("004") >= 0)
				{
					// We are now logged in.
					break;
				}
				if(nextLine.startsWith(BaseCommands.server_ping))
				{
					bwriter.write(BaseCommands.server_pong + nextLine.substring(5));
					bwriter.flush();
					System.out.println(BaseCommands.server_pong + nextLine.substring(5));
					
					bwriter.write("NICK rizzentest\n\r");			
					bwriter.write("USER rizzentest 8 * :rizzentest\n\r");		
					bwriter.flush();
					//break;
				}
				/*else if(nextLine.toLowerCase().contains("register first"))
				{
					bwriter.write("NICK rizzentest\n\r");
					bwriter.write("USER rizzentest 8 * :rizzentest\n\r");
					bwriter.flush();
					System.out.println("got here");
				}*/
			}
	
			bwriter.write("JOIN #phx\n\r");
			bwriter.write("PRIVMSG #phx :oh hai");
			bwriter.flush();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{

		
		return false;
	}
}
