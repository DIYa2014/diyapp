package com.example.diyapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.diyapp.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListViewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private View listView;

    public MenuListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d, View list) {
    	activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = list;
        Drawable bg = a.getResources().getDrawable(R.color.white);
		list.setBackgroundDrawable(bg);
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
        Drawable ico, bg;
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
        
        
        
        return vi;
    }
}
