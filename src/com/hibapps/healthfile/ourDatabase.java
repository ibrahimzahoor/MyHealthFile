package com.hibapps.healthfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ourDatabase extends SQLiteOpenHelper
{
	// Database Version
    private static final int DATABASE_VERSION = 1;
    
    // Database Name
    public static final String DATABASE_NAME = "myHealthFile_";
    
    // For Debugging
 	static final String myLogs = null;
 	
 	// DB Table Names
 	private static final String Med_Table = "medicines";
 	private static final String App_Table = "appointments";
 	private static final String Rep_Table = "reports";
	private static final String Rep_Images_Table = "reports_images";
 	
 	// Columns of Medicine Table
 	private static final String Medicine_ID = "medicine_id";
 	private static final String Name_of_Medicine = "name_of_medicine";
 	private static final String Medicine_Picture = "medicine_picture";
 	private static final String Medicine_Description = "medicine_description";
 	private static final String Is_Monday = "medicine_is_monday";
 	private static final String Is_Tuesday = "medicine_is_tuesday";
 	private static final String Is_Wednesday = "medicine_is_wednesday";
 	private static final String Is_Thursday = "medicine_is_thursday";
 	private static final String Is_Friday = "medicine_is_friday";
 	private static final String Is_Saturday = "medicine_is_saturday";
 	private static final String Is_Sunday = "medicine_is_sunday";
 	private static final String Start_Time = "medicine_start_time";
 	private static final String Delay = "medicine_delay";
 	private static final String Is_Custom = "medicine_is_custom";
 	private static final String Alarm_Time = "medicine_alarm_time";
 	
 	// Columns of Appointment Table
 	private static final String Appointment_ID = "appointment_id";
 	private static final String Name_of_Doctor = "name_of_doctor";
 	private static final String Appointment_Date = "appointment_date";
 	private static final String Appointment_Time = "appointment_time";
 	private static final String Appointment_Location = "location_";
 	private static final String Appointment_Phone_No = "appointment_phone_no";
 	private static final String Appointment_Email = "appointment_email";
 	
 	// Columns of Report Table
 	private static final String Report_ID = "report_id";
 	private static final String Report_Title = "report_title";
 	private static final String Report_Description = "report_description";
 	
 	// Columns of Report Helper Table Images
 	private static final String Report_Images = "image";

 	// Constructor
	public ourDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	// on Create Function
	public void onCreate(SQLiteDatabase db) 
	{
		Log.v(myLogs, "1");
		String med_table_query = "CREATE TABLE " + Med_Table + " ( " +
				Medicine_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Name_of_Medicine + " TEXT, " +
				Medicine_Picture + " TEXT, " +
				Medicine_Description + " TEXT, " +
				Is_Monday + " INTEGER, " +
				Is_Tuesday + " INTEGER, " +
				Is_Wednesday + " INTEGER, " +
				Is_Thursday + " INTEGER, " +
				Is_Friday + " INTEGER, " +
				Is_Saturday + " INTEGER, " +
				Is_Sunday + " INTEGER, " +
				Start_Time + " TEXT, " +
				Delay + " TEXT, " +
				Is_Custom + " INTEGER, " +
				Alarm_Time + " TEXT)";
		db.execSQL( med_table_query );
		
		Log.v(myLogs, "2");
		String app_table_query = "CREATE TABLE " + App_Table + " ( " +
				Appointment_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Name_of_Doctor + " TEXT, " +
				Appointment_Date + " TEXT, " +
				Appointment_Time + " TEXT, " +
				Appointment_Location + " TEXT, " +
				Appointment_Phone_No + " TEXT," +
				Appointment_Email + " TEXT)";
		db.execSQL( app_table_query );
		
		Log.v(myLogs, "3");
		String rep_table_query = "CREATE TABLE " + Rep_Table + " ( " +
				Report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Report_Title + " TEXT, " +
				Report_Description + " TEXT )";
		db.execSQL( rep_table_query );
		
		Log.v(myLogs, "4");
		String rep_images_table_query = "CREATE TABLE " + Rep_Images_Table + " ( " +
				Report_ID + " INTEGER, " +
				Report_Images + " TEXT )";
		db.execSQL( rep_images_table_query );
		
		Log.v(myLogs, "5");
	}

	@Override
	// on Upgrade Function
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + Med_Table );
		db.execSQL("DROP TABLE IF EXISTS " + App_Table );
		db.execSQL("DROP TABLE IF EXISTS " + Rep_Table );
		db.execSQL("DROP TABLE IF EXISTS " + Rep_Images_Table );
	}
	
	// Insert Medicine
	public Cursor insertMedicine(Medicine obj)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
        ContentValues values = new ContentValues();
        
        // Inserting values in ContentValues
        values.put("name_of_medicine", obj.name_of_medicine);
        values.put("medicine_picture", obj.medicine_picture);
        values.put("medicine_description", obj.medicine_description);
        if(obj.medicine_is_monday == true)
        	values.put("medicine_is_monday", 1);
        else
        	values.put("medicine_is_monday", 0);
        if(obj.medicine_is_tuesday == true)
        	values.put("medicine_is_tuesday", 1);
        else
        	values.put("medicine_is_tuesday", 0);
        if(obj.medicine_is_wednesday == true)
        	values.put("medicine_is_wednesday", 1);
        else
        	values.put("medicine_is_wednesday", 0);
        if(obj.medicine_is_thursday == true)
        	values.put("medicine_is_thursday", 1);
        else
        	values.put("medicine_is_thursday", 0);
        if(obj.medicine_is_friday == true)
        	values.put("medicine_is_friday", 1);
        else
        	values.put("medicine_is_friday", 0);
        if(obj.medicine_is_saturday == true)
        	values.put("medicine_is_saturday", 1);
        else
        	values.put("medicine_is_saturday", 0);
        if(obj.medicine_is_sunday == true)
        	values.put("medicine_is_sunday", 1);
        else
        	values.put("medicine_is_sunday", 0);
        values.put("medicine_start_time", obj.medicine_start_time);
		values.put("medicine_delay", obj.medicine_delay);
		if(obj.medicine_is_custom == true)
        	values.put("medicine_is_custom", 1);
        else
        	values.put("medicine_is_custom", 0);
		values.put("medicine_alarm_time", obj.medicine_alarm_time);
 	
        // Inserting Row
        db.insert(Med_Table, null, values);
        
        // Closing database connection
        db.close(); 
        
        db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM medicines ORDER BY medicine_id DESC LIMIT 1;", null);
        
        return cursor;
    }
	
	// Insert Appointment
	public Cursor insertAppointment(Appointment obj)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
        ContentValues values = new ContentValues();
        
        // Inserting values in ContentValues
        values.put(Name_of_Doctor, obj.drName);
        values.put(Appointment_Date, obj.date);
        values.put(Appointment_Time, obj.time);
        values.put(Appointment_Location, obj.location);
        values.put(Appointment_Phone_No, obj.phone_no);
        values.put(Appointment_Email, obj.email);
        
        // Inserting Row
        db.insert(App_Table, null, values);
        
        // Closing database connection
        db.close(); 
        
        db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM appointments ORDER BY appointment_id DESC LIMIT 1;", null);
        
        return cursor;
	}
	
	// Insert Report
	public Cursor insertReport(Report obj)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
        ContentValues values = new ContentValues();
        values.put(Report_Title, obj.title);
        values.put(Report_Description, obj.desciption);
        
        // Inserting Row
        db.insert(Rep_Table, null, values);
        db.close(); // Closing database connection
        
        db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM reports ORDER BY report_id DESC LIMIT 1;", null);
        
        return cursor;
	}
	
	// Save Report Picture Path
	public void savePicturePath_Report(int id, String path)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	
		ContentValues _values = new ContentValues();
        _values.put(Report_ID, id);
        _values.put(Report_Images, path); 
        
        // Inserting Row
        db.insert(Rep_Images_Table, null, _values);
         
    	db.close(); // Closing database connection
	}
	
	// get All Medicines
	public ArrayList<Medicine> getAllMedicine() 
    {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM medicines",null);
		
		ArrayList<Medicine> myList = new ArrayList<Medicine>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				Medicine obj = new Medicine( Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
						cursor.getString(3), true, true, true, true, true, true, true, "", "", true, cursor.getString(14));
				
				myList.add(obj);
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
	
	// get All Appointments
	public ArrayList<Appointment> getAllAppointment() 
    {
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(App_Table, null, null, null, null, null, null);
        
        ArrayList<Appointment> myList = new ArrayList<Appointment>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				Appointment obj = new Appointment(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2), cursor.getString(3), 
						cursor.getString(4), cursor.getString(5), cursor.getString(6));
				
				myList.add(obj);
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
	
	// get All Reports
	public ArrayList<Report> getAllReport() 
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Rep_Table, null, null, null, null, null, null);
        
        ArrayList<Report> myList = new ArrayList<Report>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				Report obj = new Report(Integer.parseInt(cursor.getString(0)), cursor.getString(1), 
						cursor.getString(2), this.getAllReportImages(Integer.parseInt(cursor.getString(0))));
				
				myList.add(obj);
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
	
	// get All Images of a Report
	public ArrayList<String> getAllReportImages(int id) 
    {
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Rep_Images_Table, new String[] { "image" }, Report_ID + " = ?", new String[] { 
        		String.valueOf(id) }, null, null, null);
        
        ArrayList<String> myList = new ArrayList<String>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				myList.add(cursor.getString(0));
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
	
	// It gets Top 5(w.r.t time) Medicines of current date
	public ArrayList<Medicine> getAllMedicine_Selected() 
    {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.MONDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Monday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else if (day == Calendar.TUESDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Tuesday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else if (day == Calendar.WEDNESDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Wednesday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else if (day == Calendar.THURSDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Thursday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else if (day == Calendar.FRIDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Friday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else if (day == Calendar.SATURDAY)
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Saturday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		else
		{
			cursor = db.query(Med_Table, new String[] { "medicine_id", "name_of_medicine", "medicine_picture", 
					"medicine_description", "medicine_alarm_time"}, Is_Sunday + " = ?", new String[] { 
					"1" }, null, null, Alarm_Time + " ASC LIMIT 5");
		}
		
		ArrayList<Medicine> myList = new ArrayList<Medicine>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				Medicine obj = new Medicine( Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
						cursor.getString(3), true, true, true, true, true, true, true, "", "", true, cursor.getString(4));
				
				myList.add(obj);
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
	
	// It gets Top 5(w.r.t time) Appointments of current date
	public ArrayList<Appointment> getAllAppointment_Selected() 
    {
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date current_date = Calendar.getInstance().getTime();        
		String is_alarm_today = df.format( current_date );
        
		StringBuilder sb = new StringBuilder(is_alarm_today);
		if(sb.charAt(0) == '0')
		{
			sb.deleteCharAt(0);
		}
		if(sb.charAt(2) == '0')
		{
			sb.deleteCharAt(2);
		}
		String resultString = sb.toString();
		
        cursor = db.query(App_Table, null, Appointment_Date + " = ?", new String[] { 
        		resultString }, null, null, Appointment_Time + " ASC LIMIT 5");
		
        ArrayList<Appointment> myList = new ArrayList<Appointment>();
		
		if(cursor.moveToFirst())
		{
			do
			{
				Appointment obj = new Appointment(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2), cursor.getString(3), 
						cursor.getString(4), cursor.getString(5), cursor.getString(6));
				
				myList.add(obj);
			}
			while(cursor.moveToNext());
		}
		
        return myList;
    }
		
	// Delete All
	public void deleteAll()
	{
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Med_Table, null, null);
        db.delete(App_Table, null, null);
        db.delete(Rep_Table, null, null);
        db.delete(Rep_Images_Table, null, null);
        
        db.close();
	}
	
	// Get Image for Medicine
	public String getImage(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(Med_Table, new String[] { "medicine_picture" }, "medicine_id = ?", new String[] { 
					String.valueOf(id) }, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getString(0).toString();
		else
			return "";
	}

	// Delete a Category
	public void deleteCategory(int id, String category)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		if(category.equals("medicine"))
		{
			db.delete(Med_Table, "medicine_id = ?", new String[] { String.valueOf(id) });
		}
		else if(category.equals("appointment"))
		{
			db.delete(App_Table, "appointment_id = ?", new String[] { String.valueOf(id) });
		}
		else
		{
			db.delete(Rep_Images_Table, "report_id = ?", new String[] { String.valueOf(id) });
			db.delete(Rep_Table, "report_id = ?", new String[] { String.valueOf(id) });
		}
		
		db.close();
	}
}