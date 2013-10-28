package com.example.diyapp.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.diyapp.R;
import com.example.diyapp.utils.Constant;
import com.example.diyapp.utils.HorizontalListView;
import com.example.diyapp.utils.MenuListViewAdapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MenuFragment extends Fragment{
	private MenuListViewAdapter adapter;
	private ArrayList<HashMap<String, String>> optionList;
	public View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("kkams", "onCreate MenuFragment");
		view = inflater.inflate(R.layout.menu, null);
		
		return view;
	}	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		String[] mainMenu = getResources().getStringArray(R.array.menu);
		
		optionList = new ArrayList<HashMap<String, String>>();

		add(mainMenu[0], 0, android.R.drawable.ic_dialog_alert); 
		add(mainMenu[1], 1, android.R.drawable.ic_input_delete); 
		add(mainMenu[2], 2, android.R.drawable.ic_menu_call); 
		add(mainMenu[3], 3, android.R.drawable.ic_media_pause); 
		add(mainMenu[4], 4, android.R.drawable.ic_media_rew);
		add(mainMenu[0], 0, android.R.drawable.ic_lock_idle_alarm); 
		add(mainMenu[1], 1, android.R.drawable.ic_menu_camera); 
		add(mainMenu[2], 2, android.R.drawable.ic_media_ff); 
		add(mainMenu[3], 3, android.R.drawable.ic_menu_close_clear_cancel); 
		add(mainMenu[4], 4, android.R.drawable.ic_menu_mapmode);  
		HorizontalListView listview = (HorizontalListView) getActivity().findViewById(R.id.listview);

        adapter=new MenuListViewAdapter(getActivity(), optionList, view);
        listview.setAdapter(adapter);

        
//		setListAdapter(adapter);

	}
	public void add(String title, int id, int ico){
		
		HashMap<String, String> map = new HashMap<String, String>(); 
        map.put(Constant.KEY_ID, Integer.toString(id));
        map.put(Constant.KEY_OPTION, title);
        map.put(Constant.KEY_ICO, Integer.toString(ico));
        optionList.add(map);
	}
}

