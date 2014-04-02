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


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragmentActivity;


public class MainActivity extends SherlockFragmentActivity 
{
	ourDatabase db = new ourDatabase(this);
	
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;
	
	public com.actionbarsherlock.view.Menu dynamic_menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) 
			{
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) 
			{
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		
		
		if (savedInstanceState == null) 
		{
			selectItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) 
	{
		MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        dynamic_menu=menu; 
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) 
	{

		switch (item.getItemId()) 
		{

			case android.R.id.home: 
			{
				
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) 
				{
					mDrawerLayout.closeDrawer(mDrawerList);
				} 
				else 
				{
					mDrawerLayout.openDrawer(mDrawerList);
				}
				
				break;
			}

		case R.id.add:
			
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements ListView.OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			selectItem(position);
		}
	}
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) 
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void selectItem(int position) 
	{
		switch (position) 
		{
			case 0:
				Toast.makeText(getApplicationContext(), "dashboard case 0", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.content,
								PageSlidingTabStripFragment.newInstance(),
								PageSlidingTabStripFragment.TAG).commit();

				break;
				
			case 1:
				Toast.makeText(getApplicationContext(), "medicine case 1", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().replace(R.id.content,MedicineFragment.newInstance()
						,PageSlidingTabStripFragment.TAG).commit();
				
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "appointment case 2", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().replace(R.id.content,AppointmentFragment.newInstance()
						,PageSlidingTabStripFragment.TAG).commit();
				
				break;
				
			case 3:
				
				Toast.makeText(getApplicationContext(), "report case 3", Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().replace(R.id.content,ReportFragment.newInstance()
						,PageSlidingTabStripFragment.TAG).commit();
				
				break;
				
			default:
				break;
		}
		
		mDrawerLayout.closeDrawer(mDrawerList);
	}

}