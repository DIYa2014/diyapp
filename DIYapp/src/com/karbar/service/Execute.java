package com.karbar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.karbar.dbPack.DbMethods;
import com.karbar.diyapp.utils.Constant;

import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;
import android.widget.Toast;

public class Execute extends Service {
	private static final String TAG = "MyService";
	DbMethods dbMethods;
	Action act;
	Trigger tr;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		dbMethods = new DbMethods(getApplicationContext());
		act = new Action(getApplicationContext(), dbMethods);
		tr = new Trigger(getApplicationContext(), dbMethods);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		dbMethods.setServiceRunning(false);
		Log.d("kkams", "onDestroy");

	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d("kkams", "onStart");
		okresowewykonywanie();
	}

	private void okresowewykonywanie() {

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			public void run() {

				Log.d("kkams", "service isRun? " + dbMethods.isServiceRunning());
				System.out.println("service isRun? "
						+ dbMethods.isServiceRunning());
				Log.d("karbarServiceTest", "Ala1");
				ArrayList<HashMap<String, String>> DIYas = dbMethods.getDIYaList();
				Log.d("karbarServiceTest", "Ala2");
				if (DIYas != null && !DIYas.isEmpty()) {
					Log.d("karbarServiceTest", "Ala3");
					for (HashMap<String, String> diya : DIYas) {
						Log.d("karbarServiceTest", "Ala4");
						int idDIYa = Integer.valueOf(diya.get(Constant.TASKS_KEY_ID));
						if (!dbMethods.isDIYaEmpty(idDIYa)) {
							Log.d("karbarServiceTest", "Ala5");
							if (diya.get(Constant.TASKS_KEY_ACTIVE).equals("true")) {
								Log.d("karbarServiceTest", "Ala6");
								if (dbMethods.isDIYaHasCondition(idDIYa)) {
									Log.d("karbarServiceTest", "Ala7");
									ArrayList<ArrayList<HashMap<String, String>>> AddedConditions = 
											dbMethods.getConditonLists(idDIYa);
									if (AddedConditions != null && !AddedConditions.isEmpty()) {
										Log.d("karbarServiceTest", "Ala8");
										for (ArrayList<HashMap<String, String>> groupsWithAddedConditions : AddedConditions) {
											Log.d("karbarServiceTest", "Ala9");
											if (!groupsWithAddedConditions.isEmpty()) {
												Log.d("karbarServiceTest", "Ala10");
												if (czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)) {
													Log.d("karbarServiceTest", "Ala11");
													ArrayList<HashMap<String, String>> AddedActions = dbMethods.getActionsLists(idDIYa);
													HashMap<String, String> firstAction = AddedActions.get(0);
													Log.d("karbarServiceTest", "Ala11a: " + firstAction.get(Constant.ADDED_ACTIONS_KEY_EXECUTED_ACTION));
													if (firstAction
															.get(Constant.ADDED_ACTIONS_KEY_EXECUTED_ACTION)
															.equals("0")) {
														Log.d("karbarServiceTest", "Ala12");
														if (dbMethods.isDIYaHasAction(idDIYa)) {
															Log.d("karbarServiceTest", "Ala13");
															wykonajAkcjeIZmienIchStatusNaWykonane(AddedActions);
														}
														break;
													} else {
														Log.d("karbarServiceTest", "Ala14");
														break;
													}
												} else if (!czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)
														&& groupsWithAddedConditions
																.get(0)
																.get(Constant.ADDED_CONDITIONS_KEY_EXECUTED_CONDITION)
																.equals("1")) {
													Log.d("karbarServiceTest", "Ala15");
													if (dbMethods.isDIYaHasAction(idDIYa)) {
														Log.d("karbarServiceTest", "Ala16");
														przywrocAkcjeIZmienStatusNaNiewykonane(dbMethods.getActionsLists(idDIYa), groupsWithAddedConditions);
													}
													break;
												} else {
													Log.d("karbarServiceTest", "Ala17");
												}
											}
										}// koniec grupy z warunkami
									}
								}
							}// koniec pojedynczej DIYa
						}
					}// koniec listy DIYas
				}
			}

		};
		//timer.scheduleAtFixedRate(timerTask, 100, 6000);
		timer.scheduleAtFixedRate(timerTask, 100, 60000);
	}
	
	/*
	 * 
	 * private void okresowewykonywanie_przed 12.04.2014() {

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			public void run() {

				Log.d("kkams", "service isRun? " + dbMethods.isServiceRunning());
				System.out.println("service isRun? "
						+ dbMethods.isServiceRunning());
				Log.d("karbarServiceTest", "Ala1");
				ArrayList<HashMap<String, String>> DIYas = dbMethods.getDIYaList();
				Log.d("karbarServiceTest", "Ala2");
				if (DIYas != null && !DIYas.isEmpty()) {
					Log.d("karbarServiceTest", "Ala3");
					for (HashMap<String, String> diya : DIYas) {
						Log.d("karbarServiceTest", "Ala4");
						int idDIYa = Integer.valueOf(diya.get(Constant.TASKS_KEY_ID));
						if (!dbMethods.isDIYaEmpty(idDIYa)) {
							Log.d("karbarServiceTest", "Ala5");
							if (diya.get(Constant.TASKS_KEY_ACTIVE).equals("true")) {
								Log.d("karbarServiceTest", "Ala6");
								if (dbMethods.isDIYaHasCondition(idDIYa)) {
									Log.d("karbarServiceTest", "Ala7");
									ArrayList<ArrayList<HashMap<String, String>>> AddedConditions = 
											dbMethods.getConditonLists(idDIYa);
									if (AddedConditions != null && !AddedConditions.isEmpty()) {
										Log.d("karbarServiceTest", "Ala8");
										for (ArrayList<HashMap<String, String>> groupsWithAddedConditions : AddedConditions) {
											Log.d("karbarServiceTest", "Ala9");
											if (!groupsWithAddedConditions.isEmpty()) {
												Log.d("karbarServiceTest", "Ala10");
												if (czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)) {
													Log.d("karbarServiceTest", "Ala11");
													HashMap<String, String> firstCondition = groupsWithAddedConditions.get(0);
													if (firstCondition
															.get(Constant.ADDED_CONDITIONS_KEY_EXECUTED_CONDITION)
															.equals("0")) {
														Log.d("karbarServiceTest", "Ala12");
														if (dbMethods.isDIYaHasAction(idDIYa)) {
															Log.d("karbarServiceTest", "Ala13");
															wykonajAkcjeIZmienIchStatusNaWykonane(dbMethods.getActionsLists(idDIYa));
														}
														break;
													} else {
														Log.d("karbarServiceTest", "Ala14");
														break;
													}
												} else if (!czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)
														&& groupsWithAddedConditions
																.get(0)
																.get(Constant.ADDED_CONDITIONS_KEY_EXECUTED_CONDITION)
																.equals("0")) {
													Log.d("karbarServiceTest", "Ala15");
													if (dbMethods.isDIYaHasAction(idDIYa)) {
														Log.d("karbarServiceTest", "Ala16");
														przywrocAkcjeIZmienStatusNaNiewykonane(dbMethods.getActionsLists(idDIYa));
													}
													break;
												} else {
													Log.d("karbarServiceTest", "Ala17");
												}
											}
										}// koniec grupy z warunkami
									}
								}
							}// koniec pojedynczej DIYa
						}
					}// koniec listy DIYas
				}
			}

		};
		//timer.scheduleAtFixedRate(timerTask, 100, 6000);
		timer.scheduleAtFixedRate(timerTask, 100, 60000);
	}
	 * 
	 * 
	 * 
	 * */
	
	
	
/*
	private void okresowewykonywanie2() {

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			public void run() {

				Log.d("kkams", "service isRun? " + dbMethods.isServiceRunning());

				ArrayList<HashMap<String, String>> DIYas = dbMethods
						.getDIYaList();

				for (HashMap<String, String> diya : DIYas) {
					if (diya.get(Constant.TASKS_KEY_ACTIVE).equals("1")) {// jeœli
																			// DIYa
																			// jest
																			// aktywna
						ArrayList<ArrayList<HashMap<String, String>>> AddedConditions = dbMethods
								.getConditonLists(Long.getLong(diya
										.get(Constant.TASKS_KEY_ID)));
						for (ArrayList<HashMap<String, String>> groupsWithAddedConditions : AddedConditions) {
							if (!groupsWithAddedConditions.isEmpty()) {
								if (czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)) {// jesli
																							// grupa
																							// warunkow
																							// prawdziwa
									if (groupsWithAddedConditions
											.get(0)
											.get(Constant.ADDED_CONDITIONS_KEY_EXECUTED_CONDITION)
											.equals("0")) {// jesli nie bylo
															// wykonane,
															// pobieram pierwszy
															// bo i tak we
															// wszystkich jest
															// to samo
										// wykonaj i zmien na wykonane
										wykonajAkcjeIZmienIchStatusNaWykonane(
												dbMethods
														.getActionsLists(Long.getLong(diya
																.get(Constant.TASKS_KEY_ID))),
												Long.getLong(diya
														.get(Constant.TASKS_KEY_ID)));
										break;
									} else {// bylo wykonane wczesniej
											// nic nie rob - tak bedzie w
											// wypadku, gdy zakladamy, ze jak
											// juz raz bylo cos wykonane, a
											// teraz ta grupa nie jest juz
											// prawdziwa, to przywracamy i nie
											// sprawdzamy reszty
											// d
										break;
									}
								} else if (!czyGrupaWarunkowPrawdziwa(groupsWithAddedConditions)
										&& groupsWithAddedConditions
												.get(0)
												.get(Constant.ADDED_CONDITIONS_KEY_EXECUTED_CONDITION)
												.equals("0")) {// jesli bylo
																// prawdziwe,
																// ale juz nie
																// jest
									// zmien na niewykonane
									// przywroc stan z przed wykonania
									przywrocAkcjeIZmienStatusNaNiewykonane(
											dbMethods
													.getActionsLists(Long.getLong(diya
															.get(Constant.TASKS_KEY_ID))),
											Long.getLong(diya
													.get(Constant.TASKS_KEY_ID)));
								} else {// jesli nie jest prawdziwe

								}
							}
						}// koniec grupy z warunkami
					}// koniec pojedynczej DIYa
				}// koniec listy DIYas

				/*
				 * int idWarunku = 0; int idWiersza; int warunek = 0; boolean
				 * trigger = true; Cursor c = mDbHelper.fetchAllDiy(); if
				 * (c.moveToFirst()) { do { trigger = true; idWiersza =
				 * c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID));
				 * System.out.println("Jestem w pêtli, w wierszu "+idWiersza);
				 * if
				 * ((c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ENABLED)
				 * )==1)){
				 * 
				 * 
				 * 
				 * System.out.println("DIYa aktywna "+idWiersza);
				 * 
				 * 
				 * warunek =
				 * c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE
				 * )); if(warunek == 1) {
				 * System.out.println("Jestem w sprawdzaniu triggera");
				 * if(!tr.dzienIGodzina(idWiersza)) trigger = false; }
				 * 
				 * 
				 * System.out.println("Jestem za pierwszym ifem, zwróci³: "+warunek
				 * +" a trigger "+trigger);
				 * 
				 * System.out.println("Jestem za pierwszym ifem, zwróci³: "+warunek
				 * +" a trigger "+trigger); warunek =
				 * c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_TRIGGER_LOCATION)); if(warunek == 1) {
				 * if(!tr.czyWDanymMiejscu(idWiersza)) trigger = false; }
				 * 
				 * System.out.println("Jestem za drugim ifem, zwróci³: "+warunek)
				 * ;
				 * 
				 * warunek =
				 * c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI
				 * )); if(warunek == 1) { if(!tr.czyWifiWlaczone(idWiersza))
				 * trigger = false; }
				 * 
				 * System.out.println("Jestem za trzecim ifem, zwróci³: "+warunek
				 * ); System.out.println("Trigger " + trigger);
				 * 
				 * if(trigger){ System.out.println("ala0.7");
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_WIFI)) == 1) {
				 * System.out.println("ala1");
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_WIFI_PARAM_TURN_ON)) == 1) {
				 * System.out.println("ala2");
				 * if(!c.getString(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_WIFI_PARAM_SSID)).equals("")){
				 * System.out.println("ala3");
				 * ac.podlaczWifiDoDanejSieci(idWiersza); } else{
				 * System.out.println("ala4"); ac.wlaczWifi(idWiersza); }
				 * 
				 * } else if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.
				 * KEY_ACTION_WIFI_PARAM_TURN_OFF)) == 1) {
				 * System.out.println("ala5"); ac.wylaczWifi(idWiersza); } }
				 * System.out.println("ala5.7");
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_NOTIFICATION)) == 1){
				 * System.out.println("ala6");
				 * ac.wyswietlPowiadomienie(idWiersza); }
				 * System.out.println("ala6.7");
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_SOUNDPROFILE)) == 1) {
				 * System.out.println("ala7");
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND))
				 * == 1) { System.out.println("ala8");
				 * ac.glosnoscDzwiek(idWiersza); } else
				 * if(c.getInt(c.getColumnIndexOrThrow
				 * (DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS
				 * )) == 1) { System.out.println("ala9");
				 * ac.glosnoscWibracje(idWiersza); } }
				 * 
				 * System.out.println("ala10"); } System.out.println("ala11");
				 * System
				 * .out.println("Jestem za trzecim ifem, zwróci³: "+warunek);
				 * System.out.println("Koñcowy wynik: " + trigger);
				 * 
				 * } } while (c.moveToNext()); }
				 * 
				 * System.out.println("ala12");
				 
			}

		};
		timer.scheduleAtFixedRate(timerTask, 100, 6000);
	}
*/
	private boolean czyGrupaWarunkowPrawdziwa(ArrayList<HashMap<String, String>> groupsWithAddedConditions) {
		ArrayList<Boolean> retArray = new ArrayList<Boolean>();
		Log.d("karbarServiceTest", "Basia1");
		if (groupsWithAddedConditions != null
				&& !groupsWithAddedConditions.isEmpty()) {
			Log.d("karbarServiceTest", "Basia2");
			for (HashMap<String, String> AddedCondition : groupsWithAddedConditions) {
				Log.d("karbarServiceTest", "Basia3");
				Integer id_con = Integer.valueOf(AddedCondition
						.get(Constant.ADDED_CONDITIONS_KEY_CONDITION_ID));
				String id_add_con = AddedCondition
						.get(Constant.ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS);
				String params = AddedCondition
						.get(Constant.ADDED_CONDITIONS_KEY_PARAMETERS_CONDITIONS);
				switch (id_con) {
				case (int) Constant.CONDITION_WIFI:
					Log.d("karbarServiceTest", "Basia4");
					boolean czy = tr.sprawdzWifi(id_add_con, params);
					retArray.add(czy);
					break;
				case (int) Constant.CONDITION_DATE:
					Log.d("karbarServiceTest", "Basia5");
					boolean czy1 = tr.sprawdzDate(id_add_con, params);
					retArray.add(czy1);
					break;

				case (int) Constant.CONDITION_GPS:
					Log.d("karbarServiceTest", "Basia6");
					boolean czy2 = tr.sprawdzGPS(id_add_con, params);
					retArray.add(czy2);
					break;

				case (int) Constant.CONDITION_TIME:
					Log.d("karbarServiceTest", "Basia7");
					boolean czy3 = tr.sprawdzCzas(id_add_con, params);
					Log.d("karbarServiceTest", "Basia7 czy " + czy3);
					retArray.add(czy3);	
					break;

				default:
					Log.d("karbarServiceTest", "Basia8");
					break;
				}
			}
		}
		
		boolean ret = true;
		
		for(Boolean bool : retArray){
			Log.d("karbarServiceTest", "Basia9");
			if(!bool.booleanValue()){
				Log.d("karbarServiceTest", "Basia10");
				ret = false;
				break;
			}
		}

		if (ret) {
			Log.d("karbarServiceTest", "Basia11");
			for (HashMap<String, String> AddedCondition : groupsWithAddedConditions) {
				Log.d("karbarServiceTest", "Basia12");
				String id_add_con = AddedCondition
						.get(Constant.ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS);
				dbMethods.setExecutedCondition(Long.valueOf(id_add_con), true);
			}
		}
		
		

		return ret;
	}

	public boolean wykonajAkcjeIZmienIchStatusNaWykonane(
			ArrayList<HashMap<String, String>> AddedActions) {
		Log.d("karbarServiceTest", "Cecylia1");
		if (AddedActions != null && !AddedActions.isEmpty()) {
			Log.d("karbarServiceTest", "Cecylia2");
			for (HashMap<String, String> addedAction : AddedActions) {
				Log.d("karbarServiceTest", "Cecylia3");
				int id_cact = Integer.valueOf(addedAction
						.get(Constant.ADDED_ACTIONS_KEY_ACTION_ID));
				Log.d("karbarServiceTest", "Cecylia3a: "+ id_cact);
				String id_add_act = addedAction
						.get(Constant.ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS);
				Log.d("karbarServiceTest", "Cecylia3b: " + id_add_act);
				String params = addedAction
						.get(Constant.ADDED_ACTIONS_KEY_PARAMETERS_ACTIONS);
				Log.d("karbarServiceTest", "Cecylia3c: " + params);
				switch (id_cact) {
				case (int) Constant.ACTION_WIFI:
					Log.d("karbarServiceTest", "Cecylia4");
					act.zmienStanWifi(id_add_act, params, false);
					dbMethods.setExecutedAction(Long.valueOf(id_add_act), true);
					break;
				case (int) Constant.ACTION_NOTIFICATION:
					Log.d("karbarServiceTest", "Cecylia5");
					act.wlaczPowiadomienie(id_add_act, params, false);
					dbMethods.setExecutedAction(Long.valueOf(id_add_act), true);
					break;
				case (int) Constant.ACTION_SOUND:
					Log.d("karbarServiceTest", "Cecylia6");
					act.ustawDzwiek(id_add_act, params, false);
					dbMethods.setExecutedAction(Long.valueOf(id_add_act), true);
					break;
				case (int) Constant.ACTION_VIBRATION:
					Log.d("karbarServiceTest", "Cecylia7");
					act.ustawWibracje(id_add_act, params, false);
					dbMethods.setExecutedAction(Long.valueOf(id_add_act), true);
					break;
				default:
					break;
				}
			}
		}
		return true;
	}

	public boolean przywrocAkcjeIZmienStatusNaNiewykonane(
			ArrayList<HashMap<String, String>> AddedActions, ArrayList<HashMap<String, String>> groupsWithAddedConditions) {
		Log.d("karbarServiceTest", "Dagmara1");
		if (AddedActions != null && !AddedActions.isEmpty()) {
			Log.d("karbarServiceTest", "Dagmara2");
			for (HashMap<String, String> addedAction : AddedActions) {
				Log.d("karbarServiceTest", "Dagmara3");
				Integer id_cact = Integer.valueOf(addedAction
						.get(Constant.ADDED_ACTIONS_KEY_ACTION_ID));
				String id_add_act = addedAction
						.get(Constant.ADDED_ACTIONS_KEY_ID_ADDEDD_ACTIONS);
				String before = addedAction
						.get(Constant.ADDED_ACTIONS_KEY_BEFORE_ACTION);
				Log.d("karolChuj", "before "+before);
				switch (id_cact) {
				case (int) Constant.ACTION_WIFI:
					Log.d("karbarServiceTest", "Dagmara4");
					act.zmienStanWifi(id_add_act, before, true);
					dbMethods
							.setExecutedAction(Long.valueOf(id_add_act), false);
					break;
				case (int) Constant.ACTION_NOTIFICATION:
					Log.d("karbarServiceTest", "Dagmara5");
					act.wlaczPowiadomienie(id_add_act, before, true);
					dbMethods
							.setExecutedAction(Long.valueOf(id_add_act), false);
					break;
				case (int) Constant.ACTION_SOUND:
					Log.d("karbarServiceTest", "Dagmara6");
					act.ustawDzwiek(id_add_act, before, true);
					dbMethods
							.setExecutedAction(Long.valueOf(id_add_act), false);
					break;
				case (int) Constant.ACTION_VIBRATION:
					Log.d("karbarServiceTest", "Dagmara7");
					act.ustawWibracje(id_add_act, before, true);
					dbMethods
							.setExecutedAction(Long.valueOf(id_add_act), false);
					break;
				default:
					break;
				}
			}
		}

		
		Log.d("karbarServiceTest", "Basia13");
		for (HashMap<String, String> AddedCondition : groupsWithAddedConditions) {
			Log.d("karbarServiceTest", "Basia14");
			String id_add_con = AddedCondition
					.get(Constant.ADDED_CONDITIONS_KEY_ID_ADDEDD_CONDITIONS);
			dbMethods.setExecutedCondition(Long.valueOf(id_add_con), false);
		}
		
		
		
		
		return false;
	}
}
