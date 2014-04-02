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

public class ReportFragment extends SherlockListFragment
{
	String TAG="Awain";
	
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
		
		Item[] mathList = new Item[] {
				new Item("LOL x-ray","description","12:00 PM",""), 
				new Item("city scan","description","10:00 PM",""), 
				new Item("Back X-ray","description","11:00 AM",""), 
				new Item("Altra sound of Ibrahim","description","01:00 AM",""),
				new Item("Baby test tube of Ibrahim","description","12:00 PM",""), 
				new Item("Sound test of Komal","description","01:00 AM","")};
		
		
		
		
		setListAdapter(new ItemAdapter(getActivity(), R.layout.subject_item_row, mathList));
		
		return view;

//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//
//		FrameLayout fl = new FrameLayout(getActivity());
//		fl.setLayoutParams(params);
//
//		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
//				.getDisplayMetrics());
//
//		TextView v = new TextView(getActivity());
//		params.setMargins(margin, margin, margin, margin);
//		v.setLayoutParams(params);
//		v.setLayoutParams(params);
//		v.setGravity(Gravity.CENTER);
//		v.setBackgroundResource(R.drawable.background_card);
//		v.setText("CARD " + (position + 1));
//
//		fl.addView(v);
//		return fl;
	}

}