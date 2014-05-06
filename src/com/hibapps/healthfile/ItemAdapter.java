package com.hibapps.healthfile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hibapps.healthfile.R;


public class ItemAdapter extends  ArrayAdapter<Item>
{
	Context context;
	int layoutResourceid;
	Item[] data=null;
	
	
	public ArrayList<Integer> mSelection = new ArrayList<Integer>();

	
	public ItemAdapter(Context context, int layoutResourceId, Item[] data) 
	{
		
        super(context, layoutResourceId, data);
        this.layoutResourceid = layoutResourceId;
        this.context = context;
        this.data = data;
        
    }
	
	/**
	 * Adds an element in selection and updates the view
	 * 
	 * @param position
	 *            Item position
	 */
	public void setNewSelection(int position) {
		mSelection.add(position);
		notifyDataSetChanged();
	}

	/**
	 * Get current selected items
	 * 
	 * @return list of items
	 */
	public ArrayList<Integer> getCurrentCheckedPosition() {
		return mSelection;
	}

	/**
	 * Remove an element from selected items
	 * 
	 * @param position
	 *            Item position
	 */
	public void removeSelection(int position) {
		mSelection.remove(Integer.valueOf(position));
		notifyDataSetChanged();
	}

	/**
	 * Clear current selection
	 */
	public void clearSelection() {
		mSelection = new ArrayList<Integer>();
		notifyDataSetChanged();
	}

	/**
	 * Get number of selected items
	 * 
	 * @return Selection count
	 */
	public int getSelectionCount() {
		return mSelection.size();
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
		
		if(data[position].category.equals("app"))
		{
			ImageView image = (ImageView) row.findViewById(R.id.list_image);
			image.setImageResource(R.drawable.ic_appointment);
		}
		else if(data[position].category.equals("med"))
		{
			ImageView image = (ImageView) row.findViewById(R.id.list_image);
			image.setImageResource(R.drawable.ic_medicine);
		}
		else
		{
			ImageView image = (ImageView) row.findViewById(R.id.list_image);
			image.setImageResource(R.drawable.ic_report);
		}
		
	
		TextView TV1 = (TextView) row.findViewById(R.id.name);
		TV1.setText( data[position].name );
		
		TextView TV2 = (TextView) row.findViewById(R.id.desc);
		TV2.setText( data[position].description );
		
		TextView TV3 = (TextView) row.findViewById(R.id.time);
		TV3.setText( data[position].time_due );
		
		row.setBackgroundColor(getContext().getResources().getColor(
				android.R.color.transparent)); // Default color

		if (mSelection.contains(position)) {
			row.setBackgroundColor(getContext().getResources().getColor(
					android.R.color.tab_indicator_text)); // color when selected
		}
		
		return row;
	}

}
