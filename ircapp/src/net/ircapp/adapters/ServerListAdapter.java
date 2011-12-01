package net.ircapp.adapters;


import java.util.ArrayList;

import net.ircapp.R;
import net.ircapp.model.Server;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ServerListAdapter extends ResourceCursorAdapter 
{

	private ArrayList<Server> servers;
	private Context context;
	
	public ServerListAdapter(Context ctx, Cursor cursor)
	{
		super(ctx, R.layout.serveritem, cursor);
		
		/*this.servers = new ArrayList<Server>();
		// have to load servers from the database
		// dont worry about that now.
		System.out.println("serverlistadapter constructor");
		this.context = ctx;
		this.servers = slist;
		*/
		//notifyDataSetChanged();
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public int getCount() 
	{
		if(this.servers != null)
		{
			return this.servers.size();
		}
		else
			return 0;
	}

	@Override
	public Server getItem(int position) 
	{
		return this.servers.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return this.servers.get(position).getServerID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LinearLayout serverLayout;
		
		Server server = this.servers.get(position);
		System.out.println("Retreived: " + server.getServerTitle() +" : " + server.getServerHostname());
		
		serverLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.serveritem, parent, false);
		System.out.println("serverLayout created");
		
		TextView txTitle = (TextView) serverLayout.findViewById(R.id.serverTitleTextView);
		txTitle.setText(server.getServerTitle());
		System.out.println("set the title text: " + txTitle.getText());
		
		TextView txHost = (TextView) serverLayout.findViewById(R.id.serverHostnameTextView);
		txHost.setText(server.getServerHostname());
		System.out.println("set the host text: " + txTitle.getText());
		
		return serverLayout;
	}*/


}
