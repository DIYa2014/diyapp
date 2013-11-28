package com.karbar.diyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


import com.karbar.fragments.ButtonsFragment;
import com.karbar.fragments.ConditionsFragment;




public class ContentActivity extends FragmentActivity {
	ConditionsFragment conditions;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_view);
		conditions = new ConditionsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrag, conditions).commit();
		

		}
	
	
}
