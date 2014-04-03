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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;

public class DashboardFragment extends SherlockListFragment
{
	//ourDatabase db = new ourDatabase(getActivity());
	
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
			Toast.makeText(getActivity().getApplicationContext(), "In dashboard fragment", 
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
		View view= inflater.inflate(R.layout.dashboard_view, container, false);
		
		//db.insertMedicine();
		
		mathList = new Item[] {
				new Item("LOL x-ray","description","12:00 PM",""), 
				new Item("city scan","description","10:00 PM",""), 
				new Item("Back X-ray","description","11:00 AM",""), 
				new Item("Altra sound of Ibrahim","description","01:00 AM",""),
				new Item("Baby test tube of Ibrahim","description","12:00 PM",""), 
				new Item("Sound test of Komal","description","01:00 AM",""),
				new Item("Dr lallu","description","12:00 PM",""), 
				new Item("Dr tharki","description","10:00 PM",""), 
				new Item("Dr Ibrahim urf hidden theeta","description","11:00 AM",""), 
				new Item("Dr Bilal urf gulabo","description","01:00 AM",""),
				new Item("Dr Abdullah","description","12:00 PM",""), 
				new Item("Acivir Pills","description","10:00 PM",""), 
				new Item("Dr Komal :P","description","01:00 AM",""),
				new Item("Viegra","description","12:00 PM",""), 
				new Item("Acivir Pills","description","10:00 PM",""), 
				new Item(" CIALIS","description","11:00 AM",""), 
				new Item("Female Viegra","description","01:00 AM",""),
				new Item("Viegra","description","12:00 PM",""), 
				new Item("Acivir Pills","description","10:00 PM",""), 
				new Item(" CIALIS","description","11:00 AM",""), 
				new Item("Female Viegra","description","01:00 AM","")};
		
		
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

/*		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());

		TextView v = new TextView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		v.setLayoutParams(params);
		v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
		v.setBackgroundResource(R.drawable.background_card);
		v.setText("CARD " + (position + 1));

		fl.addView(v);
		return fl;*/
	}

}