package com.example.diyapp.fragments;



import java.util.ArrayList;
import java.util.HashMap;


import com.example.diyapp.R;
import com.example.diyapp.utils.Constant;
import com.example.diyapp.utils.HorizontalListView;
import com.example.diyapp.utils.ConditionsListViewAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ConditionsListAreaFragment extends Fragment{
	private ConditionsListViewAdapter adapter;
	private Button button;
	private int groupCounter = 0;
	public View view;
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("kkams", "onCreate ConditionsListAreaFragment");
		 
		 view = inflater.inflate(R.layout.main_condition, container, false);
			
			return view;
	 }	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		String[] mainMenu = getResources().getStringArray(R.array.menu);
		button = (Button)getActivity().findViewById(R.id.add_condition_button);
		ArrayList<HashMap<String, String>> list;
		list = new ArrayList<HashMap<String, String>>();
		add(0, android.R.drawable.ic_input_get, list); 
		add(1, android.R.drawable.ic_input_get, list);
		add(2, android.R.drawable.ic_input_get, list);
		add(3, android.R.drawable.ic_input_get, list);
		add(4, android.R.drawable.ic_input_get, list);
		add(5, android.R.drawable.ic_input_get, list);
		
		     
		addGroup(list);
		
		button.setOnClickListener(addConditionButtonListener);
	}
	public void addGroup(ArrayList<HashMap<String, String>> array){
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
		
		HorizontalListView listview = new HorizontalListView(getActivity(), null);
		params.setMargins(30, 20, 30, 0);
		listview.setLayoutParams(params);
		listview.setId(groupCounter);
		LinearLayout ll=(LinearLayout)getActivity().findViewById(R.id.condition_horizontal_list_linear); 
        
		adapter=new ConditionsListViewAdapter(getActivity(), array, view);
        
        listview.setAdapter(adapter);
        ll.addView(listview);
        groupCounter++;
		
	}
	public void add( int id, int ico, ArrayList<HashMap<String, String>> optionList){
		
		HashMap<String, String> map = new HashMap<String, String>(); 
        map.put(Constant.KEY_ID, Integer.toString(id));
        map.put(Constant.KEY_ICO, Integer.toString(ico));
        optionList.add(map);
	}
	public void addElemToList(int ListId){
		HorizontalListView listview = (HorizontalListView)getActivity().findViewById(ListId);
		listview.setBackgroundColor(-16776961);
		
	}
	public void addElemToList(int elemId, int listId){
		HorizontalListView listview = (HorizontalListView)getActivity().findViewById(listId);
		listview.add(listview.getAdapter().getCount()+1, elemId);
		listview.setAdapter(listview.getAdapter());
	}
	public void removeElemfromList(int elemId, int listId){
		HorizontalListView listview = (HorizontalListView)getActivity().findViewById(listId);
		listview.remove(elemId);
		listview.setAdapter(listview.getAdapter());
	}
	View.OnClickListener addConditionButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
	        
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			add(0, android.R.drawable.ic_input_get, list); 
			add(1, android.R.drawable.ic_input_delete, list);
			add(2, android.R.drawable.ic_menu_day, list);
			add(3, android.R.drawable.ic_secure, list);
			add(4, android.R.drawable.ic_menu_zoom, list);
			add(5, android.R.drawable.ic_input_add, list);
			addGroup(list);
			/*test*/
			/*
			if(groupCounter == 4){
				addElemToList(android.R.drawable.ic_media_play, 0);
				addElemToList(android.R.drawable.ic_menu_delete, 0);
				addElemToList(android.R.drawable.ic_media_play, 0);
				addElemToList(android.R.drawable.ic_menu_delete, 0);
				addElemToList(android.R.drawable.ic_media_play, 0);
				addElemToList(android.R.drawable.ic_menu_delete, 0);
				addElemToList(android.R.drawable.ic_media_play, 0);
				addElemToList(android.R.drawable.ic_menu_delete, 0);
				removeElemfromList(1, 1);
				removeElemfromList(2, 1);
				removeElemfromList(3, 1);
			}*/
		}
	};
}
