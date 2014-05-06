package com.hibapps.healthfile;

import android.app.backup.BackupManager;
import android.content.Context;
import android.util.Log;

public class MyBackupManager extends BackupManager 
{

	public MyBackupManager(Context context) 
	{
		super(context);
		
	}
	
	
	@Override
	public void dataChanged ()
	{
		//notifying for data change
		MyBackUpClass backup=new MyBackUpClass();
		backup.notify();
	}
}