package com.hibapps.healthfile;

import java.io.File;
import java.util.List;

import org.brickred.socialauth.Feed;
import org.brickred.socialauth.Photo;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.util.Response;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShareButtonActivity extends SherlockFragmentActivity 
{
	String currentProvider=""; 
	// SocialAuth Component
	SocialAuthAdapter adapter;
	Profile profileMap;
	List<Photo> photosList;

	// Android Components
	Button update;
	EditText edit;
	
	
	//**********************************************
	ourDatabase db = new ourDatabase(this);
	
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;
	
	public com.actionbarsherlock.view.Menu dynamic_menu;
	//**********************************************

	
	String APIKEY="23e8b0df";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(ShareButtonActivity.this, APIKEY);
		setContentView(R.layout.share);
		
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
		mDrawerList = (ListView) findViewById(R.id.left_drawer1);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		DrawerItem []obj=new DrawerItem[3];
		
		DrawerItem obj2=new DrawerItem(R.drawable.ic_medical, (String) mPlanetTitles[0]);
		obj[0]=obj2;
		DrawerItem obj3=new DrawerItem(R.drawable.ic_nearby, (String) mPlanetTitles[1]);
		obj[1]=obj3;
		DrawerItem obj4=new DrawerItem(R.drawable.ic_social, (String) mPlanetTitles[2]);
		obj[2]=obj4;
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_item, obj));
		mDrawerList.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
				
			}
		});
	

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
		
		selectItem(2);
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		
		// Create Your Own Share Button
		Button share = (Button) findViewById(R.id.sharebutton);
		share.setText("Choose Network");
		share.setTextColor(Color.WHITE);
		

		// Add it to Library
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		
		adapter.addProvider(Provider.EMAIL, R.drawable.email);
		adapter.addProvider(Provider.MMS, R.drawable.mms);

		// Providers require setting user call Back url
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		

		// Enable Provider
		adapter.enable(share);
		
		
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
		}

		return super.onOptionsItemSelected(item);
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
				startActivity(new Intent(getBaseContext(), MainActivity.class ));
				break;
				
			case 1:
				startActivity(new Intent(getBaseContext(), NearbyLocations.class ));
				break;
			
			default:
				break;
		}		
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	

	/**
	 * Listens Response from Library
	 * 
	 */

	private final class ResponseListener implements DialogListener 
	{
		
		
		@Override
		public void onComplete(Bundle values) 
		{
			
			Log.d("ShareButton", "Authentication Successful");
			
			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			
			currentProvider=providerName.toLowerCase();
			
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(ShareButtonActivity.this, providerName + " connected", Toast.LENGTH_LONG).show();

			adapter.getFeedsAsync(new FeedDataListener());
			
			
			update = (Button) findViewById(R.id.update);
			edit = (EditText) findViewById(R.id.editTxt);

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			update.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View v) {
					// to share on multiple providers
					adapter.updateStatus(edit.getText().toString(), new MessageListener(), false);
				}
			});

			// Share via Email Intent
			if (providerName.equalsIgnoreCase("share_mail")) {
				// Use your own code here
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
						"vineet.aggarwal@3pillarglobal.com", null));
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test");
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);
				emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(emailIntent, "Test"));
			}

			// Share via mms intent
			if (providerName.equalsIgnoreCase("share_mms")) 
			{

				// Use your own code here
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);

				Intent mmsIntent = new Intent(Intent.ACTION_SEND, uri);
				mmsIntent.putExtra("sms_body", "Test");
				mmsIntent.putExtra(Intent.EXTRA_STREAM, uri);
				mmsIntent.setType("image/png");
				startActivity(mmsIntent);
			}

		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}

	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> 
	{
		@Override
		public void onExecute(String provider, Integer t) 
		{
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(ShareButtonActivity.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(ShareButtonActivity.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) 
		{

		}
	}
	
	
	// To receive the feed response after authentication
	private final class FeedDataListener implements SocialAuthListener<List<Feed>> 
	{
		@Override
		 public void onExecute(String id,List<Feed> t) 
		 {
			 Log.d("Custom-UI", "Receiving Data");
			 
			 List<Feed> feedList = t;
			 
			 if (feedList != null && feedList.size() > 0) 
			 {
				 Item[] feeds=new Item[feedList.size()];
				 Log.d("Custom-UI", "Size ###################### "+feedList.size());
				 int i=0;
				 for (Feed f : feedList) 
				 {
				     /*Log.d("Custom-UI", "Feed ID = " + f.getId());
				     Log.d("Custom-UI", "Screen Name = " + f.getScreenName());
				     Log.d("Custom-UI", "Message = " + f.getMessage());
				     Log.d("Custom-UI", "Get From = " + f.getFrom());
				     Log.d("Custom-UI", "Created at = " + f.getCreatedAt());
				     */
					 Item feedIttem=new Item();
					 
					 feedIttem.name=f.getFrom();
					 feedIttem.description=f.getMessage();
					 feedIttem.time_due=""+f.getCreatedAt();
					 feedIttem.category=currentProvider;
					 feeds[i]=feedIttem;
					 
					 
					 
					 
					 
				     Log.d("Custom-UI", "Index ###################### "+i);
				     i++;
				 }
				 
				 
				 
				 
				 FeedAdapter adapt= new FeedAdapter(ShareButtonActivity.this, R.layout.feed_item,feeds );
				 ListView feedL=(ListView) findViewById(R.id.feed);
				 feedL.setAdapter(adapt);
				 
			 }
		}
		
		
	
		 @Override
		 public void onError(SocialAuthError e) 
		 {
	
		 }

		
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
		BugSenseHandler.closeSession(ShareButtonActivity.this);
	}

}