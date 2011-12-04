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
import android.widget.TextView;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.adapters.ChannelListAdapter;
import net.ircapp.db.Database;
import net.ircapp.model.Channel;

public class ListChannelsActivity extends ListActivity implements OnItemLongClickListener
{
	private ChannelListAdapter channelListAdapter;
	private ListView listview;
	
	private int serverID;
	private String nick;
	
	/**
	 * On Create
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		// default stuff, set the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channellist);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null)
		{
			//this.nick = extras.getString("nick");
			this.serverID = extras.getInt("serverID");
			String title = extras.getString("serverTitle");
		
			System.out.println("Retrieved ID: " + serverID);
		
			setTitle("IRCApp -- " + title);
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
		
		System.out.println("# CHANNELS: " + IRCApp.getInstance().getDB().getNumRows(Database.CHANNELLIST_TABLE));
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
		
		// grab the channelname
		TextView tx = (TextView) v.findViewById(R.id.channelitem_channelName);
		String name = tx.getText().toString().trim();
		
		// toss in the extra bits we'll need at the chat level
		//i.putExtra("nick", this.nick);
		i.putExtra("channelName", name);
		i.putExtra("serverid", this.serverID);
		
		// and join the channel we're clicking on
		try
		{
			IRCApp.getInstance().getConnectedServer(this.serverID).joinChannel(name);
			//IRCApp.getInstance().getConnectedServer(this.serverID).addChannel(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// finally, start the activity
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
