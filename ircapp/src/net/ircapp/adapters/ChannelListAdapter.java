package net.ircapp.adapters;

import net.ircapp.R;
import net.ircapp.db.Database;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Should be a mirror of the serverlistadapter, for the most part
 * @author ryan
 *
 */
public class ChannelListAdapter extends ResourceCursorAdapter 
{

	public ChannelListAdapter(Context ctx, Cursor cursor)
	{
		super(ctx, R.layout.channelitem, cursor);
	}

	/**
	 * Displays our servers as a list! 
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		// bind us to the proper textviews

		
		// deal with connectivity here
	}
}
