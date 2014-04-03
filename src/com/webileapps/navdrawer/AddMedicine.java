package com.webileapps.navdrawer;

import java.util.Calendar;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddMedicine extends FragmentActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	ourDatabase db = new ourDatabase(this);
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private ImageView mImageView;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	
	public AddMedicine() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmedicine_activity);
		
		final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        
        EditText ed= (EditText)findViewById(R.id.date);
        ed.setText(calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
        
        EditText ed2= (EditText)findViewById(R.id.time);
        ed2.setText( calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        
        findViewById(R.id.date_image).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        findViewById(R.id.time_image).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }
        
        
		
		Spinner courses = (Spinner) findViewById(R.id.choose_dosage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> choose_dosage_adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_dosage_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        choose_dosage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        courses.setAdapter(choose_dosage_adapter);
		
        
     // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	    
		
	}
	
	public void TakePic(View view)
	{
		dispatchTakePictureIntent();
	}
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        mImageView = (ImageView) findViewById(R.id.image);
	        mImageView.setImageBitmap(imageBitmap);
	    }
	}
	
	 @Override
	    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
	        Toast.makeText(this, "new date:" + year + "/" + month + "/" + day, Toast.LENGTH_LONG).show();
	        
	        EditText ed= (EditText)findViewById(R.id.date);
	        ed.setText(year + "/" + month + "/" + day);
	    }

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
	        Toast.makeText(this, "new time:" + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
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
				if(AddMedicineToDB())
				{
					Toast.makeText(getApplicationContext(), "Medicine Added", Toast.LENGTH_LONG).show();
					/*Fragment frg = null;
					frg = getSupportFragmentManager().findFragmentByTag("Awain_m");
					final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.detach(frg);
					ft.attach(frg);
					ft.commit();
					//*/
					
					finish();
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Medicine Cannot be Added", Toast.LENGTH_LONG).show();	
				}
			
				return true; 
				
			case R.id.action_cancel: 
//				db.deleteAllAssesment();
//				refreshList();
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
				

		if((mon.isChecked() || tue.isChecked() || wed.isChecked() || thu.isChecked() || fri.isChecked() || sat.isChecked() || sun.isChecked()) && 
		(name_medicine.getText().toString().length() > 0 && desc_medicine.getText().toString().length() > 0 &&
			start_time_medicine.getText().toString().length() > 0 && date_end_medicine.getText().toString().length() > 0))
		{
			Medicine obj=new Medicine(0, name_medicine.getText().toString(), "", desc_medicine.getText().toString(),
			mon.isChecked(), tue.isChecked(), wed.isChecked(), thu.isChecked(), fri.isChecked(),sat.isChecked(),
			sun.isChecked(), start_time_medicine.getText().toString(), "", false, start_time_medicine.getText().toString());
			
			db.insertMedicine(obj);
			flag = true;
		}
			return flag;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medicine_menu, menu);
       
        return true;
    }

}