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


import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class AppointmentFragment extends SherlockListFragment
{
	ourDatabase db = new ourDatabase(getActivity());
	String TAG="Awain";
	
	public static AppointmentFragment newInstance() 
	{
		AppointmentFragment f = new AppointmentFragment();
		
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) 
	{

		switch (item.getItemId()) 
		{

		case R.id.add:
			Toast.makeText(getActivity().getApplicationContext(), "In Appointment fragment", 
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
		
		View view= inflater.inflate(R.layout.appointment_view, container, false);
		
		db = new ourDatabase(getActivity());
        
		//Appointment m = new Appointment(5, "Dr. Umar Suleman", "04/02/2014", "12:00", "Zahoor Elahi Road", "", ""); 
        //db.insertAppointment(m);
        ArrayList<Appointment> listOfAppointments = db.getAllAppointment();
        
        Item[] mathList = new Item[listOfAppointments.size()];
		for(int i = 0; i < listOfAppointments.size(); ++i)
		{
			Item obj = new Item( listOfAppointments.get(i).drName, listOfAppointments.get(i).date,
								listOfAppointments.get(i).time, listOfAppointments.get(i).location);
			
			mathList[i] = obj;
		}
		
		setListAdapter(new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList));
        
		return view;
	}

}