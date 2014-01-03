package com.karbar.diyapp.utils;

import com.karbar.diyapp.R;

public class Constant {
	
	public static final String KEY_OPTION = "option"; // nazwa warunku
	public static final String KEY_ID = "id";
	public static final String KEY_ICO = "ico_url";
	public static final String KEY_BUNDLE = "bundle";
	
	public static final int ID_TIME = 1;
	public static final int ID_WIFI =  2;
	public static final int ID_GPS = 3;
	public static final int ID_CALENDAR = 4;
	public static final int ID_EMPTY = -1;
	
	public static final int ICO_TIME = R.drawable.perm_group_system_clock;
	public static final int ICO_WIFI =  R.drawable.perm_group_network;
	public static final int ICO_GPS = R.drawable.perm_group_location;
	public static final int ICO_CALENDAR = R.drawable.perm_group_calendar;
	public static final int ICO_EMPTY = R.drawable.empty;
	
	public static final String KEY_DIYAID = "diyid";
	
	//u¿ywam ich ja
	
	/*tabela tasks*/
	public static final String TASKS_KEY_ID = "_id_tasks";
	public static final String TASKS_KEY_NAME_TASKS = "name_tasks";
	public static final String TASKS_KEY_DESCRIPTION = "descrition";
	public static final String TASKS_KEY_GROUPS_OF_CONDITIONS = "groups_of_conditions";
	public static final String TASKS_KEY_ADDED_CONDITIONS_ID = "added_conditions_id";
	public static final String TASKS_KEY_ADDED_ACTIONS_ID = "added_actions_id";	
	public static final String TASKS_KEY_DATE_CREATE = "date_create";
	public static final String TASKS_KEY_DATE_UPDATE = "date_update";
	public static final String TASKS_KEY_ACTIVE = "active";
	public static final String TASKS_QUANTITY_OF_GROUPS = "quantity_of_groups"; //-~~**~~-UWAGA-~~**~~- tego nie ma w bazie
	
	/*tabela actions*/
	public static final String ACTIONS_KEY_ID_ACTIONS = "_id_actions";
	public static final String ACTIONS_KEY_NAME_ACTIONS = "name_actions";
	public static final String ACTIONS_KEY_SCHEME_OF_PARAMETERS = "scheme_of_parameters_actions";
	
	/*tabela conditions*/
	public static final String CONDITIONS_KEY_ID_CONDITIONS = "_id_conditions";
	public static final String CONDITIONS_KEY_NAME_CONDITIONS = "name_conditions";
	public static final String CONDITIONS_KEY_SCHEME_OF_PARAMETERS_CONDITIONS = "scheme_of_parameters_conditions";
	
	/*tabela addedd_actions*/
	public static final String ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS = "_id_added_actions";
	public static final String ADDED_ACTIONS_KEY_ACTION_ID = "action_id";
	public static final String ADDED_ACTIONS_KEY_TASK_ID_ACTIONS = "task_id_actions";
	public static final String ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS = "parameters_actions";
	public static final String ADDED_ACTIONS_KEY_EXECUTED_ACTION = "executed_action";
	public static final String ADDED_ACTIONS_KEY_BEFORE_ACTION = "before_action";
	
	/*tabela added_conditions*/
	public static final String ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS = "_id_added_conditions";
	public static final String ADDED_CONDITIONS_KEY_CONDITION_ID = "condition_id";
	public static final String ADDED_CONDITIONS_KEY_TASK_ID_CONDITIONS = "task_id_conditions";
	public static final String ADDED_CONDITIONS_KEY_GROUP_ID = "group_id";
	public static final String ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS = "parameters_conditions";
	public static final String ADDED_CONDITIONS_KEY_EXECUTED_CONDITION = "executed_condition";

	/*mo¿liwe warunki */
	public static final long CONDITION_TIME = 1;
	public static final long CONDITION_DATE = 2;
	public static final long CONDITION_GPS = 3;
	public static final long CONDITION_WIFI = 4;
	
	/*mo¿liwe akcje*/
	public static final long ACTION_WIFI = 1;
	public static final long ACTION_VIBRATION = 2;
	public static final long ACTION_SOUND = 3;
	public static final long ACTION_NOTIFICATION = 4;
}
