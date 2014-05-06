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

public class ReportFragment extends SherlockListFragment
{
	ItemAdapter ReportAdapter;
	MultiChoiceModeListener mListener;
	
	String TAG="Awain";
	ourDatabase db = new ourDatabase(getActivity());
	
	private Item[] mathList;
	
	public static ReportFragment newInstance() 
	{
		ReportFragment report_fragment = new ReportFragment();
		return report_fragment;
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
					ReportAdapter.setNewSelection(position);
				} else {
					ReportAdapter.removeSelection(position);
				}

				mode.setTitle(ReportAdapter.getSelectionCount() + " Items Selected");
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// CAB menu options
				switch (item.getItemId()) {
				case R.id.item_delete:
					for(int i=0; i<ReportAdapter.mSelection.size(); ++i)
					{
						//Toast.makeText(getActivity(), " " + ReportAdapter.mSelection.get(i) + " " + mathList[ReportAdapter.mSelection.get(i)].id, Toast.LENGTH_LONG).show();
						ArrayList<String> pathz = db.getAllReportImages(mathList[ReportAdapter.mSelection.get(i)].id);
						for(int j = 0; j < pathz.size(); ++j)
						{
							//Toast.makeText(getActivity(), pathz.get(j), Toast.LENGTH_LONG).show(); 
							File myFile = new File(pathz.get(j));
							myFile.delete();
						}
						
						db.deleteCategory(mathList[ReportAdapter.mSelection.get(i)].id, "report");
					}
					
					akkriwqtkasolution();
					ReportAdapter.clearSelection();
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
				
				ReportAdapter.clearSelection();
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
	      
		ArrayList<Report> listOfReports = db.getAllReport();
        
        mathList = new Item[listOfReports.size()];
		for(int i = 0; i < listOfReports.size(); ++i)
		{
			Item obj = new Item( "rep", listOfReports.get(i).title, listOfReports.get(i).desciption, "" , "");
			obj.setImages(listOfReports.get(i).images);
			obj.id=listOfReports.get(i).id;
			mathList[i] = obj;
		}
			
		ReportAdapter=new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList);
		setListAdapter(ReportAdapter);
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
        
		i.putExtra("sender","report");
		i.putExtra("category", mathList[position].category);
		i.putExtra("name", mathList[position].name);
        i.putExtra("desc", mathList[position].description);
        i.putExtra("image_source", mathList[position].image);
        i.putExtra("time", mathList[position].time_due);
        i.putExtra("rep_images", mathList[position].rep_images);
        i.putExtra("notify", "no");
        
        startActivity(i);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{	
		View view =inflater.inflate(R.layout.report_view, container, false);	
		
		AdView adView = (AdView) view.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
        
		return view;
	}
}