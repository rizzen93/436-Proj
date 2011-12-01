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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListServersActivity extends ListActivity implements OnItemLongClickListener
{
	
	private ServerListAdapter serverListAdapter;
	private ArrayList<Server> servers;
	private ListView listview;
	private Database db;
	
	/**
	 * called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.servers = new ArrayList<Server>();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serverlist);

		db = new Database(this);
		db.open();

		//db.addServer("ReplayIRC", "irc.replayirc.com", 6667, "", "androidtest", false);

		// grab list cursor
		Cursor listCursor = db.getServerList();
		startManagingCursor(listCursor);
		
		setListAdapter(new ServerListAdapter(this, listCursor));
		
		/*this.servers = IRCApp.getInstance().getServerList();
		System.out.println("got the serverlist from IRCApp.getInstance().getServerList()");
		
		
		this.serverListAdapter = new ServerListAdapter(this, this.servers);
		System.out.println("made the serverlist adapter");
		
		System.out.println("setting the list adapter to serverlistadapter");
		setListAdapter(this.serverListAdapter);
		
		System.out.println("getting list view");
		this.listview = getListView();
		
		System.out.println("setting up onitemclick");
		this.listview.setOnItemLongClickListener(this);
		*/
	}
	
	
	/**
	 * For when you long-click on a server object in the list
	 * Will bring up the context menu
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) 
	{
		System.out.println("set onclick stuff");
		// get the server object
		/*final Server server = serverListAdapter.getItem(position);
		
		// list of context options we have
        CharSequence[] submenu = {"Connect", "Disconnect", "Edit", "Delete"};
		
        // create the alert dialog
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(server.getServerTitle());
        alertBuilder.setItems(submenu, new OnClickListener() 
        {
        	@Override
        	public void onClick(DialogInterface dialog, int item)
        	{
        		switch(item)
        		{
        		case 0:
        			// connect to server
        			break;
        		case 1:
        			try
                	{
                		server.disconnect();
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
        */
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
                break;
            case R.id.exit:
            	// exiting the app
                ArrayList<Server> allServers = IRCApp.getInstance().getServerList();
                
                for(Server s : allServers)
                {
                	try
                	{
                		s.disconnect();
                		
                	}
                	catch (IOException e)
                	{
                		e.printStackTrace();
                	}
                }
                finish();
        }

        return true;
    }
    
}
