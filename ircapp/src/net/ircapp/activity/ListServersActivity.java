package net.ircapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.adapters.ServerListAdapter;
import net.ircapp.db.Database;
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

public class ListServersActivity extends ListActivity implements OnItemLongClickListener
{

	private ServerListAdapter serverListAdapter;
	private Database db;
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

		IRCApp.getInstance().initDB(this);
		
		listCursor = IRCApp.getInstance().getDB().getServerList();
		startManagingCursor(listCursor);
		
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
		// create the new intent
		Intent i = new Intent(this, ListChannelsActivity.class);
		
		/*
		// add relevent server info -- id too?
		i.putExtra("serverTitle", server.getServerTitle());
		i.putExtra("serverHostname", server.getServerHostname());
		*/
		
		// check connection status here
		//db.close();
		//listCursor.close();
		
		startActivity(i);
	}

	/**
	 * For when you long-click on a server object in the list
	 * Will bring up the context menu
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) 
	{
		// get the server object

		final Cursor cursor = (Cursor) serverListAdapter.getItem(position);
		final long servid = id;

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
        			
        			//Cursor c = db.getServer(servid);  			
      
        			// get the server attributes from database
        			String title = cursor.getString(cursor.getColumnIndex(Database.SERVERS_TITLE));
        			String hostname = cursor.getString(cursor.getColumnIndex(Database.SERVERS_ADDRESS));
        			String port = cursor.getString(cursor.getColumnIndex(Database.SERVERS_PORT)); 
        			String password = cursor.getString(cursor.getColumnIndex(Database.SERVERS_PASSWORD));
        			String nickname = cursor.getString(cursor.getColumnIndex(Database.SERVERS_NICK));
        			
        			// make new object
        			Server newServer = new Server(title, hostname, Integer.parseInt(port), password, nickname);
        			IRCApp.getInstance().addServer(newServer);
        			
        			System.out.println(newServer.toString());
        			try 
        			{
        				// connect to the irc server
						newServer.connect();
						newServer.joinChannel("phx");
					} 
        			catch (IOException e) 
        			{

						e.printStackTrace();
					}
        			
        			break;
        		case 1:
        			try
                	{
        				if(IRCApp.getInstance().getNumServers() > 0)
        				{
        					if(IRCApp.getInstance().getServer(cursor).isConnected())
        						IRCApp.getInstance().getServer(cursor).disconnect();
        						
        				}
        				//server.disconnect();
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
	 * options menu button clicked
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

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
