package com.karbar.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;
import com.karbar.diyapp.utils.HorizontalListView;
import com.karbar.diyapp.utils.MenuListViewAdapter;

import dbPack.DbMethods;

public class ConditionsFragment extends Fragment{
	
	private Activity mActivity;
	private View mainView;
	private MenuListViewAdapter mMenuAdapter;
	private ArrayList<HashMap<String, String>> optionList;
	private boolean oneElemDragedFlag = false;
	private boolean ifLongPressed = false;
	private String[] mainMenu;
	private LinearLayout ll;
	private ImageView img;
	private LayoutParams imgParams;
	private Drawable img_drawable;
	private String dragElemId;
	private HorizontalListView mMenuListview;
	private Button button;
	private int groupCounter = 0;
	private int draggedImgId = -1;
	private int draggedId = -1;
	private boolean isFirstGroup = true;
	private Dialog dialog;
	private RelativeLayout mButton1;
	private RelativeLayout mButton2;
	private int diyaID =-1;

	private FragmentManager mFragmentManager;
	private ActionsFragment actions;
	private ConditionsFragment conditions;
	private Bundle bundle;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	 mainView = inflater.inflate(R.layout.conditions_fragment, container, false);
	 mButton1 = (RelativeLayout) mainView.findViewById(R.id.conditionsButtonRelative);
	 mButton2 = (RelativeLayout) mainView.findViewById(R.id.actionsButtonRelative);
	 mButton1.setOnClickListener(changeToConditionsListener);
	 mButton2.setOnClickListener(changeToActionsListener);
	 return mainView;
	 }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 mActivity = getActivity();
			mainMenu = getResources().getStringArray(R.array.menu);

			button = (Button)getActivity().findViewById(R.id.add_condition_button);
			button.setOnClickListener(addConditionButtonListener);
			bundle = getArguments();
			createMenu();
			createDraggedIco(400);
			
			if (bundle !=null){
				diyaID = bundle.getInt(Constant.KEY_DIYAID);
				Log.d("kkams", "Conditions - bundle is not null. DIYa id:" +diyaID);
				if(DbMethods.isDIYaEmpty(diyaID))
					createFirstConditionsList();
				else{
					createConditionsLists(DbMethods.getConditonLists(diyaID));
				}
			}
			else
				createFirstConditionsList();
		
		 
		
		super.onActivityCreated(savedInstanceState);
	}	
	 
	

	private void createDraggedIco(int size){
		ll = new LinearLayout(getActivity());
	    img = new ImageView(getActivity());
	    imgParams =new LayoutParams(size, size);
	
		LayoutParams params =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getActivity().addContentView(ll, params);
	}	
	private void createMenu(){
	
		optionList = new ArrayList<HashMap<String, String>>();
		
		add(mainMenu[0],0, R.drawable.perm_group_system_clock); 
		add(mainMenu[1],1, R.drawable.perm_group_network);
		add(mainMenu[2],2, R.drawable.perm_group_location);
		add(mainMenu[3],3, R.drawable.perm_group_calendar);
		mMenuListview = (HorizontalListView) getActivity().findViewById(R.id.listview);
		
		mMenuListview.setLongClickable(true);
	
	    mMenuAdapter=new MenuListViewAdapter(getActivity(), optionList, mainView, onTouch, true);
	    mMenuListview.setAdapter(mMenuAdapter);
	    
	    mMenuListview.setOnItemLongClickListener(onLongClick);
		
	}
	private void createFirstConditionsList(){
		ArrayList<HashMap<String, String>> list;
		list = new ArrayList<HashMap<String, String>>();
		
		add(-1, R.drawable.empty, list); 
		add(-1, R.drawable.empty, list);
		add(-1, R.drawable.empty, list);
		add(-1, R.drawable.empty, list);
		add(-1, R.drawable.empty, list);
		addGroup(list);
		
	}
	private void createConditionsLists(ArrayList<ArrayList<HashMap<String, String>>> list){
		
		for(int i=0;i<list.size();i++)
			addGroup(list.get(i));
		}
	public void add(String title, int id, int ico){
			
			HashMap<String, String> map = new HashMap<String, String>(); 
	        map.put(Constant.KEY_ID, Integer.toString(id));
	        map.put(Constant.KEY_OPTION, title);
	        map.put(Constant.KEY_ICO, Integer.toString(ico));
	        optionList.add(map);
		}
	public int getContainViewId(int x, int y){
		for(int i = 0; i < groupCounter; i++){
			
			if(isViewContains(getActivity().findViewById(i), x, y))
				return i;
		}
		return -2;
	}
	//sprawdza czy pod podanym x i y znajduje sie view
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
	public void addGroup(ArrayList<HashMap<String, String>> array){
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
		LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		TextView lub = new TextView(getActivity());
		lub.setText(getActivity().getResources().getString(R.string.orText));
		lub.setGravity(Gravity.CENTER_HORIZONTAL);
		
		HorizontalListView listview = new HorizontalListView(getActivity(), null);
		params.setMargins(80, 120, 80, 40);
		textParams.setMargins(0, 40, 0, 40);
		listview.setBackgroundColor(getActivity().getResources().getColor(R.color.halfTransparent));
		listview.setId(groupCounter);
		LinearLayout ll=(LinearLayout)getActivity().findViewById(R.id.condition_horizontal_list_linear); 
	    
		mMenuAdapter=new MenuListViewAdapter(getActivity(), array, mainView,onTouch, false);
		
	    listview.setAdapter(mMenuAdapter);
	    if(!isFirstGroup){
	    	params.setMargins(80, 40, 80, 40);
	    	ll.addView(lub);
	    }
		listview.setLayoutParams(params);
    	isFirstGroup = false;
	    ll.addView(listview);
	    
	    groupCounter++;
		
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
	
			add(-1, R.drawable.empty, list); 
			add(-1, R.drawable.empty, list);
			add(-1, R.drawable.empty, list);
			add(-1, R.drawable.empty, list);
			add(-1, R.drawable.empty, list);
			addGroup(list);
		}
	};
	public AdapterView.OnItemLongClickListener onLongClick = new AdapterView.OnItemLongClickListener() {
	
		 @Override
	     public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
	     	ifLongPressed=true;
				//Log.d("kkams", ""+id);
			draggedId = Integer.parseInt(Long.toString(id));	
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
						
						//Log.d("kkams", dragElemId);
			        	imgParams.leftMargin = (int)event.getRawX();
						imgParams.topMargin = (int)event.getRawY();
						
						ll.addView(img, imgParams);
						oneElemDragedFlag = false;
					}
		            switch(event.getAction())
		            {
		            case MotionEvent.ACTION_UP:   
		            				int id;
		            				x_cord = (int)event.getRawX();
		            				y_cord = (int)event.getRawY();
		            				if(( id = getContainViewId(x_cord, y_cord))!=-2){
		            					Log.d("kkams", ""+draggedId);
		            					if(draggedId == 0){
		            						runDialogTime(id);
		            					}
		            					else if(draggedId == 1){
		            						runDialogWiFi(id);
		            						}
		            					else if(draggedId == 2){
		            						runDialogGPS(id);
		            						}
		            					else if(draggedId == 3){
		            						runDialogDate(id);
		            						}
		            					
		            					
		            				}
		            				
		            				ll.removeView(img);
		            				ifLongPressed = false;
		            				
		                break;
		            
		            case MotionEvent.ACTION_MOVE:
	                                x_cord = (int)event.getRawX();
	                                y_cord = (int)event.getRawY();
	
	                                imgParams.leftMargin = x_cord - img.getWidth()/2;
	                                imgParams.topMargin = y_cord- img.getHeight()/2 ;
	
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
			     actions.setArguments(bundle);
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
	View.OnClickListener anulujListener= new OnClickListener() {
		    public void onClick(View v) {
		    	dialog.cancel();
		    }
		};
	public void removeEmptyElem(int id){

		HorizontalListView listview = (HorizontalListView)getActivity().findViewById(id);
		MenuListViewAdapter a = (MenuListViewAdapter)listview.getAdapter();
		
		HashMap<String, String> h = a.data.get(a.getCount()-1);
		
		
		int elemId = Integer.parseInt(h.get(Constant.KEY_ID));
		
		if(elemId == -1)
			removeElemfromList(a.getCount()-1, id);
	}
	public void runDialogTime(int idGroup){
		dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
		dialog.setContentView(R.layout.condition_time);
		dialog.show();
		final TimePicker tpSince = (TimePicker)dialog.findViewById(R.id.timeSince);
		tpSince.setIs24HourView(true);
		final TimePicker tpTo = (TimePicker)dialog.findViewById(R.id.timeTo);
		tpTo.setIs24HourView(true);
		final CheckBox cb1, cb2,cb3,cb4,cb5,cb6, cb7;
		cb1 = (CheckBox)dialog.findViewById(R.id.day1);
		cb2 = (CheckBox)dialog.findViewById(R.id.day2);
		cb3 = (CheckBox)dialog.findViewById(R.id.day3);
		cb4 = (CheckBox)dialog.findViewById(R.id.day4);
		cb5 = (CheckBox)dialog.findViewById(R.id.day5);
		cb6 = (CheckBox)dialog.findViewById(R.id.day6);
		cb7 = (CheckBox)dialog.findViewById(R.id.day7);
		Button ok, anuluj;
		ok = (Button)dialog.findViewById(R.id.buttonOkTime);
		anuluj = (Button)dialog.findViewById(R.id.buttonAnulujTime);
		final int idGroupFinal = idGroup;
		anuluj.setOnClickListener(anulujListener);
		ok.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	/*Log.d("kkams","Od: "+tpSince.getCurrentHour().toString()+":"+tpSince.getCurrentMinute().toString()+
		    			" Do:"+tpTo.getCurrentHour().toString()+":"+tpTo.getCurrentMinute().toString());
		    	Log.d("kkams",Boolean.toString(cb1.isChecked())+ " " +Boolean.toString(cb2.isChecked())+ " " +Boolean.toString(cb3.isChecked())+ " " +Boolean.toString(cb4.isChecked())+ " " +Boolean.toString(cb5.isChecked())+ " " +Boolean.toString(cb6.isChecked())+ " " +Boolean.toString(cb7.isChecked()));
		    	*/
		    	DbMethods.addTimeCondition(idGroupFinal, tpSince.getCurrentHour(), tpSince.getCurrentMinute(), 
		    			tpTo.getCurrentHour(), tpTo.getCurrentMinute(), new boolean[]{cb1.isChecked(),cb2.isChecked(),cb3.isChecked(),cb4.isChecked(),cb5.isChecked(),cb6.isChecked(),cb7.isChecked()});

		    	dialog.dismiss();
				addElemToList(draggedImgId, idGroupFinal);
				removeEmptyElem(idGroupFinal);
		    }});
		
		
		
	}
	public void runDialogDate(int idGroup){
		dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
		dialog.setContentView(R.layout.condition_date);
		dialog.show();
		
		Button ok, anuluj;
		ok = (Button)dialog.findViewById(R.id.buttonOkDate);
		anuluj = (Button)dialog.findViewById(R.id.buttonAnulujDate);
		final int idGroupFinal = idGroup;
		final DatePicker since = (DatePicker)dialog.findViewById(R.id.datePickerSince);
		final DatePicker to = (DatePicker)dialog.findViewById(R.id.datePickerTo);
		anuluj.setOnClickListener(anulujListener);
		ok.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	DbMethods.addDateCondition(idGroupFinal, since.getDayOfMonth(), since.getMonth(), since.getYear(), 
		    			to.getDayOfMonth(), to.getMonth(), to.getYear());
		    	dialog.dismiss();
				addElemToList(draggedImgId, idGroupFinal);
				removeEmptyElem(idGroupFinal);
		    }});
	}
	public void runDialogWiFi(int idGroup){
		dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
		dialog.setContentView(R.layout.condition_wifi);
		dialog.show();
		final Switch wifi;
		wifi = (Switch) dialog.findViewById(R.id.switchWiFi);
		final LinearLayout wiFiNameLayout;
		wiFiNameLayout = (LinearLayout)dialog.findViewById(R.id.ifWiFiOn);
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
		final int idGroupFinal = idGroup;
		anuluj.setOnClickListener(anulujListener);
		ok.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	DbMethods.addWiFiCondition(idGroupFinal, wifi.isActivated(), et.getText().toString());
		    	dialog.dismiss();
				addElemToList(draggedImgId, idGroupFinal);
				removeEmptyElem(idGroupFinal);
		    }
		    }
		);
	}
	public void runDialogGPS(int idGroup){
		dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
		dialog.setContentView(R.layout.condition_gps);
		dialog.show();

		final EditText etX = (EditText)dialog.findViewById(R.id.xGPS);
		final EditText etY = (EditText)dialog.findViewById(R.id.yGPS);
		final EditText etR = (EditText)dialog.findViewById(R.id.rGPS);
		final CheckBox reversed = (CheckBox)dialog.findViewById(R.id.outsideGPS);
		Button ok, anuluj;
		ok = (Button)dialog.findViewById(R.id.buttonOkGPS);
		anuluj = (Button)dialog.findViewById(R.id.buttonAnulujGPS);
		final int idGroupFinal = idGroup;
		anuluj.setOnClickListener(anulujListener);
		ok.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	DbMethods.addGpsCondition(idGroupFinal,Double.parseDouble(etX.getText().toString()), Double.parseDouble(etY.getText().toString()), Double.parseDouble(etR.getText().toString()), reversed.isChecked());

		    	dialog.dismiss();
				addElemToList(draggedImgId, idGroupFinal);
				removeEmptyElem(idGroupFinal);
		    }});
	}
	
}
