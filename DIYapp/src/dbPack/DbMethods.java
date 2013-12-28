package dbPack;

import java.util.ArrayList;
import java.util.HashMap;

//import com.example.testbazy.model.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;

public class DbMethods {
	private Context context;
	private Activity activity;
	Database db;
	
	public DbMethods(Context c, Activity a){
		context = c;
		activity = a;
		db = new Database (context, activity);
		db.open();
		
	}
	
	public long getNewDIYaID(){
		//TODO zwraca id dla nowej DIYi
		return db.insertTask();
	}
	public boolean isDIYaEmpty(long id){
		//TODO zwraaca czy DIA o podanym id ma ju¿ jakie warunki lub akcje
		//sprawdzam, czy ma jakies warunki i czy ma jakies akcje, jesli nie ma obu - zwracam true, zwracam true tez gdy nie ma takiego zadania
		return db.isTaskEmpty(id);
	}
	public ArrayList<ArrayList<HashMap<String, String>>> getConditonLists(int idDIYa){
		/*
		 * W HashMap masz dane dla pojedyñczego warunku. Zapisauj tam takie rzeczy jak id, nazwe czy adres obraska (R.drawable...).
		 * Klucze masz w klasie Constant, jesli jakichs nie bedzie (np jeli bedziesz dodawal argumenty ) to stworz swoje wlasne klucze
		 * 
		 * Wewnetrzna ArrayLista to grupa, zewnetzna to wczystkie grupy
		 * 
		 */
		
	
		return db.getArrayAddedConditionFromDatabase(idDIYa);
	} 
	public ArrayList<HashMap<String, String>> getActionsLists(long idDIYa){
		/*
		 * Tutaj jedna lista HashMap
		 */
		
		return db.getArrayAddedActionsFrmDatabase(idDIYa);
	} 
	
	public int addTimeCondition(int groupId, int timeSinceHour, int timeSinceMinutes, int timeToHour, int timeToMinutes, boolean [] days){
		//Log.d("kkams","Od: "+timeSinceHour+":"+timeSinceMinutes);
    	Log.d("kkams",""+groupId);
		
    	//zwraca id z bazy
    	return 1;
		
	}
	public int addDateCondition(int groupId, int dateSinceDay, int dateSinceMonth, int dateSinceYear, 
			int dateToDay, int dateToMonth, int dateToYear){
		
    	//zwraca id z bazy
    	return 2;
		
	}
	
	public int addGpsCondition(int groupId,double x, double y, double r, boolean isReversed){//isReversed - jesli true to na zewntrz okrgu
		
    	//zwraca id z bazy
    	return 3;
		
	}
	public int addWiFiCondition(int groupId, boolean on, String nameWiFi){
		
    	//zwraca id z bazy
    	return 4;
		
	}
	public int addWiFiAction(int tryb, String ssid){
		
		return 0;
	}
	public int addVibrationAction(){
		
		return 0;
	}
	public int addSoundAction(int sounLevel){
			
		return 0;
	}
	public int addnotificationAction(String tickerText, String notificationTitle, String notificationText){
			
		return 0;
	}
	
}
