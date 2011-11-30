package net.ircapp.activity;

import java.util.ArrayList;

import net.ircapp.R;
import net.ircapp.adapters.ServerListAdapter;
import net.ircapp.model.Server;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
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

	}
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		return false;
	}
}
