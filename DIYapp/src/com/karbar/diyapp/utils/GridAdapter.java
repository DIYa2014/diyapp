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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class GridAdapter extends BaseAdapter {

    private Activity activity;
    public ArrayList<HashMap<String, String>> data; 
    private static LayoutInflater inflater=null;
    private int id;
    Boolean touchFlag=false;
    boolean dropFlag=false;
    LayoutParams imageParams;
    ImageView imageDrop,image1,image2;
    int crashX,crashY;
    Drawable dropDrawable,selectDrawable;
    Rect dropRect,selectRect;
    int topy,leftX,rightX,bottomY;
    private ImageView imageView;
    public View vi;
    public HashMap<String, String> map;

    public GridAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
    	activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
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
    
    
   
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawable ico;
        map = new HashMap<String, String>();
        vi=convertView;
        if(vi == null) 
        {
        if(convertView==null)
            vi = inflater.inflate(R.layout.menu_single_element, null);
        map = data.get(position);
    	id=Integer.parseInt(map.get(Constant.KEY_ID));
    	Log.d("kkams", "id action: "+id);
    	if(map.get(Constant.KEY_ID) != null){
	    	id=Integer.parseInt(map.get(Constant.KEY_ID));
	    	switch(id){
		    	case Constant.ID_WIFI:
			        ico = activity.getResources().getDrawable(Constant.ICO_WIFI);
		    		break;
		    	case Constant.ID_NOTIFICATION:
			        ico = activity.getResources().getDrawable(Constant.ICO_NOTIFICATION);
		    		break;
		    	case Constant.ID_SOUND_LEVEL:
			        ico = activity.getResources().getDrawable(Constant.ICO_SOUND_LEVEL);
		    		break;
		    	case Constant.ID_WIBRATION:
			        ico = activity.getResources().getDrawable(Constant.ICO_WIBRATION);
		    		break;
		    	default:
		    		ico = activity.getResources().getDrawable(Constant.ICO_EMPTY);
		    		break;
	    	}
	    	imageView=(ImageView)vi.findViewById(R.id.menu_element_ico); // ico image
	    	imageView.setImageDrawable(ico);
	        }
    	
        imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        
        }
        return imageView;
        
       
       
    }
   

      
    	
    
}
