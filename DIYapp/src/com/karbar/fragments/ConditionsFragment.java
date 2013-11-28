package com.karbar.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;
import com.karbar.diyapp.utils.HorizontalListView;
import com.karbar.diyapp.utils.MenuListViewAdapter;

public class ConditionsFragment extends Fragment{
	
	private Activity mActivity;
	private View mainView;
	private MenuListViewAdapter mMenuAdapter;
	private ArrayList<HashMap<String, String>> optionList;
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
	private Button button;
	private int groupCounter = 0;
	private int draggedImgId = -1;
	private int draggedId = -1;
	private ImageButton mActionButton;
	private boolean isFirstGroup = true;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	 mainView = inflater.inflate(R.layout.conditions_fragment, container, false);
	
	 return mainView;
	 }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 mActivity = getActivity();
			mView = getView();

			mainMenu = getResources().getStringArray(R.array.menu);
			createMenu();
			createDraggedIco(400);
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
		add(mainMenu[3],3, R.drawable.stat_notify_email_generic);
		add(mainMenu[4],4, R.drawable.stat_notify_sdcard_usb);
		add(mainMenu[0],5, R.drawable.ic_dialog_alert_holo_dark);
		add(mainMenu[1],6, R.drawable.perm_group_calendar);
		add(mainMenu[2],7, R.drawable.ic_lock_airplane_mode_off);
		mMenuListview = (HorizontalListView) getActivity().findViewById(R.id.listview);
		
		mMenuListview.setLongClickable(true);
	
	    mMenuAdapter=new MenuListViewAdapter(getActivity(), optionList, mView, onTouch, true);
	    mMenuListview.setAdapter(mMenuAdapter);
	    
	    mMenuListview.setOnItemLongClickListener(onLongClick);
		
	}
	private void createFirstConditionsList(){
		button = (Button)getActivity().findViewById(R.id.add_condition_button);
		ArrayList<HashMap<String, String>> list;
		list = new ArrayList<HashMap<String, String>>();
		
		add(0, R.drawable.empty, list); 
		add(0, R.drawable.empty, list);
		add(0, R.drawable.empty, list);
		add(0, R.drawable.empty, list);
		add(0, R.drawable.empty, list);
		addGroup(list);
		
		button.setOnClickListener(addConditionButtonListener);
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
	    
		mMenuAdapter=new MenuListViewAdapter(getActivity(), array, mView,onTouch, false);
	    
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
	public void addElemToList(int ListId){
		HorizontalListView listview = (HorizontalListView)getActivity().findViewById(ListId);
		
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
	
			add(0, R.drawable.empty, list); 
			add(0, R.drawable.empty, list);
			add(0, R.drawable.empty, list);
			add(0, R.drawable.empty, list);
			add(0, R.drawable.empty, list);
			addGroup(list);
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
	/*public View.OnClickListener onActionClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent myIntent = new Intent(ConditionsActivity.getActivity(), ActionsActivity.class);
			//myIntent.putExtra("key", value); //Optional parameters
			ConditionsActivity.getActivity().startActivity(myIntent);
		}
	};*/
	public View.OnTouchListener onTouch = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				int x_cord;
				int y_cord;
				if(ifLongPressed){
					if(oneElemDragedFlag){
						RelativeLayout a = (RelativeLayout)arg0;
						
						if(img_drawable!=null)
							img.setBackgroundDrawable(img_drawable);
						
						Log.d("kkams", dragElemId);
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
		            					addElemToList(draggedImgId, id);
		            					
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
}
