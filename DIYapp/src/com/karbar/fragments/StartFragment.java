package com.karbar.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;
import com.karbar.diyapp.utils.HorizontalListView;
import com.karbar.diyapp.utils.ImageAdapter;
import com.karbar.diyapp.utils.MenuListViewAdapter;
import com.karbar.service.Execute;

import dbPack.DbMethods;

public class StartFragment extends Fragment{
	
	
	private View mainView;
	private Button button;
	private ListView listView;
	private ImageAdapter adapter;
	ConditionsFragment conditions;
	private long id;
	DbMethods dbMethods;
	private Intent intent;
	private Activity a;
	ArrayList<HashMap<String, String>> data;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	 mainView = inflater.inflate(R.layout.start_view, container, false);
	 intent = new Intent(getActivity(), Execute.class);

	 dbMethods = new DbMethods(getActivity());
	 Log.d("kkams", "is runing? "+dbMethods.isServiceRunning());

	 if(!dbMethods.isServiceRunning()){
			getActivity().startService(intent);
	 }
	 button = (Button)mainView.findViewById(R.id.addNewDiya);
	 button.setOnClickListener(change);
	 return mainView;
	 }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		

		 listView = (ListView)mainView.findViewById(R.id.diyaList);
		 if(dbMethods.getDIYaList() != null){
			 data = dbMethods.getDIYaList();
			 adapter = new ImageAdapter(getActivity(), data, mainView);
			 
		 }
		 listView.setAdapter(adapter);
		 listView.setOnItemClickListener(onDIYaItemClickListener);
		super.onActivityCreated(savedInstanceState);
	}	
	View.OnClickListener change= new OnClickListener() {
	    public void onClick(View v) {
	    	
	    	FragmentTransaction ft = getFragmentManager().beginTransaction();
	    	//ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
	    	Bundle bundle = new Bundle();

	    	id = dbMethods.getNewDIYaID();   
	    	
	    	bundle.putLong(Constant.KEY_DIYAID, id );
	    	Log.d("kkams", "Nadaje nowe id dla DIYi: "+ id);
			 conditions = new ConditionsFragment();
			 conditions.setArguments(bundle);
	    	ft.replace(R.id.contentFrag, conditions);

	    	// Start the animated transition.
	    	ft.commit();
	    	/*
	    	 FragmentManager mFragmentManager = getFragmentManager(); 

			 conditions = new ConditionsFragment();

	    	 mFragmentManager.beginTransaction().replace(R.id.contentFrag, conditions).commit();
*/
	    	 
	    }
	};	
	public AdapterView.OnItemClickListener onDIYaItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
			
			final ImageAdapter a = (ImageAdapter)av.getAdapter();
	     	HashMap<String, String> map = new HashMap<String, String>();
	     	map = a.data.get(pos);
		     	final String uniqeID = map.get(Constant.TASKS_KEY_ID);
		     	Log.d("kkams", uniqeID);
		     	final int position = pos;
				ActionItem editItem 		= new ActionItem(Constant.QUICKACTION_EDIT, getString(R.string.quickaction_edit), getResources().getDrawable(R.drawable.ic_edit));
				ActionItem removeItem 	= new ActionItem(Constant.QUICKACTION_REMOVE, getString(R.string.quickaction_remove), getResources().getDrawable(R.drawable.ic_delete));
				ActionItem runItem 	= new ActionItem(Constant.QUICKACTION_RUN, getString(R.string.quickaction_run), getResources().getDrawable(R.drawable.ic_media_play));
				ActionItem stopItem 	= new ActionItem(Constant.QUICKACTION_STOP, getString(R.string.quickaction_stop), getResources().getDrawable(R.drawable.ic_media_stop));
			     
				final QuickAction mQuickAction 	= new QuickAction(getActivity());
				final int uniqeID_final = Integer.parseInt(uniqeID);
				mQuickAction.addActionItem(editItem);
				mQuickAction.addActionItem(removeItem);
				mQuickAction.show(v);
				if(map.get(Constant.TASKS_KEY_ACTIVE).equals("1"))
					mQuickAction.addActionItem(stopItem);
				else
					mQuickAction.addActionItem(runItem);
					
					
				mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos, int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);
					
						if (actionId == Constant.QUICKACTION_EDIT) {
							Bundle bundle = new Bundle();

					    	FragmentTransaction ft = getFragmentManager().beginTransaction();
					    	
					    	bundle.putLong(Constant.KEY_DIYAID, uniqeID_final );
					    	conditions = new ConditionsFragment();
							conditions.setArguments(bundle);
					    	ft.replace(R.id.contentFrag, conditions);

					    	ft.commit();
							
						} 
						else if(actionId == Constant.QUICKACTION_REMOVE) {
							 data.remove(position);
							 dbMethods.deleteDIYa(uniqeID_final);
							 listView.setAdapter(adapter);
							 listView.setOnItemClickListener(onDIYaItemClickListener);
						    
						} 
						else if(actionId == Constant.QUICKACTION_RUN) {
							 data.get(position).put(Constant.TASKS_KEY_ACTIVE, "1");
							 dbMethods.updateDIYa(uniqeID_final, "1",data.get(position).get(Constant.TASKS_KEY_DESCRIPTION) , data.get(position).get(Constant.TASKS_KEY_NAME_TASKS));
							 listView.setAdapter(adapter);
							 listView.setOnItemClickListener(onDIYaItemClickListener);
						} 
						else if(actionId == Constant.QUICKACTION_STOP) {
							 data.get(position).put(Constant.TASKS_KEY_ACTIVE, "0");
							 dbMethods.updateDIYa(uniqeID_final, "0",data.get(position).get(Constant.TASKS_KEY_DESCRIPTION) , data.get(position).get(Constant.TASKS_KEY_NAME_TASKS));
							 listView.setAdapter(adapter);
							 listView.setOnItemClickListener(onDIYaItemClickListener);
						} 
					} 
				});
	     	
		}
		
	};
	 
}