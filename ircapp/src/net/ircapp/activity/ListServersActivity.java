package net.ircapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.adapters.ServerListAdapter;
import net.ircapp.db.Database;
import net.ircapp.model.Channel;
import net.ircapp.model.Server;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListServersActivity extends ListActivity implements OnItemLongClickListener
{

	private ServerListAdapter serverListAdapter;
	private ListView listview;
	Cursor listCursor;

	/**
	 * called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serverlist);

		setTitle("IRCApp -- Server List");
		
		System.out.println("ListServersActivity onCreate");
		
		if(!IRCApp.getInstance().isDBInitialized())
		{
			System.out.println("ListServersActivity initDB");
			IRCApp.getInstance().initDB(this);
		}
		else
			IRCApp.getInstance().getDB().open();

		
		listCursor = IRCApp.getInstance().getDB().getServerList();
		startManagingCursor(listCursor);
		
		System.out.println("ListServers new ServerListAdapter");
		serverListAdapter = new ServerListAdapter(this, listCursor);
		setListAdapter(serverListAdapter);

		this.listview = getListView();
		this.listview.setOnItemLongClickListener(this);
	}
	
	/**
	 * Clicking on a server should speed you along to the channellist
	 */
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		System.out.println("clicked on item: " + position);
		// create the new intent
		Intent i = new Intent(this, ListChannelsActivity.class);
		
		TextView tx = (TextView) v.findViewById(R.id.serveritem_serverTitle);
		
		String title = tx.getText().toString().trim();
		
		i.putExtra("serverTitle", title);
		i.putExtra("serverID", position);
		
		// and start the new activity
		startActivity(i);
	}

	/**
	 * For when you long-click on a server object in the list
	 * Will bring up the context menu
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) 
	{
		// get object from the listadapter
		final Cursor cursor = (Cursor) serverListAdapter.getItem(position);

		// get what we need from the cursor
		final int serverID = cursor.getInt(cursor.getColumnIndex(Database.KEY_ID));
		System.out.println("serverid: " + serverID);
		//final int serverID = position;
		final String serverTitle = cursor.getString(cursor.getColumnIndex(Database.SERVERS_TITLE));
		// list of context options we have
        CharSequence[] submenu = {"Connect", "Disconnect", "Edit", "Delete"};

        // create the alert dialog
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(cursor.getString(cursor.getColumnIndex(Database.SERVERS_TITLE)));
        alertBuilder.setItems(submenu, new OnClickListener() 
        {
        	@Override
        	public void onClick(DialogInterface dialog, int item)
        	{
        		switch(item)
        		{
        		case 0:
        			// connect to server
        			System.out.println("chose to connect to server");	
        			try 
        			{
				
        				// connect to the irc server
						Server s = IRCApp.getInstance().getServerFromID(serverID);
						
        				// create status channel
						Cursor chan = IRCApp.getInstance().getDB().getChannel(serverID, serverTitle + " status");
						chan.moveToFirst();
						
						if(chan.isNull(chan.getColumnIndex(Database.CHANNELS_NAME)))
						{
							
							IRCApp.getInstance().getDB().addChannel(serverID, serverTitle + " status", "");
						}else
							System.out.println("WE ALRAEDY GOT ONE");
						
						s.connect();
						s.joinChannel("#phx");
					} 
        			catch (IOException e) 
        			{

						e.printStackTrace();
					}
        			
        			break;
        		case 1:
        			// disconnect
        			try
                	{
        				Server s = IRCApp.getInstance().getServerFromID(serverID);
        				
        				// bad things happen if i remove this shit.
        				System.out.println("Disconnect: s.id== " + s.getServerID() + " -- " + serverID);
        				if(s.isConnected())
        						s.disconnect();
                	}
                	catch (IOException e)
                	{
                		e.printStackTrace();
                	}
        			break;
        		case 2:
        			// edit server details
        			break;
        		case 3:
        			// delete server from list
        			IRCApp.getInstance().removeServerByID(serverID);
        	        serverListAdapter.notifyDataSetChanged();
        			break;
        		}
        	}
        });
        
        // create & show
        AlertDialog a = alertBuilder.create();
        a.show();
        
		return true;
	}

	/**
	 * Options menu button clicked
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        // create and populate the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.serversmenu, menu);

        return true;
    }
    
    /**
     * on menu item actually selected
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        switch (item.getItemId()) 
        {
            case R.id.add:
            	// adding a server
                startActivityForResult(new Intent(this, AddServerActivity.class), 0);
                
                // refresh the listview
                this.serverListAdapter.notifyDataSetChanged();
                break;
            case R.id.exit:
            	// exiting the app
            	
            	// get all the servers we're connected to
                ArrayList<Server> allServers = IRCApp.getInstance().getServerList();
                
                for(Server s : allServers)
                {
                	try
                	{
                		// and disconnect
                		if(s.isConnected())
                			s.disconnect();
                	}
                	catch (IOException e)
                	{
                		e.printStackTrace();
                	}
                }
                IRCApp.getInstance().getDB().close();
                finish();
        }

        return true;
    }
}
