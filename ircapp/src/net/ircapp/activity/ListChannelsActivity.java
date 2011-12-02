package net.ircapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

import net.ircapp.IRCApp;
import net.ircapp.R;
import net.ircapp.adapters.ChannelListAdapter;
import net.ircapp.db.Database;
import net.ircapp.model.Server;

public class ListChannelsActivity extends ListActivity implements OnItemLongClickListener
{
	private ChannelListAdapter channelListAdapter;
	private Database db;
	private ListView listview;

	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channellist);

		db = new Database(this);
		db.open();

		Cursor listCursor = db.getServerList();
		startManagingCursor(listCursor);

		channelListAdapter = new ChannelListAdapter(this, listCursor);
		setListAdapter(channelListAdapter);

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
                startActivityForResult(new Intent(this, AddChannelActivity.class), 0);
                
                // refresh the listview
                this.channelListAdapter.notifyDataSetChanged();
                break;
        }

        return true;
    }
}
