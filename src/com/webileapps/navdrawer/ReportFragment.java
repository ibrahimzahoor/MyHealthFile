/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webileapps.navdrawer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class ReportFragment extends SherlockListFragment
{
	String TAG="Awain";
	
	private Item[] mathList;
	
	public static ReportFragment newInstance() 
	{
		ReportFragment f = new ReportFragment();
		
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) 
	{
		Intent i = new Intent(getActivity(), ItemViewAvtivity.class);
        
		
		i.putExtra("sender","report");
        i.putExtra("name", mathList[position].name);
        i.putExtra("desc", mathList[position].description);
        i.putExtra("image_source", mathList[position].image);
        i.putExtra("time", mathList[position].time_due);
        
        startActivity(i);
        super.onListItemClick(l, v, position, id);
    }
	
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) 
	{

		switch (item.getItemId()) 
		{

		case R.id.add:
			Toast.makeText(getActivity().getApplicationContext(), "In report fragmentt", 
					   Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		View view = inflater.inflate(R.layout.report_view, container, false);
		
		mathList = new Item[] 
				{
					new Item("X-ray","description","12:00 PM",""), 
					new Item("City Scan","description","10:00 PM",""), 
					new Item("Ultra sound","description","01:00 AM","")
				};
		
		setListAdapter(new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList));
		
		return view;
	}

}