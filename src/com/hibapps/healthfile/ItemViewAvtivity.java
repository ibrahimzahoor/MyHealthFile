package com.hibapps.healthfile;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ItemViewAvtivity extends SherlockFragmentActivity 
{
	Intent i = null;
	
	String APIKEY="23e8b0df";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//saving data from previous
		super.onCreate(savedInstanceState);
		
		i=getIntent();
		
		if(i.getStringExtra("sender").equals("dashboard"))
		{
			this.setTitle("Items Details");
			if(i.getStringExtra("category").equals("med"))
			{
				BugSenseHandler.initAndStartSession(ItemViewAvtivity.this, APIKEY);
				setContentView(R.layout.item_detailed_view_med);
				
				
				
			}
			else if(i.getStringExtra("category").equals("app"))
			{
				BugSenseHandler.initAndStartSession(ItemViewAvtivity.this, APIKEY);
				setContentView(R.layout.item_detailed_view_app);
			}
			else
			{
				BugSenseHandler.initAndStartSession(ItemViewAvtivity.this, APIKEY);
				setContentView(R.layout.item_detailed_view_rep);
			}
		}
		else if(i.getStringExtra("sender").equals("medicine"))
		{
			
			this.setTitle("Medicine Details");
			setContentView(R.layout.item_detailed_view_med);
		}
		else if(i.getStringExtra("sender").equals("appointment"))
		{
			this.setTitle("Appointment Details");
			setContentView(R.layout.item_detailed_view_app);
		}
		else
		{
			this.setTitle("Report Details");
			setContentView(R.layout.item_detailed_view_rep);
		}
		
		TextView name= (TextView)findViewById(R.id.textView1);
		name.setText(i.getStringExtra("name"));
		
		TextView desc= (TextView)findViewById(R.id.textView2);
		desc.setText(i.getStringExtra("desc"));
		
		if(i.getStringExtra("category").equals("med") || i.getStringExtra("category").equals("app"))
		{
			TextView time= (TextView)findViewById(R.id.textView3);
			time.setText(i.getStringExtra("time"));
		}
		
		if(i.getStringExtra("category").equals("med"))
		{
			if(i.getStringExtra("image_source").equals("empty..."))
			{
				// Do Nothing
			}
			else
			{
				ImageView image = (ImageView)findViewById(R.id.imageView1);
				
				image.setImageBitmap(BitmapFactory.decodeFile(i.getStringExtra("image_source")));
			}
		}
		else if(i.getStringExtra("category").equals("app"))
		{
			TextView date= (TextView)findViewById(R.id.textView4);
			date.setText(i.getStringExtra("image_source"));
		}
		else
		{	
			ArrayList<String> a = i.getStringArrayListExtra("rep_images");
			
			if(a.size() > 0)
			{
				final String[] data = new String[a.size()];
				a.toArray(data);
				
				ReportDisplayAdapter myAdapter = new ReportDisplayAdapter(this, R.layout.report_pics, data);
				ListView LV = (ListView)findViewById(R.id.report_picture);
				LV.setAdapter(myAdapter);
				
				LV.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
					{
						Dialog dialog = new Dialog(ItemViewAvtivity.this);
						dialog.setTitle("Report");
						dialog.getWindow().setLayout(200, 200);
						dialog.setContentView(R.layout.image);
						
						ImageView imgView=(ImageView)dialog.findViewById(R.id.fullsizeimage);
						//set image to imgView
						imgView.setImageBitmap(BitmapFactory.decodeFile(data[position]));
						dialog.getWindow().setLayout(700, 800);
						dialog.show();
					}
					
				}
				);
			}
		}
		if(i.getStringExtra("notify").equals("yes"))
		{
			int mNotificationId = i.getIntExtra("request", 0);
			// Gets an instance of the NotificationManager service
			NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.cancel(mNotificationId);
		}
		/*AdView adView = (AdView) findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);*/
	}
	
	public void phone_call(View view)
	{
		String phone_no = i.getStringExtra("phone_no"); 
		
		if (phone_no.length()>0)
        {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
	        callIntent.setData(Uri.parse("tel:" + phone_no));
	        startActivity(callIntent);
        }
	}
	
	public void send_sms(View view)
	{
		String phoneNo = i.getStringExtra("phone_no");
        String message = "Hello, main chutiya houn";                 
        if (phoneNo.length()>0 && message.length()>0)                
            sendSMS(phoneNo, message);                
        else
            Toast.makeText(getBaseContext(), "Please enter both phone number and message.", Toast.LENGTH_SHORT).show();
	}
	
	private void sendSMS(String phoneNumber, String message)
    {        
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);        
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
		BugSenseHandler.closeSession(ItemViewAvtivity.this);
	}
	
}