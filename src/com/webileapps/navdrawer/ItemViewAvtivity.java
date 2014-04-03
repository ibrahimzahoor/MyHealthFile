/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webileapps.navdrawer;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ItemViewAvtivity extends SherlockFragmentActivity 
{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		//saving data from previous
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detailed_view);
		
		Intent i=getIntent();
		
		if(i.getStringExtra("sender").equals("dashboard"))
		{
			this.setTitle("Items Details");
		}
		else if(i.getStringExtra("sender").equals("medicine"))
		{
			this.setTitle("Medicine Details");
		}
		else if(i.getStringExtra("sender").equals("report"))
		{
			this.setTitle("Report Details");
		}
		else if(i.getStringExtra("sender").equals("appointment"))
		{
			this.setTitle("Appointment Details");
		}
		
		TextView time= (TextView)findViewById(R.id.textView1);
		time.setText(i.getStringExtra("time"));
		
		TextView name= (TextView)findViewById(R.id.textView2);
		name.setText(i.getStringExtra("name"));
		
		TextView desc= (TextView)findViewById(R.id.textView3);
		desc.setText("desc");
		
		AdView adView = (AdView) findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	    
		
	}


}