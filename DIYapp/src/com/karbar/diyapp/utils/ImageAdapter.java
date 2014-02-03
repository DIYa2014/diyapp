package com.karbar.diyapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.karbar.diyapp.R;
 
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
import android.widget.RelativeLayout;
import android.widget.TextView;
 
public class ImageAdapter extends BaseAdapter {

    private Activity activity;
    public ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private View listView;
    
    
 
    public ImageAdapter (Activity a, ArrayList<HashMap<String, String>> d, View list) {
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
            vi = inflater.inflate(R.layout.single_diya, parent, false);
        RelativeLayout ll = (RelativeLayout)vi.findViewById(R.id.diya_single_row);
        TextView title = (TextView)vi.findViewById(R.id.diya_name_single_elem); // title
        TextView description = (TextView)vi.findViewById(R.id.diya_desc_single_elem); // title
        View listview = getListView();
        ll.setMinimumHeight(200);

    	map = data.get(position);
        
    	if(map.get(Constant.TASKS_KEY_NAME_TASKS).equals(""))
    		title.setText(map.get(Constant.TASKS_KEY_DATE_CREATE));
    	else
    		title.setText(map.get(Constant.TASKS_KEY_NAME_TASKS));
    	description.setText(map.get(Constant.TASKS_KEY_DESCRIPTION));
        
    	if(map.get(Constant.TASKS_KEY_ACTIVE).equals("1"))
    		ico = activity.getResources().getDrawable(R.drawable.ic_media_play);
    	else 
    		ico = activity.getResources().getDrawable(R.drawable.ic_media_stop);
    		
        
        ImageView ico_image=(ImageView)vi.findViewById(R.id.diya_ico); // ico image
        ico_image.setImageDrawable(ico);
    	
    
    	listview.setVerticalFadingEdgeEnabled(false);
    	listview.setVerticalScrollBarEnabled(false);
    	
        
        return vi;
    }
	
}
