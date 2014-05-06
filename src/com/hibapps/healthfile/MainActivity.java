package com.hibapps.healthfile;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;

import com.google.analytics.tracking.android.EasyTracker;



public class MainActivity extends SherlockFragmentActivity
{
	ourDatabase db = new ourDatabase(this);
	
	Item[] mathList;
	ItemAdapter adapter;
	ListView list;
	RelativeLayout l;
	
	public DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	public ActionBarDrawerToggle mDrawerToggle;

	public CharSequence mDrawerTitle;
	public CharSequence mTitle;
	public String[] mPlanetTitles;
	
	String APIKEY="23e8b0df";
	
	public com.actionbarsherlock.view.Menu dynamic_menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//saving data from previous
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(MainActivity.this, APIKEY);
		
		BugSenseHandler.startSession(MainActivity.this);
		
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		DrawerItem []obj=new DrawerItem[3];
		
		DrawerItem obj2=new DrawerItem(R.drawable.ic_medical, (String) mPlanetTitles[0]);
		obj[0]=obj2;
		DrawerItem obj3=new DrawerItem(R.drawable.ic_nearby, (String) mPlanetTitles[1]);
		obj[1]=obj3;
		DrawerItem obj4=new DrawerItem(R.drawable.ic_social, (String) mPlanetTitles[2]);
		obj[2]=obj4;
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_item, obj));
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
			
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) 
			{
				mDrawerLayout.closeDrawer(mDrawerList);
			} 
			else 
			{
				mDrawerLayout.openDrawer(mDrawerList);
			}
			break;

		case R.id.add:
			
			PopupMenu popup = new PopupMenu(this, findViewById(R.id.add) );
			popup.inflate(R.menu.popup_menu);
		    
		    // This activity implements OnMenuItemClickListener
		    popup.setOnMenuItemClickListener(new OnMenuItemClickListener(){

				@Override
				public boolean onMenuItemClick(MenuItem item) 
				{
					switch (item.getItemId()) 
					{
			        case R.id.add_medicine:
			        	
						Intent medicine_intent = new Intent(getBaseContext(), AddObject.class );
						medicine_intent.putExtra("addition_type","medicine");
						startActivityForResult(medicine_intent, 0);
						return true;
				        
			        case R.id.add_appointment:
			        	
						Intent appointment_intent = new Intent(getBaseContext(), AddObject.class );
						appointment_intent.putExtra("addition_type","appointment");
						startActivityForResult(appointment_intent, 1);
				        return false;
			            
			        case R.id.add_report:
			        	
			        	Intent report_intent = new Intent(getBaseContext(), AddObject.class );
			        	report_intent.putExtra("addition_type","report");
						startActivityForResult(report_intent, 2);
						return true;
			        
			        default:
			            return false;
					}
				}
		    });
		    popup.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
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
				getSupportFragmentManager().beginTransaction().replace(R.id.content, PageSlidingTabStripFragment.newInstance(), PageSlidingTabStripFragment.TAG).commit();
				break;
				
			case 1:
				startActivity(new Intent(getBaseContext(), NearbyLocations.class ));
				break;
			
			case 2:
				startActivity(new Intent(getBaseContext(), ShareButtonActivity.class ));
		        break;
				
			default:
				break;
		}
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override public void onStart() 
	{
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
		BugSenseHandler.closeSession(MainActivity.this);
	}
}
