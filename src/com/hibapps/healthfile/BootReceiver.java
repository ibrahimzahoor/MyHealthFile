package com.hibapps.healthfile;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{	
		Toast.makeText(context, "Boot Completed, Processing...", Toast.LENGTH_LONG).show();
		
		ourDatabase db = new ourDatabase(context);
		Item[] mathList;
		ArrayList<Medicine> listOfMedicines = db.getAllMedicine();
        ArrayList<Appointment> listOfAppointments = db.getAllAppointment();
        
        mathList = new Item[listOfMedicines.size() + listOfAppointments.size()];
        int j = 0;
		for(int i = 0; i < listOfMedicines.size(); ++i)
		{
			Item obj = new Item( "med", listOfMedicines.get(i).name_of_medicine, listOfMedicines.get(i).medicine_description,
								listOfMedicines.get(i).medicine_alarm_time, listOfMedicines.get(i).medicine_picture);
			obj.id = listOfMedicines.get(i).medicine_id;
			
			mathList[j] = obj;
			++j;
			Toast.makeText(context, "Processing... listOfMediciness " + i , Toast.LENGTH_LONG).show();
		}
		for(int i = 0; i < listOfAppointments.size(); ++i)
		{
			Item obj = new Item( "app", listOfAppointments.get(i).drName, listOfAppointments.get(i).date,
								listOfAppointments.get(i).time, listOfAppointments.get(i).location);
			obj.id = listOfAppointments.get(i).id;
			obj.phone_no = listOfAppointments.get(i).phone_no;
			obj.email = listOfAppointments.get(i).email;
			
			mathList[j] = obj;
			++j;
			Toast.makeText(context, "Processing... listOfAppointments " + i , Toast.LENGTH_LONG).show();
		}
			
		for(int i = 0; i < mathList.length; ++i)
		{
			AlarmManager aM = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Intent myIntent = new Intent(context, AlarmReceiver.class);
			
			if(mathList[i].category.equals("med"))
			{
				myIntent.putExtra("Category", "med");
			}
			else
			{
				myIntent.putExtra("Category", "app");
				myIntent.putExtra("phone_no", mathList[i].phone_no);
				myIntent.putExtra("email", mathList[i].email);
			}
			
			myIntent.putExtra("Name", mathList[i].name);
			myIntent.putExtra("Date", mathList[i].description);
			myIntent.putExtra("Time", mathList[i].time_due);
			myIntent.putExtra("Location", mathList[i].image);
			myIntent.putExtra("Request", mathList[i].id);
			
			PendingIntent pI = PendingIntent.getBroadcast(context, mathList[i].id, myIntent, 0);
		
			Calendar calendar = Calendar.getInstance();
		    calendar.setTimeInMillis(System.currentTimeMillis());
		    long endTime = calendar.getTimeInMillis();
		    if(mathList[i].category.equals("med"))
		    {
			    String[] arr = mathList[i].time_due.split("[:]");
			    
			    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[0]));
			    calendar.set(Calendar.MINUTE, Integer.parseInt(arr[1]));
			    calendar.set(Calendar.SECOND, 1);
		    }
		    else
		    {
		    	String[] arr1 = mathList[i].description.split("[/]");
			    String[] arr2 = mathList[i].time_due.split("[:]");
			    
			    calendar.set(Calendar.YEAR, Integer.parseInt(arr1[2])); 
			    setMonth(calendar, Integer.parseInt(arr1[0]));
			    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr1[1]));
			    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr2[0]));
			    calendar.set(Calendar.MINUTE, Integer.parseInt(arr2[1]));
			    calendar.set(Calendar.SECOND, 1);
		    }
		    long startTime = calendar.getTimeInMillis();
		    if(endTime - startTime <= 0)
		    	aM.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pI);
		    
		    Toast.makeText(context, "Processing... mathList " + i + " " + mathList[i].description + " " + mathList[i].time_due, Toast.LENGTH_LONG).show();
		}
		Toast.makeText(context, "Boot Completed, Done", Toast.LENGTH_LONG).show();
	}
	
	public void setMonth(Calendar c, int no)
	{
		if(no == 1){ c.set(Calendar.MONTH, Calendar.JANUARY); }
		if(no == 2){ c.set(Calendar.MONTH, Calendar.FEBRUARY); }
		if(no == 3){ c.set(Calendar.MONTH, Calendar.MARCH); }
		if(no == 4){ c.set(Calendar.MONTH, Calendar.APRIL); }
		if(no == 5){ c.set(Calendar.MONTH, Calendar.MAY); }
		if(no == 6){ c.set(Calendar.MONTH, Calendar.JUNE); }
		if(no == 7){ c.set(Calendar.MONTH, Calendar.JULY); }
		if(no == 8){ c.set(Calendar.MONTH, Calendar.AUGUST); }
		if(no == 9){ c.set(Calendar.MONTH, Calendar.SEPTEMBER); }
		if(no == 10){ c.set(Calendar.MONTH, Calendar.OCTOBER); }
		if(no == 11){ c.set(Calendar.MONTH, Calendar.NOVEMBER); }
		if(no == 12){ c.set(Calendar.MONTH, Calendar.DECEMBER); }
	}
}