package com.hibapps.healthfile;

import java.io.File;
import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hibapps.healthfile.R;

public class AppointmentFragment extends SherlockListFragment
{
	ItemAdapter AppointmentAdapter;
	MultiChoiceModeListener mListener;
	
	String TAG="Awain";
	ourDatabase db = new ourDatabase(getActivity());
	
	private Item[] mathList;
	
	public static AppointmentFragment newInstance() 
	{
		AppointmentFragment appointment_fragment = new AppointmentFragment();
		return appointment_fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mListener = new MultiChoiceModeListener() {

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

				// If element is checked, it is added to selection; if not, it's deleted
				if (checked) {
					AppointmentAdapter.setNewSelection(position);
				} else {
					AppointmentAdapter.removeSelection(position);
				}

				mode.setTitle(AppointmentAdapter.getSelectionCount() + " Items Selected");
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// CAB menu options
				switch (item.getItemId()) {
				case R.id.item_delete:
					for(int i=0; i<AppointmentAdapter.mSelection.size(); ++i)
					{
						//Toast.makeText(getActivity(), " " + AppointmentAdapter.mSelection.get(i) + " " + mathList[AppointmentAdapter.mSelection.get(i)].id, Toast.LENGTH_LONG).show();
						
						AlarmManager aM = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
						Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
						PendingIntent pI = PendingIntent.getBroadcast(getActivity(), mathList[AppointmentAdapter.mSelection.get(i)].id, myIntent, 0);
						aM.cancel(pI);
						db.deleteCategory(mathList[AppointmentAdapter.mSelection.get(i)].id, "appointment");
					}
					
					akkriwqtkasolution();
					AppointmentAdapter.clearSelection();
					mode.finish();
					return true;
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				
				MenuInflater inflater = getActivity().getMenuInflater();
	            inflater.inflate(R.menu.contexual_menu, menu);
	            return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				
				AppointmentAdapter.clearSelection();
			}
		};
	}

	@Override
	public void onResume() {
		super.onResume();
			
		akkriwqtkasolution();
	}
	
	public void akkriwqtkasolution()
	{
		db = new ourDatabase(getActivity());
		ArrayList<Appointment> listOfAppointments = db.getAllAppointment();
        
        mathList = new Item[listOfAppointments.size()];
		for(int i = 0; i < listOfAppointments.size(); ++i)
		{
			Item obj = new Item( "app", listOfAppointments.get(i).drName, listOfAppointments.get(i).date, 
								listOfAppointments.get(i).time, listOfAppointments.get(i).location );
			obj.id=listOfAppointments.get(i).id;
			obj.phone_no = listOfAppointments.get(i).phone_no;
			obj.email = listOfAppointments.get(i).email;
			mathList[i] = obj;
		}
	
		AppointmentAdapter=new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList);
		setListAdapter(AppointmentAdapter);
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(mListener);
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(getActivity(), ItemViewAvtivity.class);   
		
		i.putExtra("sender","appointment");
		i.putExtra("category", mathList[position].category);
        i.putExtra("name", mathList[position].name);
        i.putExtra("desc", mathList[position].description);
        i.putExtra("time", mathList[position].time_due);
        i.putExtra("image_source", mathList[position].image);
        i.putExtra("phone_no", mathList[position].phone_no);
        i.putExtra("email", mathList[position].email);
        i.putExtra("notify", "no");
        
        startActivity(i);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{	
		View view =inflater.inflate(R.layout.appointment_view, container, false);	
		
		AdView adView = (AdView) view.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
        
		return view;
	}
}