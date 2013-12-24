package dbPack;

import java.util.ArrayList;
import java.util.HashMap;

import com.karbar.diyapp.utils.Constant;

import android.util.Log;

public class DbMethods {
	public static final int getNewDIYaID(){
		//TODO zwraca id dla nowej DIYi
		return 0;
	}
	public static final boolean isDIYaEmpty(int id){
		//TODO zwraaca czy DIA o podanym id ma ju¿ jakie warunki lub akcje
		return true;
	}
	public static final ArrayList<ArrayList<HashMap<String, String>>> getConditonLists(int idDIYa){
		/*
		 * W HashMap masz dane dla pojedyñczego warunku. Zapisauj tam takie rzeczy jak id, nazwe czy adres obraska (R.drawable...).
		 * Klucze masz w klasie Constant, jesli jakichs nie bedzie (np jeli bedziesz dodawal argumenty ) to stworz swoje wlasne klucze
		 * 
		 * Wewnetrzna ArrayLista to grupa, zewnetzna to wczystkie grupy
		 */
		
		return null;
	} 
	public static final ArrayList<HashMap<String, String>> getActionsLists(int idDIYa){
		/*
		 * Tutaj jedna lista HashMap
		 */
		
		return null;
	} 
	
	public static final int addTimeCondition(int groupId, int timeSinceHour, int timeSinceMinutes, int timeToHour, int timeToMinutes, boolean [] days){
		//Log.d("kkams","Od: "+timeSinceHour+":"+timeSinceMinutes);
    	Log.d("kkams",""+groupId);
		
    	//zwraca id z bazy
    	return 1;
		
	}
	public static final int addDateCondition(int groupId, int dateSinceDay, int dateSinceMonth, int dateSinceYear, 
			int dateToDay, int dateToMonth, int dateToYear){
		
    	//zwraca id z bazy
    	return 2;
		
	}
	
	public static final int addGpsCondition(int groupId,double x, double y, double r, boolean isReversed){//isReversed - jesli true to na zewntrz okrgu
		
    	//zwraca id z bazy
    	return 3;
		
	}
	public static final int addWiFiCondition(int groupId, boolean on, String nameWiFi){
		
    	//zwraca id z bazy
    	return 4;
		
	}
	public static final int addWiFiAction(int tryb, String ssid){
		
		return 0;
	}
	public static final int addVibrationAction(){
		
		return 0;
	}
	public static final int addSoundAction(int sounLevel){
			
		return 0;
	}
	public static final int addnotificationAction(String tickerText, String notificationTitle, String notificationText){
			
		return 0;
	}
	
}
