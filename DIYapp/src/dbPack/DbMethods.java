package dbPack;

import java.util.ArrayList;
import java.util.HashMap;

import com.karbar.diyapp.utils.Constant;

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
	private Database db;
	
	public DbMethods(Context c){
		context = c;
		db = new Database (context);
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
	
	public ArrayList<HashMap<String, String>> getDIYaList(){
		
		return db.getAllTasks();
	}
	
	public HashMap<String, String> getOneDiya(long idDIYa){
		return db.getOneTask(idDIYa);
	}
	
	
	public ArrayList<ArrayList<HashMap<String, String>>> getConditonLists(long idDIYa){
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
		
		return db.getArrayAddedActionsFromDatabase(idDIYa);
	} 
	
	public long addTimeCondition(long idDIYa, int groupId, int timeSinceHour, int timeSinceMinutes, int timeToHour, int timeToMinutes, boolean [] days){
		
    	String days_boolean= "";
    	for(int i=0;i < days.length -1; i++){
    		days_boolean += days[i]+",";
    	}
    	days_boolean += days[days.length -1];
		String params = "" + timeSinceHour + "/~/"+ timeSinceMinutes + "/~/"+ timeToHour+ "/~/"+ timeToMinutes + "/~/"+ days_boolean;
    	//zwraca id z bazy
    	return db.insertAddedCondition(Constant.CONDITION_TIME, idDIYa, groupId, params);
    	
		
	}
	public long addDateCondition(long idDIYa, int groupId, int dateSinceDay, int dateSinceMonth, int dateSinceYear, 
			int dateToDay, int dateToMonth, int dateToYear){
		String params = "" + dateSinceDay + "/~/" + dateSinceMonth + "/~/" + dateSinceYear + "/~/" + dateToDay + "/~/" + dateToMonth + "/~/" + dateToYear;
    	//zwraca id z bazy
    	return db.insertAddedCondition(Constant.CONDITION_DATE, idDIYa, groupId, params);
		
	}
	
	public long addGpsCondition(long idDIYa, int groupId,double x, double y, double r, boolean isReversed){//isReversed - jesli true to na zewntrz okrgu
		String params = "" + x + "/~/" + y + "/~/" + r + "/~/" + isReversed;
    	//zwraca id z bazy
    	return db.insertAddedCondition(Constant.CONDITION_GPS, idDIYa, groupId, params);
		
	}
	public long addWiFiCondition(long idDIYa, int groupId, boolean on, String nameWiFi){
		String params = "" + on + "/~/" + nameWiFi;
    	//zwraca id z bazy
		System.out.println(Constant.CONDITION_WIFI + " "+idDIYa+" "+groupId + " " + on+ " " + nameWiFi);
    	return db.insertAddedCondition(Constant.CONDITION_WIFI, idDIYa, groupId, params);
		
	}
	
	public boolean isServiceRunning(){
		return db.isServiceRunning();
	}
	
	public boolean setServiceRunning(boolean run){
		return db.setServiceRunning(run);
	}
	
	public boolean addGroupToTask(long idDIYa, int idGroup){
		return db.addGroupToTask(idDIYa, idGroup);
	}

	public long addWiFiAction(long idDIYa, int tryb, String ssid){
		
		String params = tryb + "/~/" + ssid;
		
		return db.insertAddedAction(Constant.ID_WIFI, idDIYa, params);
	}
	public long addVibrationAction(long idDIYa){
		
		String params = "";
		
		return db.insertAddedAction(Constant.ID_WIBRATION, idDIYa, params);
	}
	public long addSoundAction(long idDIYa, int soundLevel){
			
		String params = ""+ soundLevel;
		
		return db.insertAddedAction(Constant.ID_SOUND_LEVEL, idDIYa, params);
	}
	public long addnotificationAction(long idDIYa, String tickerText, String notificationTitle, String notificationText){
			
	String params = tickerText + "/~/" + notificationTitle + "/~/" + notificationText;
		
		return db.insertAddedAction(Constant.ID_NOTIFICATION, idDIYa, params);
	}
	
	public String[] convertParamsIntoTab(String params){
		String [] p = params.split("/~/");
		return p;
		
	}
	
	//updateDIYa wywo³aj, gdy na koncu klikniesz zapisz lub w momencie zmiany aktywnoœci
	public boolean updateDIYa(long idDIYa, String act, String description, String name){
		return db.updateTask(idDIYa, act, description, name);
	}
	
	public boolean deleteDIYa(long idDIYa){
		return db.deleteTask(idDIYa);
	}
	
	public HashMap<String, String> getOneAddedActionFromDatabase(long idAddAct){
		return db.getAddedAction(idAddAct);	
	}
	
	public HashMap<String, String> getOneAddedConditionFromDatabase(long idAddCon){
		return db.getAddedCondition(idAddCon);
	}
	
	public boolean updateAddedCondition(long idCon, String params){
		return db.updateAddedCondition(idCon, params);
	}
	
	public boolean updateAddedAction(long idAddAct, String params){
		return db.updateAddedAction(idAddAct, params, "");//trzeci parametr to before, bede tego uzywal w serwisie
	}
	
	public boolean deleteAddedConditionFromTask(long idAddCon, long idDIYa){
		return db.deleteAddedCondition(idAddCon, idDIYa);
	}
	public boolean deleteAddedActionFromTask(long idAddAct, long idDIYa){
		return db.deleteAddedAction(idAddAct, idDIYa);
	}
	
	public boolean updateAddedAction(long idAddAct, String params, String before){
		return db.updateAddedAction(idAddAct, params, before);
	}
	
	public boolean setExecutedAction(long idAddAct, boolean exec){
		return db.setExecutedAction(idAddAct, exec);
	}
	
	public boolean setExecutedCondition(long idAddCon, boolean exec){
		return db.setExecutedCondition(idAddCon, exec);
	}
	
}
