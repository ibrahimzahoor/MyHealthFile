package com.hibapps.healthfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hibapps.healthfile.R;

public class DashboardFragment extends SherlockListFragment
{
	ourDatabase db = new ourDatabase(getActivity());
	
	private Item[] mathList;

	public static DashboardFragment newInstance() 
	{
		DashboardFragment f = new DashboardFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) 
	{
		Intent i = new Intent(getActivity(), ItemViewAvtivity.class);
		
		i.putExtra("sender","dashboard");
		i.putExtra("category", mathList[position].category);
        i.putExtra("name", mathList[position].name);
        i.putExtra("desc", mathList[position].description);
        i.putExtra("image_source", mathList[position].image);
        i.putExtra("time", mathList[position].time_due);
        i.putExtra("notify", "no");
    
        startActivity(i);
        super.onListItemClick(l, v, position, id);
    }
	
	
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) 
	{
		switch (item.getItemId()) 
		{

		case R.id.add:
			//Toast.makeText(getActivity().getApplicationContext(), "In dashboard fragment", Toast.LENGTH_SHORT).show();
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view= inflater.inflate(R.layout.dashboard_view, container, false);
		AdView adView = (AdView) view.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		db = new ourDatabase(getActivity());
		
        ArrayList<Medicine> listOfMedicines = db.getAllMedicine_Selected();//new ArrayList<Medicine>();
        ArrayList<Appointment> listOfAppointments = db.getAllAppointment_Selected();
        
        mathList = new Item[listOfMedicines.size() + listOfAppointments.size()];
        int j = 0;
		for(int i = 0; i < listOfMedicines.size(); ++i)
		{
			Item obj = new Item( "med", listOfMedicines.get(i).name_of_medicine, listOfMedicines.get(i).medicine_description,
								listOfMedicines.get(i).medicine_alarm_time, listOfMedicines.get(i).medicine_picture);
			
			mathList[j] = obj;
			++j;
		}
		
		for(int i = 0; i < listOfAppointments.size(); ++i)
		{
			Item obj = new Item( "app", listOfAppointments.get(i).drName, listOfAppointments.get(i).date,
								listOfAppointments.get(i).time, listOfAppointments.get(i).location);
			
			mathList[j] = obj;
			++j;
		}
		
		List<Item> items = Arrays.asList(mathList);
		
		Collections.sort(items, new Comparator<Item>(){

		  public int compare(Item o1, Item o2)
		  {
		     return o1.time_due.compareTo(o2.time_due);
		  }
		});
        
		items.toArray(mathList);
		
		setListAdapter(new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList));
		
		return view;
	}

}