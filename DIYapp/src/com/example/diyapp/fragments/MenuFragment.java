package com.example.diyapp.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.diyapp.R;
import com.example.diyapp.utils.Constant;
import com.example.diyapp.utils.HorizontalListView;
import com.example.diyapp.utils.MenuListViewAdapter;


import android.R.drawable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class MenuFragment extends Fragment{
	private MenuListViewAdapter adapter;
	private ArrayList<HashMap<String, String>> optionList;
	public View view;
	private boolean oneElemDragedFlag = false;
	private boolean ifLongPressed = false;

	private LinearLayout ll;
	private ImageView img;
	private LayoutParams imgParams;
	private Drawable img_drawable;
	private String dragElemId;
	
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
		add(mainMenu[3], 1, android.R.drawable.ic_input_delete); 
		add(mainMenu[4], 1, android.R.drawable.ic_input_delete);
		add(mainMenu[0], 5, android.R.drawable.ic_lock_idle_alarm); 
		add(mainMenu[1], 6, android.R.drawable.ic_menu_camera); 
		add(mainMenu[2], 7, android.R.drawable.ic_media_ff); 
		add(mainMenu[3], 8, android.R.drawable.ic_menu_close_clear_cancel); 
		add(mainMenu[4], 2, android.R.drawable.ic_menu_call);  
		final HorizontalListView listview = (HorizontalListView) getActivity().findViewById(R.id.listview);

        listview.setLongClickable(true);
        ll = new LinearLayout(getActivity());
        img = new ImageView(getActivity());
        imgParams =new LayoutParams(400, 400);

    	LayoutParams params =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        getActivity().addContentView(ll, params);
        adapter=new MenuListViewAdapter(getActivity(), optionList, view, onTouch);
        listview.setAdapter(adapter);
        
        
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
            	ifLongPressed=true;
				
				
            	MenuListViewAdapter a = (MenuListViewAdapter)listview.getAdapter();
            	HashMap<String, String> map = new HashMap<String, String>();
            	map = a.data.get(pos);
            	img_drawable = getActivity().getResources().getDrawable(Integer.parseInt(map.get(Constant.KEY_ICO)));
            	dragElemId = map.get(Constant.KEY_ID);
            	//Log.d("move", "Long_clicked");
            	
    			//Log.d("kkams", "Long click menu - "+ pos);
    			
               return true;
            }
            }
            );

	}
	public void add(String title, int id, int ico){
		
		HashMap<String, String> map = new HashMap<String, String>(); 
        map.put(Constant.KEY_ID, Integer.toString(id));
        map.put(Constant.KEY_OPTION, title);
        map.put(Constant.KEY_ICO, Integer.toString(ico));
        optionList.add(map);
	}
	
	
	public View.OnTouchListener onTouch = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			if(ifLongPressed){
				
				//Log.d("kkams", "po ifie");
				if(oneElemDragedFlag){
					LinearLayout a = (LinearLayout)arg0;
					
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
	            				ll.removeView(img);
	            				ifLongPressed = false;
	            				
	                break;
	            
	            case MotionEvent.ACTION_MOVE:
								//getActivity().addContentView(img, params);
                                int x_cord = (int)event.getRawX();
                                int y_cord = (int)event.getRawY();
                                //Log.d("move","x: "+ x_cord +" y: "+y_cord);
                                //if(x_cord>windowwidth){x_cord=windowwidth;}
                                //if(y_cord>windowheight){y_cord=windowheight;}

                                imgParams.leftMargin = x_cord - img.getWidth()/2;
                                imgParams.topMargin = y_cord-219 - img.getHeight()/2 ;

                                img.setLayoutParams(imgParams);
                                break;
	            default:
                                break;
            }
			}
			else{
				oneElemDragedFlag =true;
				//Log.d("kkams", "przed ifem");
			}
           return true;
		}
};
}

