package com.example.diyapp.fragments;

import com.example.diyapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConditionsListAreaFragment extends Fragment{
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("kkams", "onCreate ConditionsListAreaFragment");
		 return inflater.inflate(R.layout.main_condition, container, false);
	 }	
}
