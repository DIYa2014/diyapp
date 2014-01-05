package com.karbar.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;


import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;
import com.karbar.diyapp.utils.GridViewAdapter;
import com.karbar.diyapp.utils.HorizontalListView;
import com.karbar.diyapp.utils.MenuListViewAdapter;

import dbPack.DbMethods;

public class ActionsFragment extends Fragment{
	
	private Activity mActivity;
	private MenuListViewAdapter mMenuAdapter;
	private ArrayList<HashMap<String, String>> optionList;
	private ArrayList<HashMap<String, String>> optionList2;
	private View mView;
	private boolean oneElemDragedFlag = false;
	private boolean ifLongPressed = false;
	private String[] mainMenu;
	private LinearLayout ll;
	private ImageView img;
	private LayoutParams imgParams;
	private Drawable img_drawable;
	private String dragElemId;
	private HorizontalListView mMenuListview;
	private int groupCounter = 0;
	private int draggedImgId = -1;
	private int diyaID =-1;

	
	private GridView mGrid;
	private GridViewAdapter mGridAdapter;
	
	private RelativeLayout mButton1;
	private RelativeLayout mButton2;
	private Button buttonAdd;
	private Dialog dialog;

	private FragmentManager mFragmentManager;
	private StartFragment start;
	private ActionsFragment actions;
	private ConditionsFragment conditions;
	private Bundle bundle;
	private DbMethods dbMethods;
		
	 
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		mView = inflater.inflate(R.layout.actions_fragment, container, false);
		dbMethods = new DbMethods(getActivity());
		
		 mButton1 = (RelativeLayout) mView.findViewById(R.id.conditionsButtonRelative);
		 mButton2 = (RelativeLayout) mView.findViewById(R.id.actionsButtonRelative);
		 
		 mButton1.setOnClickListener(changeToConditionsListener);
		 mButton2.setOnClickListener(changeToActionsListener);
		 
	 return mView;
	 }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
			 mActivity = getActivity();
			
			mainMenu = getResources().getStringArray(R.array.menu);
	        
			mActivity = getActivity();
			buttonAdd = (Button)mActivity.findViewById(R.id.add_complete_diya);
			buttonAdd.setOnClickListener(addListener);
			createMenu();
			createDraggedIco(400);
			createGrid();
			bundle = getArguments();
				if (bundle !=null){
					diyaID = Integer.parseInt(String.valueOf(bundle.getLong(Constant.KEY_DIYAID)));
					if(!dbMethods.isDIYaEmpty(diyaID)){
						dbMethods.getActionsLists(diyaID);
					}
					
				}
				
		 
		
		super.onActivityCreated(savedInstanceState);
	}	
	 

	private void createDraggedIco(int size){
		ll = new LinearLayout(getActivity());
	    img = new ImageView(getActivity());
	    imgParams =new LayoutParams(size, size);
	
		LayoutParams params =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getActivity().addContentView(ll, params);
	}	
	private void createGrid(){
		mGrid = (GridView)getActivity().findViewById(R.id.action_grid);
		optionList2 = new ArrayList<HashMap<String, String>>();
		
		
		
		
	
	    mGridAdapter=new GridViewAdapter(getActivity(), optionList2);
	    mGrid.setAdapter(mGridAdapter);
	    
	    
	}
	private void createExistingGrid(ArrayList<HashMap<String, String>>  list){
		mGrid = (GridView)getActivity().findViewById(R.id.action_grid);
	    mGridAdapter=new GridViewAdapter(getActivity(), list);
	    mGrid.setAdapter(mGridAdapter);
	    
	    
	}
	private void createMenu(){
	
		optionList = new ArrayList<HashMap<String, String>>();
		add(mainMenu[1],0, R.drawable.perm_group_network);
		add(mainMenu[1],1, R.drawable.perm_group_user_dictionary);
		add(mainMenu[1],2, R.drawable.ic_audio_ring_notif);
		add(mainMenu[1],3, R.drawable.ic_audio_ring_notif_vibrate);
		mMenuListview = (HorizontalListView) getActivity().findViewById(R.id.listview_action);
		
		mMenuListview.setLongClickable(true);
	
	    mMenuAdapter=new MenuListViewAdapter(getActivity(), optionList, mView, onTouch,true);
	    mMenuListview.setAdapter(mMenuAdapter);
	    
	    mMenuListview.setOnItemLongClickListener(onLongClick);
	    
		
	}
	
	public void add(String title, int id, int ico){
			
			HashMap<String, String> map = new HashMap<String, String>(); 
	        map.put(Constant.KEY_ID, Integer.toString(id));
	        map.put(Constant.KEY_OPTION, title);
	        map.put(Constant.KEY_ICO, Integer.toString(ico));
	        optionList.add(map);
		}
	
	
	private boolean isViewContains(View view, int rx, int ry) {
	    int[] l = new int[2];
	    view.getLocationOnScreen(l);
	    int x = l[0];
	    int y = l[1];
	    int w = view.getWidth();
	    int h = view.getHeight();
	
	    if (rx < x || rx > x + w || ry < y || ry > y + h) {
	        return false;
	    }
	    return true;
	}
	
	public int getListCount(){
		return groupCounter;
	}
	public View getListById(int id){
		return getActivity().findViewById(id);
	}
	
	
	public void add( int id, int ico, ArrayList<HashMap<String, String>> optionList){
		
		HashMap<String, String> map = new HashMap<String, String>(); 
	    map.put(Constant.KEY_ID, Integer.toString(id));
	    map.put(Constant.KEY_ICO, Integer.toString(ico));
	    optionList.add(map);
	}
	
	View.OnClickListener addConditionButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
	        /*
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	
			add(0, android.R.drawable.ic_input_get, list); 
			add(1, android.R.drawable.ic_input_delete, list);
			add(2, android.R.drawable.ic_menu_day, list);
			add(3, android.R.drawable.ic_secure, list);
			add(4, android.R.drawable.ic_menu_zoom, list);
			add(5, android.R.drawable.ic_input_add, list);
			add(0, android.R.drawable.ic_input_get, list); 
			add(1, android.R.drawable.ic_input_delete, list);
			add(2, android.R.drawable.ic_menu_day, list);
			add(3, android.R.drawable.ic_secure, list);
			add(4, android.R.drawable.ic_menu_zoom, list);
			add(5, android.R.drawable.ic_input_add, list);add(0, android.R.drawable.ic_input_get, list); 
			add(1, android.R.drawable.ic_input_delete, list);
			add(2, android.R.drawable.ic_menu_day, list);
			add(3, android.R.drawable.ic_secure, list);
			add(4, android.R.drawable.ic_menu_zoom, list);
			add(5, android.R.drawable.ic_input_add, list);
			addGroup(list);
			*/
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
	
	public AdapterView.OnItemLongClickListener onLongClick = new AdapterView.OnItemLongClickListener() {
	
		 @Override
	     public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
	     	ifLongPressed=true;
				
				
	     	MenuListViewAdapter a = (MenuListViewAdapter)mMenuListview.getAdapter();
	     	HashMap<String, String> map = new HashMap<String, String>();
	     	map = a.data.get(pos);
	     	dragElemId = map.get(Constant.KEY_ID);
	     	draggedImgId = Integer.parseInt(map.get(Constant.KEY_ICO));
	     	img_drawable = mActivity.getResources().getDrawable(draggedImgId);
	     	
				
	        return true;
	     }
	
	};
	public View.OnTouchListener onTouch = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				int x_cord;
				int y_cord;
				if(ifLongPressed){
					if(oneElemDragedFlag){
						
						if(img_drawable!=null)
							img.setBackgroundDrawable(img_drawable);
						
						
			        	imgParams.leftMargin = (int)event.getRawX();
						imgParams.topMargin = (int)event.getRawY();
						
						ll.addView(img, imgParams);
						oneElemDragedFlag = false;
					}
		            switch(event.getAction())
		            {
		            case MotionEvent.ACTION_UP:   
		            				x_cord = (int)event.getRawX();
		            				y_cord = (int)event.getRawY();
		            				if(isViewContains(getActivity().findViewById(R.id.action_grid), x_cord, y_cord)){
		            					
		            					if(Integer.parseInt(dragElemId) == 0)
		            						runDialogWiFi();
		            					else if(Integer.parseInt(dragElemId) == 1)
		            						runDialogNotification();
		            					else if(Integer.parseInt(dragElemId) == 2)
		            						runDialogSoundLevel();
		            					else if(Integer.parseInt(dragElemId) == 3)
	            							vibrationOn();
		            				}
		            				
		            				ll.removeView(img);
		            				ifLongPressed = false;
		            				
		                break;
		            
		            case MotionEvent.ACTION_MOVE:
	                                x_cord = (int)event.getRawX();
	                                y_cord = (int)event.getRawY();
	
	                                imgParams.leftMargin = x_cord - img.getWidth()/2;
	                                imgParams.topMargin = y_cord - img.getHeight()/2 ;
	
	                                img.setLayoutParams(imgParams);
	                                break;
		            default:
	                                break;
	            }
				}
				else{
					oneElemDragedFlag =true;
				}
	           return true;
			}
	};
	 View.OnClickListener changeToActionsListener= new OnClickListener() {
		    public void onClick(View v) {
		    	 
		    	 mFragmentManager = getFragmentManager(); 
		    	 actions = new ActionsFragment();
		    	 FragmentTransaction transaction = mFragmentManager.beginTransaction();
		    	 transaction.replace(R.id.contentFrag, actions);
		    	 transaction.commit();
		    	 
		    	
		    }
		};
		View.OnClickListener addListener= new OnClickListener() {
		    public void onClick(View v) {

		    	 mFragmentManager = getFragmentManager(); 

				 start = new StartFragment();

		    	 mFragmentManager.beginTransaction().replace(R.id.contentFrag, start).commit();

		    	 
		    }
		};	
	View.OnClickListener changeToConditionsListener= new OnClickListener() {
		    public void onClick(View v) {
		    	 mFragmentManager = getFragmentManager(); 
		    	 conditions = new ConditionsFragment();
		    	 conditions.setArguments(bundle);
		    	 FragmentTransaction transaction = mFragmentManager.beginTransaction();
		    	 transaction.replace(R.id.contentFrag, conditions);
		    	 transaction.commit();
		    	 
		    }
		};	
		
		View.OnClickListener anulujListener= new OnClickListener() {
		    public void onClick(View v) {
		    	dialog.cancel();
		    }
		};
	
		public void runDialogWiFi(){
			dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
			dialog.setContentView(R.layout.condition_wifi);
			dialog.show();
			final Switch wifi;
			wifi = (Switch) dialog.findViewById(R.id.switchWiFi);
			final LinearLayout wiFiNameLayout;
			wiFiNameLayout = (LinearLayout)dialog.findViewById(R.id.ifWiFiOn);
			TextView jesli = (TextView)dialog.findViewById(R.id.text_if);
			jesli.setText("On/OFF WiFi");
			TextView name = (TextView)dialog.findViewById(R.id.conectetToWifiNamed);
			name.setText("Polacz z konktretna siecia(opcjonalnie)");
			OnCheckedChangeListener switchListener = new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
						wiFiNameLayout.setVisibility(View.VISIBLE);
					else
						wiFiNameLayout.setVisibility(View.GONE);
						
					
				}
			};
			wifi.setOnCheckedChangeListener(switchListener);
			final EditText et = (EditText)dialog.findViewById(R.id.wifiName);
			Button ok, anuluj;
			ok = (Button)dialog.findViewById(R.id.buttonOkWifi);
			anuluj = (Button)dialog.findViewById(R.id.buttonAnulujWifi);
			anuluj.setOnClickListener(anulujListener);
			ok.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			    	//DbMethods.addWiFiCondition( wifi.isActivated(), et.getText().toString());
					add( 0, draggedImgId, optionList2);
					mGrid.setAdapter(mGridAdapter);
					int tryb = 0;
					String ssid ="";
					if(wifi.isActivated()){
						tryb = 1;
						if(!et.getText().toString().isEmpty()){
							tryb = 2;
							ssid = et.getText().toString();
						}
					}
					long diyaID = bundle.getLong(Constant.KEY_DIYAID);
					dbMethods.addWiFiAction(diyaID,tryb, ssid);
			    	dialog.dismiss();
			    }
			    }
			);
		}
		public void runDialogNotification(){
			dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
			dialog.setContentView(R.layout.action_notification);
			dialog.show();
			
			final EditText ticker = (EditText)dialog.findViewById(R.id.tickerTextNotificationEdit);
			final EditText title = (EditText)dialog.findViewById(R.id.notificationTitleNotificationEdit);
			final EditText content = (EditText)dialog.findViewById(R.id.notificationTextNotificationEdit);
			Button ok, anuluj;
			ok = (Button)dialog.findViewById(R.id.buttonOkNotification);
			anuluj = (Button)dialog.findViewById(R.id.buttonAnulujNotification);
			anuluj.setOnClickListener(anulujListener);
			ok.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			    	//DbMethods.addWiFiCondition( wifi.isActivated(), et.getText().toString());
					add( 0, draggedImgId, optionList2);
					mGrid.setAdapter(mGridAdapter);
					long diyaID = bundle.getLong(Constant.KEY_DIYAID);
					dbMethods.addnotificationAction(diyaID, ticker.getText().toString(), title.getText().toString(), content.getText().toString());
			    	dialog.dismiss();
			    }
			    }
			);
		}
		public void runDialogSoundLevel(){
			dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
			dialog.setContentView(R.layout.action_sound);
			dialog.show();
			
			final SeekBar level = (SeekBar)dialog.findViewById(R.id.setSoundLevel);
			Button ok, anuluj;
			ok = (Button)dialog.findViewById(R.id.buttonOkSound);
			anuluj = (Button)dialog.findViewById(R.id.buttonAnulujSound);
			anuluj.setOnClickListener(anulujListener);
			ok.setOnClickListener(new OnClickListener() {
			    public void onClick(View v) {
			    	//DbMethods.addWiFiCondition( wifi.isActivated(), et.getText().toString());
					add( 0, draggedImgId, optionList2);
					mGrid.setAdapter(mGridAdapter);
					long diyaID = bundle.getLong(Constant.KEY_DIYAID);
					dbMethods.addSoundAction(diyaID, level.getProgress());
			    	dialog.dismiss();
			    }
			    }
			);
		}
		public void vibrationOn(){
					
					add( 0, draggedImgId, optionList2);
					mGrid.setAdapter(mGridAdapter);
					long diyaID = bundle.getLong(Constant.KEY_DIYAID);
					dbMethods.addVibrationAction(diyaID);
			    	
		}
	}
