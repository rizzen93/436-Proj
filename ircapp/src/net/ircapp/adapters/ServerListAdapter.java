package net.ircapp.adapters;

import net.ircapp.R;
import net.ircapp.db.Database;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ServerListAdapter extends ResourceCursorAdapter 
{
	
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
		TextView txTitle = (TextView) view.findViewById(R.id.serveritem_serverTitle);
		TextView txHost = (TextView) view.findViewById(R.id.serveritem_serverHostname);
		//TextView txStatus = (TextView) view.findViewWithTag(R.id.serveritem_serverStatus);

		// get the proper text from the db
		txTitle.setText(cursor.getString(cursor.getColumnIndex(Database.SERVERS_TITLE)));
		txHost.setText(cursor.getString(cursor.getColumnIndex(Database.SERVERS_ADDRESS)));
		
		// deal with connectivity here
	}
}
