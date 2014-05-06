package com.hibapps.healthfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class NearbyLocations extends SherlockFragmentActivity implements LocationListener{
	
	GoogleMap mGoogleMap;	
	Spinner mSprPlaceType;	
	
	String[] mPlaceType=null;
	String[] mPlaceTypeName=null;
	
	double mLatitude=0;
	double mLongitude=0;
	
	String APIKEY="23e8b0df";
	
	Polyline polyline1;
	
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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(NearbyLocations.this, APIKEY);
		setContentView(R.layout.nearby_location_activity);		
		
		
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);
		mDrawerList = (ListView) findViewById(R.id.left_drawer2);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		// set up the drawer's list view with items and click listener
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
		
		selectItem(1);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		
		
		
		// Array of place types
		mPlaceType = getResources().getStringArray(R.array.place_type);
		
		// Array of place type names
		mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);
		
		// Creating an array adapter with an array of Place types
		// to populate the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);
		
		// Getting reference to the Spinner 
		mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
		
		// Setting adapter on Spinner to set place types
		mSprPlaceType.setAdapter(adapter);
		
		Button btnFind;
		
		
		// Getting reference to Find Button
		btnFind = ( Button ) findViewById(R.id.btn_find);
		
		
		// Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

        	int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available
        	
	    	// Getting reference to the SupportMapFragment
	    	SupportMapFragment fragment = ( SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	    			
	    	// Getting Google Map
	    	mGoogleMap = fragment.getMap();
	    			
	    	// Enabling MyLocation in Google Map
	    	mGoogleMap.setMyLocationEnabled(true);
	    	
	    	
	    	
	    	// Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                    onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);
	    	
	    	// Setting click event lister for the find button
			btnFind.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
					
					
					int selectedPosition = mSprPlaceType.getSelectedItemPosition();
					String type = mPlaceType[selectedPosition];
										
					
					StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
					sb.append("location="+mLatitude+","+mLongitude);
					sb.append("&radius=5000");
					sb.append("&types="+type);
					sb.append("&sensor=true");
					sb.append("&key=AIzaSyDLGpzkJ8ixdjoOidXiUIBuFTNUhdo-K0M");
					
					
					// Creating a new non-ui thread task to download Google place json data 
			        PlacesTask placesTask = new PlacesTask();		        			        
			        
					// Invokes the "doInBackground()" method of the class PlaceTask
			        placesTask.execute(sb.toString());
					
					
				}
			});
			
			
			mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener()
			{
				@Override
				public boolean onMarkerClick(final Marker marker) {
					
					Button btnDirection = ( Button ) findViewById(R.id.btn_direction);
					btnDirection.setVisibility(View.VISIBLE);
					btnDirection.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							
							LatLng origin = new LatLng(mLatitude, mLongitude);
							LatLng destination = marker.getPosition();
							
							
							
							// Getting URL to the Google Directions API
							String url = getDirectionsUrl(origin, destination);
							
							DownloadTask downloadTask = new DownloadTask();
							
							// Start downloading json data from Google Directions API
							downloadTask.execute(url);
							
						}
					});
					
					return false;
				}
				
			});
        }		
 		
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
				
			case 2:
				startActivity(new Intent(getBaseContext(), ShareButtonActivity.class ));
		        break;
				
			default:
				break;
		}		
		mDrawerLayout.closeDrawer(mDrawerList);
	}	
	
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);                
                

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();                

                // Connecting to url 
                urlConnection.connect();                

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }

                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }

        return data;
    }         

	
	/** A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String>{

		String data = null;
		
		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try{
				data = downloadUrl(url[0]);
			}catch(Exception e){
				 Log.d("Background Task",e.toString());
			}
			return data;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result){			
			ParserTaskPlaces parserTask = new ParserTaskPlaces();
			
			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
		
	}
	
	/** A class to parse the Google Places in JSON format */
	private class ParserTaskPlaces extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

		JSONObject jObject;
		
		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String,String>> doInBackground(String... jsonData) {
		
			List<HashMap<String, String>> places = null;			
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();
        
	        try{
	        	jObject = new JSONObject(jsonData[0]);
	        	
	            /** Getting the parsed data as a List construct */
	            places = placeJsonParser.parse(jObject);
	            
	        }catch(Exception e){
	                Log.d("Exception",e.toString());
	        }
	        return places;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String,String>> list){			
			
			// Clears all the existing markers 
			mGoogleMap.clear();
			
			for(int i=0;i<list.size();i++){
			
				// Creating a marker
	            MarkerOptions markerOptions = new MarkerOptions();
	            
	            // Getting a place from the places list
	            HashMap<String, String> hmPlace = list.get(i);
	
	            // Getting latitude of the place
	            double lat = Double.parseDouble(hmPlace.get("lat"));	            
	            
	            // Getting longitude of the place
	            double lng = Double.parseDouble(hmPlace.get("lng"));
	            
	            // Getting name
	            String name = hmPlace.get("place_name");
	            
	            // Getting vicinity
	            String vicinity = hmPlace.get("vicinity");
	            
	            LatLng latLng = new LatLng(lat, lng);
	            
	            // Setting the position for the marker
	            markerOptions.position(latLng);
	
	            // Setting the title for the marker. 
	            //This will be displayed on taping the marker
	            markerOptions.title(name + " : " + vicinity);	            
	
	            // Placing a marker on the touched position
	            mGoogleMap.addMarker(markerOptions);            
            
			}		
			
		}
		
	}
	
	//for directions code
	
		private String getDirectionsUrl(LatLng origin,LatLng dest){
			
			// Origin of route
			String str_origin = "origin="+origin.latitude+","+origin.longitude;
			
			// Destination of route
			String str_dest = "destination="+dest.latitude+","+dest.longitude;		
			
						
			// Sensor enabled
			String sensor = "sensor=false";			
						
			// Building the parameters to the web service
			String parameters = str_origin+"&"+str_dest+"&"+sensor;
						
			// Output format
			String output = "json";
			
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
			
			
			return url;
		}
		
		
		// Fetches data from url passed
			private class DownloadTask extends AsyncTask<String, Void, String>{			
						
				// Downloading data in non-ui thread
				@Override
				protected String doInBackground(String... url) {
						
					// For storing data from web service
					String data = "";
							
					try{
						// Fetching the data from web service
						data = downloadUrl(url[0]);
					}catch(Exception e){
						Log.d("Background Task",e.toString());
					}
					return data;		
				}
				
				// Executes in UI thread, after the execution of
				// doInBackground()
				@Override
				protected void onPostExecute(String result) {			
					super.onPostExecute(result);			
					
					ParserTaskDirections parserTask = new ParserTaskDirections();
					
					// Invokes the thread for parsing the JSON data
					parserTask.execute(result);
						
				}		
			}
			
			/** A class to parse the Google Places in JSON format */
		    private class ParserTaskDirections extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
		    	
		    	// Parsing the data in non-ui thread    	
				@Override
				protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
					
					JSONObject jObject;	
					List<List<HashMap<String, String>>> routes = null;			           
		            
		            try{
		            	jObject = new JSONObject(jsonData[0]);
		            	DirectionsJSONParser parser = new DirectionsJSONParser();
		            	
		            	// Starts parsing data
		            	routes = parser.parse(jObject);    
		            }catch(Exception e){
		            	e.printStackTrace();
		            }
		            return routes;
				}
				
				// Executes in UI thread, after the parsing process
				@Override
				protected void onPostExecute(List<List<HashMap<String, String>>> result) {
					ArrayList<LatLng> points = null;
					PolylineOptions lineOptions = null;
					MarkerOptions markerOptions = new MarkerOptions();
					String distance = "";
					String duration = "";
					
					
					
					if(result.size()<1){
						Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
						return;
					}
						
					
					// Traversing through all the routes
					for(int i=0;i<result.size();i++){
						points = new ArrayList<LatLng>();
						lineOptions = new PolylineOptions();
						
						// Fetching i-th route
						List<HashMap<String, String>> path = result.get(i);
						
						// Fetching all the points in i-th route
						for(int j=0;j<path.size();j++){
							HashMap<String,String> point = path.get(j);	
							
							if(j==0){	// Get distance from the list
								distance = (String)point.get("distance");						
								continue;
							}else if(j==1){ // Get duration from the list
								duration = (String)point.get("duration");
								continue;
							}
							
							double lat = Double.parseDouble(point.get("lat"));
							double lng = Double.parseDouble(point.get("lng"));
							LatLng position = new LatLng(lat, lng);	
							
							points.add(position);						
						}
						
						// Adding all the points in the route to LineOptions
						lineOptions.addAll(points);
						lineOptions.width(2);
						lineOptions.color(Color.RED);	
						
					}
					
					TextView tvDistanceDuration =(TextView) findViewById(R.id.tv_distance_time);;
					tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
					tvDistanceDuration.setVisibility(View.VISIBLE);
					
					// Drawing polyline in the Google Map for the i-th route
					
					mGoogleMap.addPolyline(lineOptions.color(Color.BLUE).width(4));
					
				}			
		    }   



	@Override
	public void onLocationChanged(Location location) {
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		LatLng latLng = new LatLng(mLatitude, mLongitude);
		
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override public void onStart() 
	{
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	}
	
	@Override
	public void onStop()
	{
		
		EasyTracker.getInstance(this).activityStop(this);
		BugSenseHandler.closeSession(NearbyLocations.this);
		
		super.onStop();
	}
	
}