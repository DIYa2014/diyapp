package com.karbar.diyapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.karbar.diyapp.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MenuListViewAdapter extends BaseAdapter {

    private Activity activity;
    public ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private View listView;
    private int id;
    Boolean touchFlag=false;
    boolean dropFlag=false;
    LayoutParams imageParams;
    ImageView imageDrop,image1,image2;
    int crashX,crashY;
    Drawable dropDrawable,selectDrawable;
    Rect dropRect,selectRect;
    int topy,leftX,rightX,bottomY;
    private ImageView ico_image;
    public View vi;
    private OnTouchListener onTouchListener;
    private boolean isMenu;

   
    public MenuListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d, View list, View.OnTouchListener otl, boolean isMenu) {
    	activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = list;
        onTouchListener = otl;
        this.isMenu = isMenu;
    }
    
    public ArrayList<HashMap<String, String>> getData(){
    	return data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    public View getListView(){
    	return listView;
    }
   
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	if(isMenu){
	    	Drawable ico;
	        HashMap<String, String> map = new HashMap<String, String>();
	        vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.menu_single_element, null);
	        
	        vi.setMinimumHeight(500);
	        TextView title = (TextView)vi.findViewById(R.id.menu_element_text); // title
	        View listview = getListView();
	        
	        
	    	map = data.get(position);
	        
	    	
	    	title.setText(map.get(Constant.KEY_OPTION));
	    	id=Integer.parseInt(map.get(Constant.KEY_ID));
	        ico = activity.getResources().getDrawable(Integer.parseInt(map.get(Constant.KEY_ICO)));
	        
	        ico_image=(ImageView)vi.findViewById(R.id.menu_element_ico); // ico image
	        ico_image.setImageDrawable(ico);
	    	
	    
	    	listview.setVerticalFadingEdgeEnabled(false);
	    	listview.setVerticalScrollBarEnabled(false);
	    	
	        title.setVisibility(View.GONE);
	
	    	vi.setOnTouchListener(onTouchListener);
    	}
    	else{
    		Drawable ico;
	        HashMap<String, String> map = new HashMap<String, String>();
	        vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.conditions_single_element, null);
	        
	        vi.setMinimumHeight(500);
	        View listview = getListView();
	        
	    	map = data.get(position);
	        
	    	id=Integer.parseInt(map.get(Constant.KEY_ID));
	        ico = activity.getResources().getDrawable(Integer.parseInt(map.get(Constant.KEY_ICO)));
	        
	        ico_image=(ImageView)vi.findViewById(R.id.menu_element_ico); // ico image
	        ico_image.setImageDrawable(ico);
	    	
	    
	    	listview.setVerticalFadingEdgeEnabled(false);
	    	listview.setVerticalScrollBarEnabled(false);
    	}
        return vi;
    }
   

      
    	
    
}
