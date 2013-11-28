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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class ButtonsFragment extends Fragment{
	
	 View mainView;
	 RelativeLayout mButton1;
	 RelativeLayout mButton2;
	 TextView mButtonText1;
	 TextView mButtonText2;
	 ImageView mButtonImage1;
	 ImageView mButtonImage2;

	 ImageView mButtonArrow1;
	 ImageView mButtonArrow2;
	 Intent mIntent;
	 FragmentManager mFragmentManager;
	 ConditionsFragment content;
	 ActionsFragment actions;
	 ConditionsFragment conditions;
		
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	 mainView = inflater.inflate(R.layout.content_buttons, container, false);
	 mButton1 = (RelativeLayout) mainView.findViewById(R.id.conditionsButtonRelative);
	 mButton2 = (RelativeLayout) mainView.findViewById(R.id.actionsButtonRelative);
	 mButtonText1 = (TextView)mainView.findViewById(R.id.textViewCondition);
	 mButtonText2 = (TextView)mainView.findViewById(R.id.textViewAction);
	 mButtonImage1 = (ImageView)mainView.findViewById(R.id.imageViewCondition);
	 mButtonImage2 = (ImageView)mainView.findViewById(R.id.imageViewAction);
	 mButtonArrow1 = (ImageView)mainView.findViewById(R.id.arrowCondition);
	 mButtonArrow2 = (ImageView)mainView.findViewById(R.id.arrowAction);
	
	
	 View.OnClickListener changeToActionsListener= new OnClickListener() {
			    public void onClick(View v) {
			    	 
			    	 mFragmentManager = getFragmentManager(); 
			    	 actions = new ActionsFragment();
			    	 FragmentTransaction transaction = mFragmentManager.beginTransaction();
			    	 transaction.replace(R.id.contentFrag, actions);
			    	 transaction.commit();
			    	 
			    	 mButtonText1.setTextColor(getActivity().getResources().getColor(R.color.white));
			    	 mButtonText2.setTextColor(getActivity().getResources().getColor(R.color.orange));
			    	 mButton1.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.inactive_menu));
			    	 mButton2.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
			    	 mButtonImage1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.conditions_white));
			    	 mButtonImage2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.actions_orange));
			    	 mButtonArrow1.setVisibility(View.GONE);
			    	 mButtonArrow2.setVisibility(View.VISIBLE);
			    }
			};
		View.OnClickListener changeToConditionsListener= new OnClickListener() {
			    public void onClick(View v) {
			    	 mFragmentManager = getFragmentManager(); 

					 conditions = new ConditionsFragment();

			    	 mFragmentManager.beginTransaction().replace(R.id.contentFrag, conditions).commit();

			    	 mButtonText2.setTextColor(getActivity().getResources().getColor(R.color.white));
			    	 mButtonText1.setTextColor(getActivity().getResources().getColor(R.color.orange));
			    	 mButton2.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.inactive_menu));
			    	 mButton1.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

			    	 mButtonImage1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.conditions_orange));
			    	 mButtonImage2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.actions_white));
			    	 mButtonArrow2.setVisibility(View.GONE);
			    	 mButtonArrow1.setVisibility(View.VISIBLE);
			    }
			};		
	 mButton1.setOnClickListener(changeToConditionsListener);
	 mButton2.setOnClickListener(changeToActionsListener);
	 
	
	 return mainView;
	 }	
	 
}
