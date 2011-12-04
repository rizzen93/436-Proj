package net.ircapp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.adapters.ChannelListAdapter;
import net.ircapp.model.Channel;

public class ListChannelsActivity extends ListActivity implements OnItemLongClickListener
{
	private ChannelListAdapter channelListAdapter;
	private ListView listview;
	
	private int serverID;
	
	/**
	 * On Create
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		// default stuff, set the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channellist);
		
		System.out.println("ListChannels onCreate()");
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null)
		{
			this.serverID = extras.getInt("serverID");
			String title = extras.getString("serverTitle");
		
			System.out.println("Retrieved ID: " + serverID);
		
			// create the default channel we get system messages from
			// and add it to the db
			Channel system = new Channel(serverID, title, "");
			IRCApp.getInstance().getDB().addChannel(serverID, system.getChannelName(), system.getChannelPassword());
		}
		
		// get the cursor we're using from the global instance
		Cursor listCursor = IRCApp.getInstance().getDB().getChannelList();
		startManagingCursor(listCursor);

		// start using the channeladapter to populate the list
		channelListAdapter = new ChannelListAdapter(this, listCursor);
		setListAdapter(channelListAdapter);

		// set the listview & long clicks
		this.listview = getListView();
		this.listview.setOnItemLongClickListener(this);
	}

	/**
	 * Individual channel options
	 * edit, remove
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteChannel(long id)
	{
		
	}
	
	/**
	 * Join channel the user clicked, and switch to chatactivity
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent i = new Intent(this, ChatActivity.class);
		
		
		/*
		// and relevent channel info
		i.putExtra("channelName", channel.getChannelName());
		i.putExtra("channeHost", channel.getHostname()?);
		*/
		startActivity(i);
	}

	/**
	 * options menu button clicked
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.channelsmenu, menu);

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
            	// adding a channel
            	Intent i = new Intent(this, AddChannelActivity.class);
            	i.putExtra("serverID", this.serverID);
            	
                startActivityForResult(i, 0);
                
                // refresh the listview
                this.channelListAdapter.notifyDataSetChanged();
                break;
        }

        return true;
    }
}
