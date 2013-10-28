package com.example.diyapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.diyapp.R;


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

    Boolean touchFlag=false;
    boolean dropFlag=false;
    LayoutParams imageParams;
    ImageView imageDrop,image1,image2;
    int crashX,crashY;
    Drawable dropDrawable,selectDrawable;
    Rect dropRect,selectRect;
    int topy,leftX,rightX,bottomY;

    int dropArray[];
    public MenuListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d, View list) {
    	activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = list;
        
    }
    
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawable ico;
        HashMap<String, String> map = new HashMap<String, String>();
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.menu_single_element, null);
        
        vi.setMinimumHeight(500);
        TextView title = (TextView)vi.findViewById(R.id.menu_element_text); // title
        View listview = getListView();
        
        
    	map = data.get(position);
        
    	
        	title.setText(map.get(Constant.KEY_OPTION));
            ico = activity.getResources().getDrawable(Integer.parseInt(map.get(Constant.KEY_ICO)));
            
            ImageView ico_image=(ImageView)vi.findViewById(R.id.menu_element_ico); // ico image
            ico_image.setImageDrawable(ico);
        	
        
        	listview.setVerticalFadingEdgeEnabled(false);
        	listview.setVerticalScrollBarEnabled(false);
        	
 	        title.setVisibility(View.GONE);
 	        
        
        
       // vi.setOnTouchListener(dragListener);
        return vi;
    }
    
		
      /*
    	OnTouchListener dragListener = new OnTouchListener() 
    	{
    		@Override
         public boolean onTouch(View v, MotionEvent event) 
         {
             if(touchFlag==true)
             {
                 System.err.println("Display If  Part ::->"+touchFlag);
                 switch (event.getActionMasked()) 
                 {
                 case MotionEvent.ACTION_DOWN :
                	 Log.d("kkams","action_down");
                     break;
                 case MotionEvent.ACTION_MOVE:
                	 Log.d("kkams","action_move");
                     break;  
                 case MotionEvent.ACTION_UP:
                	 Log.d("kkams","action_up");
                     break;
                 default:
                     break;
                 }
             }else
             {
                
             }               
             return true;
         }

		
		
     };*/
    
}
