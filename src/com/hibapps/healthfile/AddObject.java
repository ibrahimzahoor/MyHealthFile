package com.hibapps.healthfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class AddObject extends FragmentActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	private String type = "";
	ourDatabase db = new ourDatabase(this);
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private ImageView mImageView;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	
	// For Record of Medicine Image
	private Uri mImageUri;
	private static final String TAG = null;
	File photo = null;
	
	// For Record of Report Images
	ArrayList<Bitmap> record_of_rep_pics = new ArrayList<Bitmap>();

	public AddObject() 
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		type = getIntent().getStringExtra("addition_type");
		
		if(type.equals("medicine"))
		{	
			setContentView(R.layout.addmedicine_activity);
			this.setInitialDateTime();
			this.forMedicine();
			getActionBar().setTitle("Add Medicine");
		}
		else if(type.equals("appointment"))
		{
			setContentView(R.layout.add_appointment);
			this.setInitialDateTime();
			getActionBar().setTitle("Add Appointment");
			 AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.location);
			   autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
		}
		else
		{
			setContentView(R.layout.add_report);
			getActionBar().setTitle("Add Report");
		}
		
        // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	}
	
	public void forMedicine()
	{
		Spinner courses = (Spinner) findViewById(R.id.choose_dosage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> choose_dosage_adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_dosage_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        choose_dosage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        courses.setAdapter(choose_dosage_adapter);
	}
	
	public void setInitialDateTime()
	{
		final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        
        EditText ed= (EditText)findViewById(R.id.date);
        ed.setText(calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
        
        EditText ed2= (EditText)findViewById(R.id.time);
        ed2.setText( calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        
        findViewById(R.id.date_image).setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        findViewById(R.id.time_image).setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
        if (dpd != null) 
        {
            dpd.setOnDateSetListener(this);
        }

        TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
        if (tpd != null) 
        {
            tpd.setOnTimeSetListener(this);
        }
	}
	
	public void TakePic_Medicine(View view)
	{
		if(photo != null)
		{
			photo.delete();
		}
		dispatchTakePictureIntent();
	}

	public void TakePic_Report(View view)
	{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) 
	    {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	private void dispatchTakePictureIntent() 
	{
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    
		try
	    {
	        // place where to store camera taken picture
			photo = this.createTemporaryFile_Medicine("abc", ".jpg");
	    }
	    catch(Exception e)
	    {
	        Log.v(TAG, "Can't create file to take picture!");
	    }
	    mImageUri = Uri.fromFile(photo);
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
	    //start camera intent
	    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}
	
	private File createTemporaryFile_Medicine(String part, String ext) throws Exception
	{
		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyHealthFile_Pics/";
	
		File dir = new File(fullPath);
		if (!dir.exists())
		{
		  dir.mkdirs();
		}
		
		File dir2 = new File(fullPath+"Medicines/");
		if (!dir2.exists())
		{
		  dir2.mkdirs();
		}
	
	    File tempDir= Environment.getExternalStorageDirectory();
	    tempDir=new File(tempDir.getAbsolutePath()+"/MyHealthFile_Pics/Medicines/");
	    if(!tempDir.exists())
	    {
	        tempDir.mkdir();
	    }
	    
	    return File.createTempFile(part, ext, tempDir);
	}
	
	public void grabImage(ImageView imageView)
	{
	    this.getContentResolver().notifyChange(mImageUri, null);
	    ContentResolver cr = this.getContentResolver();
	    Bitmap bitmap;
	    try
	    {
	        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
	        
	        imageView.setImageBitmap(bitmap);
	    }
	    catch (Exception e)
	    {
	        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
	        Log.d(TAG, "Failed to load", e);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(type.equals("report"))
		{
		    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) 
		    {
		        Bundle extras = data.getExtras();
		        Bitmap imageBitmap = (Bitmap) extras.get("data");
		        record_of_rep_pics.add(imageBitmap);
		        mImageView = (ImageView) findViewById(R.id.image);
		        mImageView.setImageBitmap(imageBitmap);
		    }
		}
		else
		{
			if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
		    {
				mImageView = (ImageView) findViewById(R.id.image);   
				this.grabImage(mImageView);
			
				ImageButton iB = (ImageButton) findViewById(R.id.camera_image);
				iB.setEnabled(false);
		    }
		    super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) 
	{
        //Toast.makeText(this, "new date:" + month+1 + "/" + day + "/" + year, Toast.LENGTH_LONG).show();
        
        EditText ed= (EditText)findViewById(R.id.date);
        ed.setText(month+1 + "/" + day + "/" + year);
    }

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) 
	{
        //Toast.makeText(this, "new time:" + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
        EditText ed= (EditText)findViewById(R.id.time);
        ed.setText(hourOfDay + ":" + minute);
    }   
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection 
		switch (item.getItemId()) 
		{ 
			case R.id.action_accept: 
				if(type.equals("medicine"))
				{
					if(AddMedicineToDB())
					{
						Toast.makeText(getApplicationContext(), "Medicine Added", Toast.LENGTH_LONG).show();
						
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Medicine Cannot be Added", Toast.LENGTH_LONG).show();	
					}
				}
				else if(type.equals("appointment"))
				{
					if(AddAppointmentToDB())
					{
						Toast.makeText(getApplicationContext(), "Appointment Added", Toast.LENGTH_LONG).show();
						
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Appointment Cannot be Added", Toast.LENGTH_LONG).show();	
					}
				}
				else if(type.equals("report"))
				{
					if(AddReportToDB())
					{
						Toast.makeText(getApplicationContext(), "Report Added", Toast.LENGTH_LONG).show();
						
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Report Cannot be Added", Toast.LENGTH_LONG).show();	
					}
				}
			
				return true; 
				
			case R.id.action_cancel:
				if(type.equals("medicine"))
				{
					if(photo != null)
					{
						photo.delete();
					}
				}
				finish();
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
				return true;
			
			default: return super.onOptionsItemSelected(item);
		 }
	}
	
	public boolean AddMedicineToDB()
	{
		boolean flag=false;
		EditText name_medicine= (EditText)findViewById(R.id.medicine_name);
		EditText desc_medicine= (EditText)findViewById(R.id.medicine_description);
		EditText start_time_medicine= (EditText)findViewById(R.id.time);
		EditText date_end_medicine= (EditText)findViewById(R.id.date);
		ToggleButton mon = (ToggleButton) findViewById(R.id.monday);
		ToggleButton tue = (ToggleButton) findViewById(R.id.tuesday);
		ToggleButton wed = (ToggleButton) findViewById(R.id.wednesday);
		ToggleButton thu = (ToggleButton) findViewById(R.id.thursday);
		ToggleButton fri = (ToggleButton) findViewById(R.id.friday);
		ToggleButton sat = (ToggleButton) findViewById(R.id.saturday);
		ToggleButton sun = (ToggleButton) findViewById(R.id.sunday);
		Spinner courses = (Spinner) findViewById(R.id.choose_dosage);

		if((mon.isChecked() || tue.isChecked() || wed.isChecked() || thu.isChecked() || fri.isChecked() || sat.isChecked() || sun.isChecked()) && 
		(name_medicine.getText().toString().length() > 0 && desc_medicine.getText().toString().length() > 0 &&
			start_time_medicine.getText().toString().length() > 0 && date_end_medicine.getText().toString().length() > 0))
		{
			String delay = "";
			if(courses.getSelectedItem().toString().equals("Once a Day"))
			{
				delay = "12";
			}
			else if(courses.getSelectedItem().toString().equals("Twice a Day"))
			{
				delay = "8";
			}
			else
			{
				delay = "6";
			}
			
			AlarmManager aM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(this, AlarmReceiver.class);
			int id = -1;
			Cursor c = null;
			if(photo == null)
			{
				Medicine obj=new Medicine(0, name_medicine.getText().toString(), "empty...", desc_medicine.getText().toString(),
						mon.isChecked(), tue.isChecked(), wed.isChecked(), thu.isChecked(), fri.isChecked(),sat.isChecked(),
						sun.isChecked(), start_time_medicine.getText().toString(), delay, false, start_time_medicine.getText().toString());
				
				c = db.insertMedicine(obj);
				
				intent.putExtra("Location", "empty...");
			}
			else
			{
				Medicine obj=new Medicine(0, name_medicine.getText().toString(), photo.getAbsolutePath(), desc_medicine.getText().toString(),
						mon.isChecked(), tue.isChecked(), wed.isChecked(), thu.isChecked(), fri.isChecked(),sat.isChecked(),
						sun.isChecked(), start_time_medicine.getText().toString(), "", false, start_time_medicine.getText().toString());
				
				c = db.insertMedicine(obj);
				
				intent.putExtra("Location", photo.getAbsolutePath());
			}
			if(c.moveToFirst())
			{
				id = Integer.parseInt(c.getString(0).toString());
				
				intent.putExtra("Category", "med");
				intent.putExtra("Name", name_medicine.getText().toString());
				intent.putExtra("Date", desc_medicine.getText().toString());
				intent.putExtra("Time", start_time_medicine.getText().toString());
				intent.putExtra("Request", id);
				
				PendingIntent pI = PendingIntent.getBroadcast(this.getApplicationContext(), id, intent, 0);
				
				// Set Alarm
				Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(System.currentTimeMillis());
			    long endTime = calendar.getTimeInMillis();
			    
			    String[] arr = start_time_medicine.getText().toString().split("[:]");
			    
			    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[0]));
			    calendar.set(Calendar.MINUTE, Integer.parseInt(arr[1]));
			    calendar.set(Calendar.SECOND, 1);
			    long startTime = calendar.getTimeInMillis();
			    
			    if(endTime - startTime <= 0)
			    	aM.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pI);
				
				flag = true;
				
				//Toast.makeText(getApplicationContext(), c.getString(0).toString() + " " + c.getString(1).toString() + " " + c.getString(2).toString(), Toast.LENGTH_LONG).show();
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "Cursor is null", Toast.LENGTH_LONG).show();
			}
		}
		return flag;
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
	
	public boolean AddAppointmentToDB()
	{
		boolean flag=false;
		EditText name_appointment = (EditText)findViewById(R.id.doctor_name);
		EditText loc_appointment = (EditText)findViewById(R.id.location);				
		EditText date_appointment = (EditText)findViewById(R.id.date);
		EditText time_appointment = (EditText)findViewById(R.id.time);
		EditText phn_appointment = (EditText)findViewById(R.id.Phone_num);
		EditText email_appointment = (EditText)findViewById(R.id.email);
		
		if( name_appointment.getText().toString().length() > 0)
		{
			Appointment obj=new Appointment(0, name_appointment.getText().toString(), 
					date_appointment.getText().toString(), time_appointment.getText().toString(), 
					loc_appointment.getText().toString(), phn_appointment.getText().toString(), email_appointment.getText().toString());
			
			int id = -1;
			Cursor c = null;
			c = db.insertAppointment(obj);
			
			if(c.moveToFirst())
			{
				id = Integer.parseInt(c.getString(0).toString());
				
				// Set Alarm
				AlarmManager aM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent(this, AlarmReceiver.class);
				intent.putExtra("Category", "app");
				intent.putExtra("Name", name_appointment.getText().toString());
				intent.putExtra("Location", loc_appointment.getText().toString());
				intent.putExtra("Date", date_appointment.getText().toString());
				intent.putExtra("Time", time_appointment.getText().toString());
				intent.putExtra("Request", id);
				
				PendingIntent pI = PendingIntent.getBroadcast(this.getApplicationContext(), id, intent, 0);
				
			    Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(System.currentTimeMillis());
			    long endTime = calendar.getTimeInMillis();
			    
			    String[] arr1 = date_appointment.getText().toString().split("[/]");
			    String[] arr2 = time_appointment.getText().toString().split("[:]");
			    
			    calendar.set(Calendar.YEAR, Integer.parseInt(arr1[2])); 
			    setMonth(calendar, Integer.parseInt(arr1[0]));
			    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr1[1]));
			    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr2[0]));
			    calendar.set(Calendar.MINUTE, Integer.parseInt(arr2[1]));
			    calendar.set(Calendar.SECOND, 1);
			    long startTime = calendar.getTimeInMillis();
			    
			    if(endTime - startTime <= 0)
			    	aM.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pI);
			    
				flag = true;
				
				//Toast.makeText(getApplicationContext(), c.getString(0).toString() + " " + c.getString(1).toString() + " " + c.getString(2).toString(), Toast.LENGTH_LONG).show();
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "Cursor is null", Toast.LENGTH_LONG).show();
			}
		}
		return flag;
	}
	
	public boolean AddReportToDB()
	{
		boolean flag=false;
		EditText title_report = (EditText)findViewById(R.id.report_title);
		EditText desc_report = (EditText)findViewById(R.id.report_description);
	
		if( title_report.getText().toString().length() > 0)
		{
			ArrayList<String> dummy = new ArrayList<String>();
			Report obj=new Report(0, title_report.getText().toString(), 
					desc_report.getText().toString(), dummy);
			
			String id = "";
			Cursor c = db.insertReport(obj);
			
			if(c.moveToFirst())
			{
				id = c.getString(0).toString();
				
				mImageView = (ImageView) findViewById(R.id.image);
			    if(mImageView.getDrawable() != null)
			    {
			    	//Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
			    	
			    	for(int i = 0; i < record_of_rep_pics.size(); ++i)
			    	{
				    	saveImageToExternalStorage_Report(record_of_rep_pics.get(i), id, i+1); 
			    	}
			    }
			    flag = true;
				//Toast.makeText(getApplicationContext(), c.getString(0).toString() + " " + c.getString(1).toString() + " " + c.getString(2).toString(), Toast.LENGTH_LONG).show();
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "Cursor is null", Toast.LENGTH_LONG).show();
			}
		}
		return flag;
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medicine_menu, menu);
       
        return true;
    }
	
	public void saveImageToExternalStorage_Report(Bitmap image, String id, int index) 
	{
		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyHealthFile_Pics/";
		
		File dir = new File(fullPath);
		if (!dir.exists())
		{
		  dir.mkdirs();
		}
		
		fullPath = fullPath + "Reports/";
		File dir2 = new File(fullPath);
		if (!dir2.exists())
		{
		  dir2.mkdirs();
		}
		
		try
		{	  
			File file = new File(fullPath, id+"_"+index+".jpg");

			if(file.exists())
				file.delete();

			file.createNewFile();
			OutputStream fout = new FileOutputStream(file);
			
			// 100 means no compression, the lower you go, the stronger the compression
			image.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			
			fout.flush();
			fout.close();
			db.savePicturePath_Report(Integer.parseInt(id), fullPath+id+"_"+index+".jpg");
		}
		catch (Exception e)
		{
			 Log.e("saveToExternalStorage()", e.getMessage());
		}
	}
}