package com.example.diyapp;

import com.example.diyapp.fragments.ConditionsListAreaFragment;
import com.example.diyapp.fragments.MenuFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


public class Conditions extends FragmentActivity {
	
	private Fragment fragment1;
	private Fragment fragment2;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.user_conditions);
		Log.d("kkams", "onCreate");


		fragment1 = new ConditionsListAreaFragment();
		fragment2 = new MenuFragment();
		
		switchMenuFrag(fragment2);

		switchContentFrag(fragment1);
		
		
		
		

		}
	public void switchMenuFrag(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment, fragment).commit();
	}
	public void switchContentFrag(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.condition_list_area_fragment, fragment).commit();
	}
}
