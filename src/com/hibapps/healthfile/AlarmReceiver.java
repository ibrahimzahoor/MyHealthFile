package com.hibapps.healthfile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Toast.makeText(context, "AlarmReceiver Processing", Toast.LENGTH_LONG).show();
		Bundle bundle = intent.getExtras();
		String time = bundle.getString("Time");
		String category = bundle.getString("Category");
		
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

		if(alert == null)
		{
		    alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		    if(alert == null) 
		    {  
		        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);                
		    }
		}
		Ringtone r = RingtoneManager.getRingtone(context, alert);
		r.play();
		
		// Vibrate the mobile phone
	    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(2000);
	    
	    Intent resultIntent = null;
	    resultIntent = new Intent(context, ItemViewAvtivity.class);
	    
    	Item obj = new Item( category, bundle.getString("Name"), bundle.getString("Date"),
    							bundle.getString("Time"), bundle.getString("Location"));
    	if(category.equals("med"))
    		resultIntent.putExtra("sender","medicine");
    	else
    		resultIntent.putExtra("sender","appointment");
    	
    	resultIntent.putExtra("category", obj.category);
    	resultIntent.putExtra("name", obj.name);
    	resultIntent.putExtra("desc", obj.description);
    	resultIntent.putExtra("image_source", obj.image);
    	resultIntent.putExtra("time", obj.time_due);
    	resultIntent.putExtra("notify", "yes");
    	resultIntent.putExtra("request", bundle.getInt("Request"));
	    
	    PendingIntent resultPendingIntent =
            PendingIntent.getActivity(context, bundle.getInt("Request"), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
	    mBuilder.setContentTitle("MyHealthFile");
	    
		if(category.equals("med"))
	    {	
    	    mBuilder.setContentText("Take " + obj.name + " Medicine");
	    }
	    else
	    {
	    	mBuilder.setContentText("Time for " + obj.name + " Appointment");
		}
		
		mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		mBuilder.setContentIntent(resultPendingIntent);
		
		// Sets an ID for the notification
		int mNotificationId = bundle.getInt("Request");
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
		Toast.makeText(context, "Alarm & Notification for " + time, Toast.LENGTH_LONG).show();
	}
}