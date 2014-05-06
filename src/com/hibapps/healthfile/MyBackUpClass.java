package com.hibapps.healthfile;

import java.io.IOException;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;

import android.os.ParcelFileDescriptor;
import android.util.Log;


public class MyBackUpClass extends BackupAgentHelper 
{
	
    @Override
    public void onCreate() 
    {
            Log.d("myLogs", "onCreate called");

            //SharedPreferencesBackupHelper prefs = new SharedPreferencesBackupHelper(this, getPackageName() + "_preferences");
            //addHelper(PreferenceConstants.BACKUP_PREF_KEY, prefs);

            FileBackupHelper hosts = new FileBackupHelper(this, "../databases/" + ourDatabase.DATABASE_NAME);
            addHelper(ourDatabase.DATABASE_NAME, hosts);

            
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,ParcelFileDescriptor newState) throws IOException 
         {
                    super.onBackup(oldState, data, newState);
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
                    ParcelFileDescriptor newState) throws IOException {
            Log.d("ConnectBot.BackupAgent", "onRestore called");

            
                    Log.d("ConnectBot.BackupAgent", "onRestore in-lock");

                    super.onRestore(data, appVersionCode, newState);
            
    }
}
    
