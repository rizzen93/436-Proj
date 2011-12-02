package net.ircapp.adapters;


import java.util.ArrayList;

import net.ircapp.R;
import net.ircapp.db.Database;
import net.ircapp.model.Server;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ServerListAdapter extends ResourceCursorAdapter 
{

	private ArrayList<Server> servers;
	private Context context;

	public ServerListAdapter(Context ctx, Cursor cursor)
	{
		super(ctx, R.layout.serveritem, cursor);
	}

	/**
	 * Displays our servers as a list! 
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		// bind us to the proper textviews
		TextView txTitle = (TextView) view.findViewById(R.id.serveritem_serverStatus);
		TextView txHost = (TextView) view.findViewById(R.id.serveritem_serverHostname);
		TextView txStatus = (TextView) view.findViewWithTag(R.id.serveritem_serverStatus);

		// get the proper text from the db
		txTitle.setText(cursor.getString(cursor.getColumnIndex(Database.SERVERS_TITLE)));
		txHost.setText(cursor.getString(cursor.getColumnIndex(Database.SERVERS_ADDRESS)));
	}

	/**
	 * Gets a specific item from the list
	 */

}