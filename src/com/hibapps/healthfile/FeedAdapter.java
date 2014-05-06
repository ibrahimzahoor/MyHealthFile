package com.hibapps.healthfile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hibapps.healthfile.R;


public class FeedAdapter extends  ArrayAdapter<Item>
{
	Context context;
	int layoutResourceid;
	Item[] data=null;

	public FeedAdapter(Context context, int layoutResourceId, Item[] data) 
	{
        super(context, layoutResourceId, data);
        this.layoutResourceid = layoutResourceId;
        this.context = context;
        this.data = data;
    }
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		View row;
		if(convertView != null)
		{
			row = convertView;
			row.setBackgroundColor(getContext().getResources().getColor( android.R.color.transparent)); // Default color
		}
		else
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceid, parent, false);
			
		}
		
		//Log.i("mp","my Position is: "+ data[position].subjectName);
		TextView TV1 = (TextView) row.findViewById(R.id.from);
		TV1.setText( data[position].name );
		
		TextView TV2 = (TextView) row.findViewById(R.id.message);
		TV2.setText( data[position].description );
		
		TextView TV3 = (TextView) row.findViewById(R.id.created_at);
		TV3.setText( data[position].time_due );
		
		ImageView imageview= (ImageView)row.findViewById(R.id.provider_photo);
		
		if(data[position].category.equals("facebook"))
		{
			imageview.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_feed));
		}
		else if(data[position].category.equals("linkedin"))
		{
			imageview.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_linkedin));
		}
		else if(data[position].category.equals("twitter"))
		{
			imageview.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_twitter));
		}
		else if(data[position].category.equals("yahoo"))
		{
			imageview.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_yahoo));
		}
		
		return row;
	}
	
}
