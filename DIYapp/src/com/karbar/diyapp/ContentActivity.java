package com.karbar.diyapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.karbar.fragments.ConditionsFragment;
import com.karbar.fragments.StartFragment;




public class ContentActivity extends FragmentActivity {
	ConditionsFragment conditions;
	StartFragment start;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_view);
		start = new StartFragment() ;
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag, start).commit();
		

		}
	
	
}
