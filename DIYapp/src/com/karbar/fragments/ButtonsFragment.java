package com.karbar.fragments;

import com.karbar.diyapp.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;



public class ButtonsFragment extends Fragment{
	
	 View mainView;
	 ImageButton mButton1;
	 ImageButton mButton2;
	 Intent mIntent;
	 FragmentManager mFragmentManager;
	 ConditionsFragment content;
	 ActionsFragment actions;
	 ConditionsFragment conditions;
		
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	 mainView = inflater.inflate(R.layout.content_buttons, container, false);
	 mButton1 = (ImageButton) mainView.findViewById(R.id.conditionsButton);
	 mButton2 = (ImageButton) mainView.findViewById(R.id.actionsButton);
	
	
	
	 View.OnClickListener changeToActionsListener= new OnClickListener() {
			    public void onClick(View v) {
			    	 
			    	 mFragmentManager = getFragmentManager(); 
			    	 actions = new ActionsFragment();
			    	 FragmentTransaction transaction = mFragmentManager.beginTransaction();
			    	 transaction.replace(R.id.contentFrag, actions);
			    	 transaction.commit();
			    	
			    	 
			    }
			};
		View.OnClickListener changeToConditionsListener= new OnClickListener() {
			    public void onClick(View v) {
			    	 mFragmentManager = getFragmentManager(); 

					 conditions = new ConditionsFragment();

			    	 mFragmentManager.beginTransaction().replace(R.id.contentFrag, conditions).commit();
			    	 
			    }
			};		
	 mButton1.setOnClickListener(changeToConditionsListener);
	 mButton2.setOnClickListener(changeToActionsListener);
	 
	
	 return mainView;
	 }	
	 
}
