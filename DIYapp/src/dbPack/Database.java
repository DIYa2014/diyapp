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

public class Database {
	
	private static final String DEBUG_TAG = "DIYaDatabase";
	
	private static final int DB_VERSION = 5;
	private static final String DB_NAME = "diyadatabase.db";
	private static final String DB_TASKS_TABLE = "tasks";
	private static final String DB_ACTIONS_TABLE = "actions";
	private static final String DB_CONDITIONS_TABLE = "conditions";
	private static final String DB_ADDED_ACTIONS_TABLE = "added_actions";
	private static final String DB_ADDED_CONDITIONS_TABLE = "added_conditions";
	private static final String DB_SERVICE_TABLE = "service";
	
	/*tabela tasks*/
	public static final String TASKS_KEY_ID = "_id_tasks";
	public static final String TASKS_ID_TASKS_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int TASKS_ID_TASKS_COLUMN = 0;

	public static final String TASKS_KEY_NAME_TASKS = "name_tasks";
	public static final String TASKS_NAME_TASKS_OPTIONS = "TEXT NOT NULL";
	public static final int TASKS_NAME_TASKS_COLUMN = 1;
	
	public static final String TASKS_KEY_DESCRIPTION = "descrition";
	public static final String TASKS_DESCRITION_OPTIONS = "TEXT";
	public static final int TASKS_DESCRIPTION_COLUMN = 2;
	
	public static final String TASKS_KEY_GROUPS_OF_CONDITIONS = "groups_of_conditions";
	public static final String TASKS_GROUPS_OF_CONDITIONS_OPTIONS = "TEXT NOT NULL";
	public static final int TASKS_GROUPS_OF_CONDITIONS_COLUMN = 3;
	
	public static final String TASKS_KEY_ADDED_CONDITIONS_ID = "added_conditions_id";
	public static final String TASKS_ADDED_CONDITIONS_ID_OPTIONS = "TEXT NOT NULL";
	public static final int TASKS_ADDED_CONDITIONS_ID_COLUMN = 4;
	
	public static final String TASKS_KEY_ADDED_ACTIONS_ID = "added_actions_id";
	public static final String TASKS_ADDED_ACTIONS_ID_OPTIONS = "TEXT NOT NULL";
	public static final int TASKS_ADDED_ACTIONS_ID_COLUMN = 5;
	
	public static final String TASKS_KEY_DATE_CREATE = "date_create";
	public static final String TASKS_DATE_CREATE_OPTIONS = "TEXT"; /*boolean: 0 false 1 true*/
	public static final int TASKS_DATE_CREATE_COLUMN = 6;
	
	public static final String TASKS_KEY_DATE_UPDATE = "date_update";
	public static final String TASKS_DATE_UPDATE_OPTIONS = "TEXT"; /*boolean: 0 false 1 true*/
	public static final int TASKS_DATE_UPDATE_COLUMN = 7;
	
	public static final String TASKS_KEY_ACTIVE = "active";
	public static final String TASKS_ACTIVE_OPTIONS = "INTEGER "; /*boolean: 0 false 1 true*/
	public static final int TASKS_ACTIVE_COLUMN = 8;
	
	public static final String TASKS_QUANTITY_OF_GROUPS = "quantity_of_groups"; //-~~**~~-UWAGA-~~**~~- tego nie ma w bazie
	
	
	/*tabela actions*/
	public static final String ACTIONS_KEY_ID_ACTIONS = "_id_actions";
	public static final String ACTIONS_ID_ACTIONS_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int ACTIONS_ID_ACTIONS_COLUMN = 0;
	
	public static final String ACTIONS_KEY_NAME_ACTIONS = "name_actions";
	public static final String ACTIONS_NAME_ACTIONS_OPTIONS = "TEXT NOT NULL";
	public static final int ACTIONS_NAME_ACTIONS_COLUMN = 1;
	
	public static final String ACTIONS_KEY_SCHEME_OF_PARAMETERS = "scheme_of_parameters_actions";
	public static final String ACTIONS_SCHEME_OF_PARAMETERS_ACTIONS_OPTIONS = "TEXT NOT NULL";
	public static final int ACTIONS_SCHEME_OF_PARAMETERS_ACTIONS_COLUMN = 2;
	
	
	/*tabela conditions*/
	public static final String CONDITIONS_KEY_ID_CONDITIONS = "_id_conditions";
	public static final String CONDITIONS_ID_CONDITIONS_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int CONDITIONS_ID_CONDITIONS_COLUMN = 0;
	
	public static final String CONDITIONS_KEY_NAME_CONDITIONS = "name_conditions";
	public static final String CONDITIONS_NAME_CONDITIONS_OPTIONS = "TEXT NOT NULL";
	public static final int CONDITIONS_NAME_CONDITIONS_COLUMN = 1;
	
	public static final String CONDITIONS_KEY_SCHEME_OF_PARAMETERS_CONDITIONS = "scheme_of_parameters_conditions";
	public static final String CONDITIONS_SCHEME_OF_PARAMETERS_CONDITIONS_OPTIONS = "TEXT NOT NULL";
	public static final int CONDITIONS_SCHEME_OF_PARAMETERS_CONDITIONS_COLUMN = 2;
	
	
	/*tabela addedd_actions*/
	public static final String ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS = "_id_added_actions";
	public static final String ADDED_ACTIONS_ID_ADDEDD_ACTIONS_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int ADDED_ACTIONS_ID_ADDEDD_ACTIONS_COLUMN = 0;
	
	public static final String ADDED_ACTIONS_KEY_ACTION_ID = "action_id";
	public static final String ADDED_ACTIONS_ACTION_ID_OPTIONS = "INTEGER";
	public static final int ADDED_ACTIONS_ACTION_ID_COLUMN = 1;
	
	public static final String ADDED_ACTIONS_KEY_TASK_ID_ACTIONS = "task_id_actions";
	public static final String ADDED_ACTIONS_TASK_ID_ACTIONS_OPTIONS = "INTEGER";
	public static final int ADDED_ACTIONS_TASK_ID_ACTIONS_COLUMN = 2;
	
	public static final String ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS = "parameters_actions";
	public static final String ADDED_ACTIONS_PARAMETERS_ACTIONS_OPTIONS = "TEXT";
	public static final int ADDED_ACTIONS_PARAMETERS_ACTIONS_COLUMN = 3;
	
	public static final String ADDED_ACTIONS_KEY_EXECUTED_ACTION = "executed_action";
	public static final String ADDED_ACTIONS_EXECUTED_ACTION_OPTIONS = "INTEGER";
	public static final int ADDED_ACTIONS_EXECUTED_ACTION_COLUMN = 4;
	
	public static final String ADDED_ACTIONS_KEY_BEFORE_ACTION = "before_action";
	public static final String ADDED_ACTIONS_BEFORE_ACTION_OPTIONS = "TEXT";
	public static final int ADDED_ACTIONS_BEFORE_ACTION_COLUMN = 5;
	
	
	/*tabela added_conditions*/
	public static final String ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS = "_id_added_conditions";
	public static final String ADDED_CONDITIONS_ID_ADDEDD_CONDITIONS_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int ADDED_CONDITIONS_ID_ADDEDD_CONDITIONS_COLUMN = 0;
	
	public static final String ADDED_CONDITIONS_KEY_CONDITION_ID = "condition_id";
	public static final String ADDED_CONDITIONS_CONDITION_ID_OPTIONS = "INTEGER";
	public static final int ADDED_CONDITIONS_CONDITION_ID_COLUMN = 1;
	
	public static final String ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS = "task_id_conditions";
	public static final String ADDED_CONDITIONS_TASK_ID_CONDITIONS_OPTIONS = "INTEGER";
	public static final int ADDED_CONDITIONS_TASK_ID_CONDITIONS_COLUMN = 2;
	
	public static final String ADDED_CONDITIONS_KEY_GROUP_ID = "group_id";
	public static final String ADDED_CONDITIONS_GROUP_ID_OPTIONS = "INTEGER";
	public static final int ADDED_CONDITIONS_GROUP_ID_COLUMN = 3;
	
	public static final String ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS = "parameters_conditions";
	public static final String ADDED_CONDITIONS_PARAMETERS_CONDITIONS_OPTIONS = "TEXT";
	public static final int ADDED_CONDITIONS_PARAMETERS_CONDITIONS_COLUMN = 4;
	
	public static final String ADDED_CONDITIONS_KEY_EXECUTED_CONDITION = "executed_condition";
	public static final String ADDED_CONDITIONS_EXECUTED_CONDITION_OPTIONS = "INTEGER";
	public static final int ADDED_CONDITIONS_EXECUTED_CONDITION_COLUMN = 5;
	
	/*tabela service*/
	public static final String SERVICE_KEY_ID_SERVICE = "id_service";
	public static final String SERVICE_ID_SERVICE_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final int SERVICE_ID_SERVICE_COLUMN = 0;

	public static final String SERVICE_KEY_RUNNING = "running";
	public static final String SERVICE_RUNNING_OPTIONS = "INTEGER";
	public static final int SERVICE_RUNNING_COLUMN = 1;

	//wszystkie kolumny
	public static final String[] column_keys_task = {TASKS_KEY_ID, TASKS_KEY_NAME_TASKS, TASKS_KEY_DESCRIPTION, TASKS_KEY_GROUPS_OF_CONDITIONS, 
													TASKS_KEY_ADDED_CONDITIONS_ID, TASKS_KEY_ADDED_ACTIONS_ID, TASKS_KEY_DATE_CREATE, 
													TASKS_KEY_DATE_UPDATE, TASKS_KEY_ACTIVE
													}; 
	
	public static final String[] column_keys_added_condition = {ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS, ADDED_CONDITIONS_KEY_CONDITION_ID, 
																ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS, ADDED_CONDITIONS_KEY_GROUP_ID, 
																ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, ADDED_CONDITIONS_KEY_EXECUTED_CONDITION
																};
	public static final String[] column_keys_added_actions = {ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS, ADDED_ACTIONS_KEY_ACTION_ID, ADDED_ACTIONS_KEY_TASK_ID_ACTIONS, 
																ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS, ADDED_ACTIONS_KEY_EXECUTED_ACTION, ADDED_ACTIONS_KEY_BEFORE_ACTION
																};
	public static final String[] column_keys_service = {SERVICE_KEY_ID_SERVICE, SERVICE_KEY_RUNNING};
	
	/*tworzenie tabel w bazie danych*/
	private static final String DB_CREATE_TASKS_TABLE = 
			"CREATE TABLE" + " " + DB_TASKS_TABLE + "( " +
			TASKS_KEY_ID + " " + TASKS_ID_TASKS_OPTIONS + ", " +
			TASKS_KEY_NAME_TASKS + " " + TASKS_NAME_TASKS_OPTIONS + ", " +
			TASKS_KEY_DESCRIPTION + " " + TASKS_DESCRITION_OPTIONS + ", " +
			TASKS_KEY_GROUPS_OF_CONDITIONS + " " + TASKS_GROUPS_OF_CONDITIONS_OPTIONS + ", " + 
			TASKS_KEY_ADDED_CONDITIONS_ID + " " + TASKS_ADDED_CONDITIONS_ID_OPTIONS + ", " +
			TASKS_KEY_ADDED_ACTIONS_ID + " " + TASKS_ADDED_ACTIONS_ID_OPTIONS +  ", " +
			TASKS_KEY_DATE_CREATE + " " + TASKS_DATE_CREATE_OPTIONS + ", " +
			TASKS_KEY_DATE_UPDATE + " " + TASKS_DATE_UPDATE_OPTIONS + ", " +
			TASKS_KEY_ACTIVE + " " + TASKS_ACTIVE_OPTIONS + 
			" );";
	private static final String DB_CREATE_ACTIONS_TABLE = 
			"CREATE TABLE" + " " + DB_ACTIONS_TABLE + "( " +
			ACTIONS_KEY_ID_ACTIONS + " " + ACTIONS_ID_ACTIONS_OPTIONS + ", " +
			ACTIONS_KEY_NAME_ACTIONS + " " + ACTIONS_NAME_ACTIONS_OPTIONS + ", " +
			ACTIONS_KEY_SCHEME_OF_PARAMETERS + " " + ACTIONS_SCHEME_OF_PARAMETERS_ACTIONS_OPTIONS + 
			" );";
	private static final String DB_CREATE_CONDITIONS_TABLE = 
			"CREATE TABLE" + " " + DB_CONDITIONS_TABLE + "( " +
			CONDITIONS_KEY_ID_CONDITIONS + " " + CONDITIONS_ID_CONDITIONS_OPTIONS + ", " +
			CONDITIONS_KEY_NAME_CONDITIONS + " " + CONDITIONS_NAME_CONDITIONS_OPTIONS + ", " +
			CONDITIONS_KEY_SCHEME_OF_PARAMETERS_CONDITIONS + " " + CONDITIONS_SCHEME_OF_PARAMETERS_CONDITIONS_OPTIONS + 
			" );";
	private static final String DB_CREATE_ADDED_ACTIONS_TABLE = 
			"CREATE TABLE" + " " + DB_ADDED_ACTIONS_TABLE + "( " +
			ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS + " " + ADDED_ACTIONS_ID_ADDEDD_ACTIONS_OPTIONS + ", " +
			ADDED_ACTIONS_KEY_ACTION_ID + " " + ADDED_ACTIONS_ACTION_ID_OPTIONS + ", " +
			ADDED_ACTIONS_KEY_TASK_ID_ACTIONS + " " + ADDED_ACTIONS_TASK_ID_ACTIONS_OPTIONS + ", " +
			ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS + " " + ADDED_ACTIONS_PARAMETERS_ACTIONS_OPTIONS + ", " +
			ADDED_ACTIONS_KEY_EXECUTED_ACTION + " " + ADDED_ACTIONS_EXECUTED_ACTION_OPTIONS + ", " +
			ADDED_ACTIONS_KEY_BEFORE_ACTION + " " + ADDED_ACTIONS_BEFORE_ACTION_OPTIONS + 
			" );";
	private static final String DB_CREATE_ADDED_CONDITIONS_TABLE = 
			"CREATE TABLE" + " " + DB_ADDED_CONDITIONS_TABLE + "( " +
			ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS + " " + ADDED_CONDITIONS_ID_ADDEDD_CONDITIONS_OPTIONS + ", " + 
			ADDED_CONDITIONS_KEY_CONDITION_ID + " " + ADDED_CONDITIONS_CONDITION_ID_OPTIONS + ", " + 
			ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS+ " " + ADDED_CONDITIONS_TASK_ID_CONDITIONS_OPTIONS + ", " +
			ADDED_CONDITIONS_KEY_GROUP_ID + " " + ADDED_CONDITIONS_GROUP_ID_OPTIONS + ", " + 
			ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS + " " + ADDED_CONDITIONS_PARAMETERS_CONDITIONS_OPTIONS + ", " + 
			ADDED_CONDITIONS_KEY_EXECUTED_CONDITION + " " + ADDED_CONDITIONS_EXECUTED_CONDITION_OPTIONS + 
			" );";
	private static final String DB_CREATE_SERVICE_TABLE = 
			"CREATE TABLE" + " " + DB_SERVICE_TABLE + "( " +
			SERVICE_KEY_ID_SERVICE + " " + SERVICE_ID_SERVICE_OPTIONS + ", " +
			SERVICE_KEY_RUNNING + " " + SERVICE_RUNNING_OPTIONS + 
			" );";
	
	private static final String DROP_TASKS_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_TASKS_TABLE;
	private static final String DROP_ACTIONS_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_ACTIONS_TABLE;
	private static final String DROP_CONDITIONS_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_CONDITIONS_TABLE;
	private static final String DROP_ADDED_ACTIONS_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_ADDED_ACTIONS_TABLE;
	private static final String DROP_ADDED_CONDITIONS_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_ADDED_CONDITIONS_TABLE;
	private static final String DROP_SERVICE_TABLE = 
			"DROP TABLE IF EXISTS" + " " + DB_SERVICE_TABLE;
	
	private SQLiteDatabase db;
	private Context context;
	private DatabaseHelper dbHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			System.out.println("jestem w on create database");
			db.execSQL(DB_CREATE_TASKS_TABLE);
			db.execSQL(DB_CREATE_ACTIONS_TABLE);
			db.execSQL(DB_CREATE_CONDITIONS_TABLE);
			db.execSQL(DB_CREATE_ADDED_ACTIONS_TABLE);
			db.execSQL(DB_CREATE_ADDED_CONDITIONS_TABLE);
			db.execSQL(DB_CREATE_SERVICE_TABLE);
			System.out.println("jestem w on create database2");
			String sql = "INSERT or replace INTO " + DB_SERVICE_TABLE + "(" + SERVICE_KEY_RUNNING + ") VALUES(0)" ; 
			db.execSQL(sql);
			System.out.println("jestem w on create database3");
			//tu trzeba jeszcze wrzucuc wartoœci dla tabel action i condition
			
			Log.d(DEBUG_TAG, "Database creating...");
			Log.d(DEBUG_TAG, "Tables " + DB_CREATE_TASKS_TABLE + ", " + DB_CREATE_ACTIONS_TABLE + ", " + DB_CREATE_CONDITIONS_TABLE + ", " + DB_CREATE_ADDED_ACTIONS_TABLE + ", " + DB_CREATE_ADDED_CONDITIONS_TABLE +", " + DB_CREATE_SERVICE_TABLE + " ver." + DB_VERSION + " created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TASKS_TABLE);
			db.execSQL(DROP_ACTIONS_TABLE);
			db.execSQL(DROP_CONDITIONS_TABLE);
			db.execSQL(DROP_ADDED_ACTIONS_TABLE);
			db.execSQL(DROP_ADDED_CONDITIONS_TABLE);
			db.execSQL(DROP_SERVICE_TABLE);
			Log.d(DEBUG_TAG, "Database updating...");
			Log.d(DEBUG_TAG, "Table " + DB_CREATE_TASKS_TABLE + ", " + DB_CREATE_ACTIONS_TABLE + ", " + DB_CREATE_CONDITIONS_TABLE + ", " + DB_CREATE_ADDED_ACTIONS_TABLE + ", " + DB_CREATE_ADDED_CONDITIONS_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
			Log.d(DEBUG_TAG, "All data is lost.");
			
			onCreate(db);
		}
		
	}
	
	public Database(Context c){
		context = c;
	}
	
	public Database open(){
		dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
		try{
			Log.d(DEBUG_TAG, "Trying to get writable database "+ DB_NAME);
			System.out.println("próbuje sie dostaæ do bazy");
			db = dbHelper.getWritableDatabase();
			System.out.println("uda³o sie dostaæ do bazy");
			Log.d(DEBUG_TAG, "Getting writable database "+ DB_NAME + " ended with success");
		} catch(SQLException e){
			Log.d(DEBUG_TAG, "Getting writable database "+ DB_NAME + " ended with failure.");
			Log.d(DEBUG_TAG, "Exceprion: " + e.toString());
			Log.d(DEBUG_TAG, "Trying to get readable database");
			System.out.println("NIE uda³o sie dostaæ do bazywritable szykam readable");
			db = dbHelper.getReadableDatabase();
			System.out.println("uda³o sie dostaæ do bazy ale tylko readable");
			Log.d(DEBUG_TAG, "Getting readable database "+ DB_NAME + " ended with success");
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	//metody dostepowe do danych, zwracane beda kolekcje odpowiednih obiektow
	/*
	 * jakie metody beda potrzebne?
	 * 1.zwracajace dane
	 * -lista wszystkich zadan
	 * -uaktywnienie zadania
	 * -unieaktywnienie zadania
	 * -wszystkie dodane akcje z parametrami & wszystkie dodane warunki z parametrami
	 * -wszystkie dodane akcje z parametrami
	 * -wszystkie dodane warunki z parametrami
	 * -wszystkie dodane akcje bez parametrow & wszystkie dodane warunki bez parametrow
	 * -wszystkie dodane akcje bez parametrow
	 * -wszystkie dodane warunki bez parametrow
	 * -pojedynczy dodany warunek z parametrami
	 * -pojedyncza dodana akcja z parametrami
	 * -wszystkie dostepne akcje
	 * -wszystkie dostepne warunki
	 * 
	 * 2.dodajace i tworzace
	 * -utworzenie zadania
	 * -dodanie warunku bez parametrów do zadania
	 * -dodanie warunku z parametrami do zadania
	 * -dodanie akcji bez parametrow do zadania
	 * -dodanie akcji z parametrami do zadania
	 * -dodanie parametrow do akci z zadania
	 * -dodanie parametrow do warunku z zadania
	 * 
	 * 3. usuwajace
	 * -usuniecie zadania wraz ze wszystkimi dodanymi akcjami i warunkami
	 * -usniecie dodanego warunku
	 * -usuniecie dodanej akcji
	 * 
	 * */
	
	public boolean isServiceRunning(){

		Cursor cursor = db.query(DB_SERVICE_TABLE, column_keys_service, null, null, null, null, null);
		cursor.moveToFirst();
		if(cursor != null && cursor.moveToFirst()){
			int running = cursor.getInt(SERVICE_RUNNING_COLUMN);
			boolean run = running == 1 ? true : false;
			return run;
		}
		return false;
	}
	
	/*public boolean initServiceRunning(){
		ContentValues newValues = new ContentValues();
		newValues.put(SERVICE_KEY_RUNNING, 0);
		long id = dbHelper.getWritableDatabase().insert(DB_SERVICE_TABLE, null, newValues);
		if (id == 1){
			return true;
		}
		return false;
	}
	*/
	
	public boolean setServiceRunning(boolean run){
		String where = SERVICE_KEY_ID_SERVICE + "=" + 1;
		ContentValues updateValues = new ContentValues();
		int running = run ? 1 : 0;
		updateValues.put(SERVICE_KEY_RUNNING, running);
		if (db.update(DB_SERVICE_TABLE, updateValues, where, null) > 0){
			return true;
		}
		return false;
	}
	
	
	//dodawanie nowego zadania, wstawiana jest obecna data i zwracane jest id zadania
	public long insertTask(){
		//Task task = new Task();
		Time now = new Time();
		now.setToNow();
		String nowString = now.toString();
		ContentValues newValues = new ContentValues();
		int act = 1;
		newValues.put(TASKS_KEY_ACTIVE, act);
		newValues.put(TASKS_KEY_DATE_CREATE, nowString);
		newValues.put(TASKS_KEY_DATE_UPDATE, nowString);
		newValues.put(TASKS_KEY_DESCRIPTION, "");
		newValues.put(TASKS_KEY_NAME_TASKS, "");
		newValues.put(TASKS_KEY_ADDED_ACTIONS_ID, "");
		newValues.put(TASKS_KEY_ADDED_CONDITIONS_ID, "");
		newValues.put(TASKS_KEY_GROUPS_OF_CONDITIONS, "1");
		//task.setDate_create(nowString);
		//task.setDate_update(nowString);
		//task.setActive(true);
		System.out.println("jestem, zyje");
		long id  = db.insert(DB_TASKS_TABLE, null, newValues);
		if(id == -1){
			System.out.println("Blad przy tworzeniu zadania");
			return -1;
		}
		//task.setId(id);
		return id;

	}
	
	public boolean updateTask(long idTask, String act, String description, String name){
		if(idTask > 0){
			Time now = new Time();
			now.setToNow();
			String nowString = now.toString();
			ContentValues updateValues = new ContentValues();
			int active = Integer.parseInt(act);
			updateValues.put(TASKS_KEY_ACTIVE, active);
			updateValues.put(TASKS_KEY_DATE_UPDATE, nowString);
			if(!description.equals(""))
				updateValues.put(TASKS_KEY_DESCRIPTION, description);
			if(!name.equals(""))
				updateValues.put(TASKS_KEY_NAME_TASKS, name);

			String where = TASKS_KEY_ID + "=" + idTask;

			return db.update(DB_TASKS_TABLE, updateValues, where, null) > 0;
		}
		else
			return false;
	}
	
	public boolean deleteTask(long idTask){
		if(idTask > 0){
			String where_task = TASKS_KEY_ID + " IN (" + idTask + ")";
			String where_act = ADDED_ACTIONS_KEY_TASK_ID_ACTIONS + " IN (" + idTask + ")";
			String where_cond = ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS + " IN (" + idTask + ")";
			
			if( db.delete(DB_TASKS_TABLE, where_task, null) > 0 && db.delete(DB_ADDED_ACTIONS_TABLE, where_act, null) > 0 && db.delete(DB_ADDED_CONDITIONS_TABLE, where_cond, null) > 0 )
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public ArrayList<HashMap<String, String>> getAllTasks(){
		ArrayList<HashMap<String, String>> getAllTasks = new ArrayList<HashMap<String,String>>();
		Cursor cursor = db.query( DB_TASKS_TABLE, column_keys_task, null, null, null, null, null); 
		if(cursor != null){
			cursor.moveToFirst();
			do{
				String id = cursor.getString(TASKS_ID_TASKS_COLUMN);
				String name = cursor.getString(TASKS_NAME_TASKS_COLUMN);
				String description = cursor.getString(TASKS_DESCRIPTION_COLUMN);
				String groups_of_conditions = cursor.getString(TASKS_GROUPS_OF_CONDITIONS_COLUMN);
				String added_conditions_id = cursor.getString(TASKS_ADDED_CONDITIONS_ID_COLUMN);
				String added_acions_id = cursor.getString(TASKS_ADDED_ACTIONS_ID_COLUMN);
				int active = cursor.getInt(TASKS_ACTIVE_COLUMN);
				boolean act = active == 1 ? true : false;
				String date_create = cursor.getString(TASKS_DATE_CREATE_COLUMN);
				String date_update = cursor.getString(TASKS_DATE_UPDATE_COLUMN); 
				
				HashMap<String, String> task = new HashMap<String, String>();
				/*
				 * public static final String TASKS_KEY_ID = "_id_tasks";
	public static final String TASKS_KEY_NAME_TASKS = "name_tasks";
	public static final String TASKS_KEY_DESCRIPTION = "descrition";
	public static final String TASKS_KEY_GROUPS_OF_CONDITIONS = "groups_of_conditions";
	public static final String TASKS_KEY_ADDED_CONDITIONS_ID = "added_conditions_id";
	public static final String TASKS_KEY_ADDED_ACTIONS_ID = "added_actions_id";	
	public static final String TASKS_KEY_DATE_CREATE = "date_create";
	public static final String TASKS_KEY_DATE_UPDATE = "date_update";
	public static final String TASKS_KEY_ACTIVE = "active";
	public static final String TASKS_QUANTITY_OF_GROUPS = "quantity_of_groups";
				 * 
				 * 
				 * 
				 * 
				 * 
				 * */
				
				String [] gr = groups_of_conditions.split(",");
				int quant_gr = gr.length;
				
				
				task.put(TASKS_KEY_ID, id);
				task.put(TASKS_KEY_NAME_TASKS, name);
				task.put(TASKS_KEY_DESCRIPTION, description);
				task.put(TASKS_KEY_GROUPS_OF_CONDITIONS, groups_of_conditions);
				task.put(TASKS_KEY_ADDED_CONDITIONS_ID, added_conditions_id);
				task.put(TASKS_KEY_ADDED_ACTIONS_ID, added_acions_id);
				task.put(TASKS_KEY_DATE_CREATE, date_create);
				task.put(TASKS_KEY_DATE_UPDATE, date_update);
				task.put(TASKS_KEY_ACTIVE, ""+act);
				task.put(TASKS_QUANTITY_OF_GROUPS, ""+quant_gr);
				
				
				
				getAllTasks.add(task);
			} while (cursor.moveToNext());
			
			
		}
		return getAllTasks;
	}
	
	public String[] getTaskReturnGroups(long id){
		//Task task = null;
		System.out.println("get task1");
		String where = TASKS_KEY_ID + "=" + id;
		Cursor cursor = db.query(DB_TASKS_TABLE, column_keys_task, where, null, null, null, null);
		System.out.println("get task2");
		cursor.moveToFirst();
		System.out.println("get task2.5");
		String [] groupsListStrings = null;
		if(cursor != null && cursor.moveToFirst()){
			System.out.println("get task3");
			String name = cursor.getString(TASKS_NAME_TASKS_COLUMN);
			String description = cursor.getString(TASKS_DESCRIPTION_COLUMN);
			String groups_of_conditions = cursor.getString(TASKS_GROUPS_OF_CONDITIONS_COLUMN);
			String added_conditions_id = cursor.getString(TASKS_ADDED_CONDITIONS_ID_COLUMN);
			String added_acions_id = cursor.getString(TASKS_ADDED_ACTIONS_ID_COLUMN);
			int active = cursor.getInt(TASKS_ACTIVE_COLUMN);
			boolean act = active == 1 ? true : false;
			String date_create = cursor.getString(TASKS_DATE_CREATE_COLUMN);
			String date_update = cursor.getString(TASKS_DATE_UPDATE_COLUMN); 
			System.out.println("get task4");
			//task = new Task();
			System.out.println("get task5");
			//task.setId(id);
			//task.setName(name);
			//task.setDescription(description);
			//task.setGroups_of_conditions(groups_of_conditions);
			//task.setAdded_acions_id(added_acions_id);
			//task.setAdded_conditions_id(added_conditions_id);
			//task.setActive(act);
			//task.setDate_create(date_create);
			//task.setDate_update(date_update);
			System.out.println("get task6");
			
			groupsListStrings = groups_of_conditions.split(",");
		}
		return groupsListStrings;
	}
	
	public boolean addGroupToTask(long idTask, int group ){
		//Task task2 = task;
		String where = TASKS_KEY_ID + "=" + idTask;
		Cursor cursor = db.query(DB_TASKS_TABLE, column_keys_task, where, null, null, null, null);
		cursor.moveToFirst();
		if(cursor != null && cursor.moveToFirst()){
			String groups = cursor.getString(TASKS_GROUPS_OF_CONDITIONS_COLUMN);
			groups += ","+group;
			
			ContentValues updateValues = new ContentValues();
			updateValues.put(TASKS_KEY_GROUPS_OF_CONDITIONS, groups);
			
			if(db.update(DB_TASKS_TABLE, updateValues, where, null) > 0){
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<HashMap<String, String>> getArrayConditionFromOneGrup(String idGroup, long idTask){
		ArrayList<HashMap<String, String>> ret =  new ArrayList<HashMap<String,String>>();
		
		String whereCon2 = ADDED_CONDITIONS_KEY_GROUP_ID + '=' + idGroup + " AND " + ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS +"="+idTask;
		Cursor cursorCon = db.query(DB_ADDED_CONDITIONS_TABLE, column_keys_added_condition, whereCon2, null, null, null, null);
		cursorCon.moveToFirst();
		if(cursorCon != null && cursorCon.moveToFirst()){
			do{
				/*ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS, 
				ADDED_CONDITIONS_KEY_CONDITION_ID,
ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS, 
ADDED_CONDITIONS_KEY_GROUP_ID, 
ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, 
ADDED_CONDITIONS_KEY_EXECUTED_CONDITION*/
				System.out.println("ilosc wierszy = "+cursorCon.getCount());
				System.out.println("pozycja = "+cursorCon.getPosition());
				System.out.println("fdefes");
				HashMap<String, String> map = new HashMap<String, String>();
				//System.out.println("pierwsza kolumna = "+cursorCon.getColumnName(21));
				String id_add_con = cursorCon.getString(ADDED_CONDITIONS_ID_ADDEDD_CONDITIONS_COLUMN);
				String id_con = cursorCon.getString(ADDED_CONDITIONS_CONDITION_ID_COLUMN);
				String id_task = cursorCon.getString(ADDED_CONDITIONS_TASK_ID_CONDITIONS_COLUMN);
				String id_group = cursorCon.getString(ADDED_CONDITIONS_GROUP_ID_COLUMN);
				String params = cursorCon.getString(ADDED_CONDITIONS_PARAMETERS_CONDITIONS_COLUMN);
				String exec = cursorCon.getString(ADDED_CONDITIONS_EXECUTED_CONDITION_COLUMN);
				HashMap<String, String> tempMap = new HashMap<String, String>();
				//AddedCondition ac = new AddedCondition();
				map.put(ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS, id_add_con);
				map.put(ADDED_CONDITIONS_KEY_CONDITION_ID, id_con);
				map.put(ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS, id_task);
				map.put(ADDED_CONDITIONS_KEY_GROUP_ID, id_group);
				map.put(ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, params);
				map.put(ADDED_CONDITIONS_KEY_EXECUTED_CONDITION, exec);
				
				ret.add(map);
				
				
			}
			while(cursorCon.moveToNext());
			
		}
		return ret;
		
	}
	
	public ArrayList<ArrayList<HashMap<String, String>>> getArrayAddedConditionFromDatabase(long idTask){
		//ArrayList<AddedCondition> arrayC = new ArrayList<AddedCondition>();
		ArrayList<ArrayList<HashMap<String, String>>> getConditonLists = new ArrayList<ArrayList<HashMap<String,String>>>();
		//String whereCon = ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS +" IN (" + addedConditions + ")";
		
		String [] groupsListStrings = getTaskReturnGroups(idTask);
		
		
		for(String str : groupsListStrings){
			Log.d("kkams", "group: "+str + " id= " + getArrayConditionFromOneGrup(str, idTask));
			getConditonLists.add(
					getArrayConditionFromOneGrup(str, idTask)
					);
		}
		
		
		return getConditonLists;
	}
	
	public boolean isTaskEmpty(long idTask){
		String where = TASKS_KEY_ID + "=" + idTask;
		Cursor cursor = db.query(DB_TASKS_TABLE, column_keys_task, where, null, null, null, null);
		System.out.println("get task2");
		cursor.moveToFirst();
		if(cursor != null && cursor.moveToFirst()){
			System.out.println("get task3");
			String name = cursor.getString(TASKS_NAME_TASKS_COLUMN);
			String description = cursor.getString(TASKS_DESCRIPTION_COLUMN);
			String groups_of_conditions = cursor.getString(TASKS_GROUPS_OF_CONDITIONS_COLUMN);
			String added_conditions_id = cursor.getString(TASKS_ADDED_CONDITIONS_ID_COLUMN);
			String added_acions_id = cursor.getString(TASKS_ADDED_ACTIONS_ID_COLUMN);
			int active = cursor.getInt(TASKS_ACTIVE_COLUMN);
			boolean act = active == 1 ? true : false;
			String date_create = cursor.getString(TASKS_DATE_CREATE_COLUMN);
			String date_update = cursor.getString(TASKS_DATE_UPDATE_COLUMN); 
			System.out.println("get task4");
			if(added_conditions_id.equals("") && added_acions_id.equals(""))
				return true;
			return false;
		}
			return true;
		
	}
	
	public ArrayList<HashMap<String, String>> getArrayAddedActionsFromDatabase(long idTask){
		ArrayList<HashMap<String, String>> getActionsLists = new ArrayList<HashMap<String,String>>();
		
		String whereCon2 = ADDED_ACTIONS_KEY_TASK_ID_ACTIONS +"="+idTask;
		Cursor cursorAct = db.query(DB_ADDED_ACTIONS_TABLE, column_keys_added_actions, whereCon2, null, null, null, null);
		cursorAct.moveToFirst();
		if(cursorAct != null && cursorAct.moveToFirst()){
			do{
				/*ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS, 
				ADDED_CONDITIONS_KEY_CONDITION_ID,
ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS, 
ADDED_CONDITIONS_KEY_GROUP_ID, 
ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, 
ADDED_CONDITIONS_KEY_EXECUTED_CONDITION*/
				System.out.println("ilosc wierszy = "+cursorAct.getCount());
				System.out.println("pozycja = "+cursorAct.getPosition());
				System.out.println("fdefes");
				HashMap<String, String> map = new HashMap<String, String>();
				//System.out.println("pierwsza kolumna = "+cursorCon.getColumnName(21));
				/*
				 * ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS, ADDED_ACTIONS_KEY_ACTION_ID, ADDED_ACTIONS_KEY_TASK_ID_ACTIONS, 
				 * ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS, ADDED_ACTIONS_KEY_EXECUTED_ACTION, ADDED_ACTIONS_KEY_BEFORE_ACTION
				 * 
				 * */
				String id_add_act = cursorAct.getString(ADDED_ACTIONS_ID_ADDEDD_ACTIONS_COLUMN);
				String id_act = cursorAct.getString(ADDED_ACTIONS_ACTION_ID_COLUMN);
				String id_task = cursorAct.getString(ADDED_ACTIONS_TASK_ID_ACTIONS_COLUMN);
				String params = cursorAct.getString(ADDED_ACTIONS_PARAMETERS_ACTIONS_COLUMN);
				String exec = cursorAct.getString(ADDED_ACTIONS_EXECUTED_ACTION_COLUMN);
				String before = cursorAct.getString(ADDED_ACTIONS_BEFORE_ACTION_COLUMN);
				HashMap<String, String> tempMap = new HashMap<String, String>();
				//AddedCondition ac = new AddedCondition();
				map.put(ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS, id_add_act);
				map.put(ADDED_ACTIONS_KEY_ACTION_ID, id_act);
				map.put(ADDED_ACTIONS_KEY_TASK_ID_ACTIONS, id_task);
				map.put(ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS, params);
				map.put(ADDED_ACTIONS_KEY_EXECUTED_ACTION, exec);
				map.put(ADDED_ACTIONS_KEY_BEFORE_ACTION, before);
				
				getActionsLists.add(map);
				
				
			}
			while(cursorAct.moveToNext());
			
		}
		
		return getActionsLists;
	}

	public long addConditionToTask(long idTask, long addedConditionID){
		//long id = task.getId();
		//Task task2 = task;
		String where = TASKS_KEY_ID + "=" + idTask;
		Cursor cursor = db.query(DB_TASKS_TABLE, column_keys_task, where, null, null, null, null);
		cursor.moveToFirst();
		if(cursor != null && cursor.moveToFirst()){
			System.out.println("cokolwiek");
			//tu poprawiæ
			String addedConditions = cursor.getString(TASKS_ADDED_CONDITIONS_ID_COLUMN);
			System.out.println("addedConditions = " + addedConditions);
			if(addedConditions.equals("")){
				addedConditions += addedConditionID;
			}
			else
				addedConditions += ", " + addedConditionID;
			
			ContentValues updateValues = new ContentValues();
			updateValues.put(TASKS_KEY_ADDED_CONDITIONS_ID, addedConditions);
			int iloscZaktualizowanych = db.update(DB_TASKS_TABLE, updateValues, where, null);
			//update wszystkich dodanych warunków - array
		
			if(iloscZaktualizowanych > 0){
				return iloscZaktualizowanych;
			}
		}
		
		return -1;
	}
	

	public long insertAddedCondition(long idCondition, long idTask, int idGroup, String params){
		//AddedCondition addCon = new AddedCondition();
		/*ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS	
		ADDED_CONDITIONS_KEY_CONDITION_ID
		ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS
		ADDED_CONDITIONS_KEY_GROUP_ID
		ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS
		ADDED_CONDITIONS_KEY_EXECUTED_CONDITION
		
		*/
		//long idTask = task.getId();
		
		ContentValues newValues = new ContentValues();
		newValues.put(ADDED_CONDITIONS_KEY_CONDITION_ID, idCondition);
		newValues.put(ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS, idTask);
		newValues.put(ADDED_CONDITIONS_KEY_GROUP_ID, idGroup);
		newValues.put(ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, "");
		newValues.put(ADDED_CONDITIONS_KEY_EXECUTED_CONDITION, 0);
		newValues.put(ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS, params);
		

		long id  = db.insert(DB_ADDED_CONDITIONS_TABLE, null, newValues);
		if(id == -1){
			System.out.println("Blad przy tworzeniu dodanego warunku");
			return -1;
		}

		System.out.println("insertAddedCondition, id = " + id + " a task to " + idTask);
		addConditionToTask(idTask, id);
		
		return id;
	}
		
}
