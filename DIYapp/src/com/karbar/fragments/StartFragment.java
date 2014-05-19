package com.karbar.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.karbar.dbPack.DbMethods;
import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;
import com.karbar.diyapp.utils.ImageAdapter;
import com.karbar.service.Execute;
import com.karbar.service.Execute_2;

public class StartFragment extends Fragment {

	private View mainView;
	private Button button;
	private ListView listView;
	private ImageAdapter adapter;
	ConditionsFragment conditions;
	private long id;
	DbMethods dbMethods;
	private Intent intent;
	private Intent intent2;
	ArrayList<HashMap<String, String>> data;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.start_view, container, false);
		button = (Button) mainView.findViewById(R.id.addNewDiya);
		button.setOnClickListener(change);
		return mainView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		intent = new Intent(getActivity(), Execute.class);
		intent2 = new Intent(getActivity(), Execute_2.class);
		dbMethods = new DbMethods(getActivity());
		if (!dbMethods.isServiceRunning()) {
			//getActivity().startService(intent);
			
			try {
	             
	            //Create a new PendingIntent and add it to the AlarmManager
				Calendar calendar = Calendar.getInstance();
	            PendingIntent pendingIntent = 
	            			//PendingIntent.getActivity(getActivity(), 123456, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
	            			PendingIntent.getService(getActivity(), 123456, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
	            AlarmManager am =
	                (AlarmManager) getActivity().getSystemService(Activity.ALARM_SERVICE);
	           // 
		            if(PendingIntent.getBroadcast(getActivity(), 123456, intent2, PendingIntent.FLAG_NO_CREATE) == null){
		            	
		            	am.cancel(pendingIntent);
		            	
			            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
			                    2*60*1000, pendingIntent);
			            //am.toString();
			            Log.d("am_service", "Ustawiono alarm manager?: " + am.toString());
			            
			            boolean alarmUp = PendingIntent.getBroadcast(getActivity(), 123456, intent2, PendingIntent.FLAG_NO_CREATE) != null;
			            
			            Log.d("am_service", "Ustawiono alarm manager?, alarmUp: " + alarmUp);
	            }
	            
	            else {
	            	Log.d("am_service", "Nie ustawiono powtornie alarmmanagera");
	            }
	          } catch (Exception e) {
	        	  Log.d("am_service", "Wyjatek przy ustawianiu alarmmanagera");
	          }
			
			
		}
		listView = (ListView) mainView.findViewById(R.id.diyaList);
		if (dbMethods.getDIYaList() != null) {
			data = dbMethods.getDIYaList();
			adapter = new ImageAdapter(getActivity(), data, mainView);
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onDIYaItemClickListener);
		super.onActivityCreated(savedInstanceState);
	}

	
	View.OnClickListener change = new OnClickListener() {
		public void onClick(View v) {

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Bundle bundle = new Bundle();
			id = dbMethods.getNewDIYaID();
			bundle.putLong(Constant.KEY_DIYAID, id);
			Log.d("kkams", "Nadaje nowe id dla DIYi: " + id);
			conditions = new ConditionsFragment();
			conditions.setArguments(bundle);
			ft.replace(R.id.contentFrag, conditions).addToBackStack( "tag" );
			ft.commit();
		}
	};
	public AdapterView.OnItemClickListener onDIYaItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
			final ImageAdapter a = (ImageAdapter) av.getAdapter();
			HashMap<String, String> map = new HashMap<String, String>();
			map = a.data.get(pos);
			final String uniqeID = map.get(Constant.TASKS_KEY_ID);
			final int position = pos;
			final QuickAction mQuickAction = new QuickAction(getActivity());
			ActionItem editItem = new ActionItem(Constant.QUICKACTION_EDIT,
					getString(R.string.quickaction_edit), getResources()
							.getDrawable(R.drawable.ic_edit));
			mQuickAction.addActionItem(editItem);
			ActionItem removeItem = new ActionItem(Constant.QUICKACTION_REMOVE,
					getString(R.string.quickaction_remove), getResources()
							.getDrawable(R.drawable.ic_delete));
			ActionItem runItem = new ActionItem(Constant.QUICKACTION_RUN,
					getString(R.string.quickaction_run), getResources()
							.getDrawable(R.drawable.ic_media_play));
			ActionItem stopItem = new ActionItem(Constant.QUICKACTION_STOP,
					getString(R.string.quickaction_stop), getResources()
							.getDrawable(R.drawable.ic_media_stop));

			final int uniqeID_final = Integer.parseInt(uniqeID);
			
			mQuickAction.addActionItem(removeItem);
			mQuickAction.show(v);
			if (map.get(Constant.TASKS_KEY_ACTIVE).equals("1"))
				mQuickAction.addActionItem(stopItem);
			else
				mQuickAction.addActionItem(runItem);

			mQuickAction
					.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
						@Override
						public void onItemClick(QuickAction quickAction,
								int pos, int actionId) {

							if (actionId == Constant.QUICKACTION_EDIT) {
								Bundle bundle = new Bundle();

								FragmentTransaction ft = getFragmentManager()
										.beginTransaction();

								bundle.putLong(Constant.KEY_DIYAID,
										uniqeID_final);
								conditions = new ConditionsFragment();
								conditions.setArguments(bundle);
								ft.replace(R.id.contentFrag, conditions).addToBackStack( "tag" );

								ft.commit();

							} else if (actionId == Constant.QUICKACTION_REMOVE) {
								data.remove(position);
								dbMethods.deleteDIYa(uniqeID_final);
								listView.setAdapter(adapter);
								listView.setOnItemClickListener(onDIYaItemClickListener);

							} else if (actionId == Constant.QUICKACTION_RUN) {
								data.get(position).put(
										Constant.TASKS_KEY_ACTIVE, "1");
								dbMethods
										.updateDIYa(
												uniqeID_final,
												"1",
												data.get(position)
														.get(Constant.TASKS_KEY_DESCRIPTION),
												data.get(position)
														.get(Constant.TASKS_KEY_NAME_TASKS));
								listView.setAdapter(adapter);
								listView.setOnItemClickListener(onDIYaItemClickListener);
							} else if (actionId == Constant.QUICKACTION_STOP) {
								data.get(position).put(
										Constant.TASKS_KEY_ACTIVE, "0");
								dbMethods
										.updateDIYa(
												uniqeID_final,
												"0",
												data.get(position)
														.get(Constant.TASKS_KEY_DESCRIPTION),
												data.get(position)
														.get(Constant.TASKS_KEY_NAME_TASKS));
								listView.setAdapter(adapter);
								listView.setOnItemClickListener(onDIYaItemClickListener);
							}
						}
					});

		}

	};

}