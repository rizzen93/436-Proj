package net.ircapp.adapters;


import java.util.ArrayList;

import net.ircapp.model.Server;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ServerListAdapter extends BaseAdapter 
{

	private ArrayList<Server> servers;
	private Activity context;
	
	public ServerListAdapter(Activity context)
	{
		// have to load servers from the database
		// dont worry about that now.
		this.servers = new ArrayList<Server>();
		Server s = new Server();
		servers.add(s);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
