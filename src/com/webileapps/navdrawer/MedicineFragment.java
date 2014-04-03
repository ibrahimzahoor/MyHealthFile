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

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;

public class MedicineFragment extends SherlockListFragment
{
	String TAG="Awain";
	ourDatabase db = new ourDatabase(getActivity());
	
	public static MedicineFragment newInstance() 
	{
		MedicineFragment f = new MedicineFragment();
		
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
			Toast.makeText(getActivity().getApplicationContext(), "In medicine fragmentt", 
					   Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity().getApplicationContext(), "In medicine fragmentt", 
					   Toast.LENGTH_SHORT).show();
			Activity activity = this.getActivity();
			
			Intent intent = new Intent(activity.getBaseContext(), AddMedicine.class );
	        startActivity(intent);
	        
			break;
			
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		View view =inflater.inflate(R.layout.medicine_view, container, false);
		
        db = new ourDatabase(getActivity());
        
        //db.deleteAll();
        
        //Medicine m = new Medicine(5, "Triaminic Cold", "abc", "eat twice", true, true, true, true, true, true, true, "", "", true, "12:00"); 
        //db.insertMedicine(m);
        ArrayList<Medicine> listOfMedicines = db.getAllMedicine();
        
        
        Item[] mathList = new Item[listOfMedicines.size()];
		for(int i = 0; i < listOfMedicines.size(); ++i)
		{
			Item obj = new Item( listOfMedicines.get(i).name_of_medicine, listOfMedicines.get(i).medicine_description,
								listOfMedicines.get(i).medicine_alarm_time, listOfMedicines.get(i).medicine_picture);
			
			mathList[i] = obj;
		}
			
		setListAdapter(new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList));
		
		return view;
	}

}