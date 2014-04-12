package com.karbar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.karbar.dbPack.DbMethods;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Trigger extends Activity{
	
	Context mc;
	
	DbMethods dbMethods;
	
	public Trigger(Context c, DbMethods dbMethods) {
		// TODO Auto-generated constructor stub
		this.mc = c;
		this.dbMethods = dbMethods;
	}
	
	
	public boolean dzienIGodzinaTest2(int dzienPoczatkowy, int miesiacPoczatkowy, int rokPoczatkowy, int minutaPoczatkowy, int godzinaPoczatkowy,
										int dzienKoncowy, int miesiacKoncowy, int rokKoncowy, int minutaKoncowy, int godzinaKoncowy){
		Calendar c = Calendar.getInstance();
		System.out.println("hah4");
		/*int d9 = c.getFirstDayOfWeek();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int d10 = Calendar.MONDAY;
		*/
		Calendar poczatkowa  = GregorianCalendar.getInstance();
		poczatkowa.set(GregorianCalendar.YEAR, rokPoczatkowy);
		poczatkowa.set(GregorianCalendar.MONTH, miesiacPoczatkowy-1);
		poczatkowa.set(GregorianCalendar.DAY_OF_MONTH, dzienPoczatkowy);
		poczatkowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaPoczatkowy);
		poczatkowa.set(GregorianCalendar.MINUTE, minutaPoczatkowy);
		poczatkowa.set(GregorianCalendar.SECOND, 0);

		Calendar koncowa  = GregorianCalendar.getInstance();
		koncowa.set(GregorianCalendar.YEAR, rokKoncowy);
		koncowa.set(GregorianCalendar.MONTH, miesiacKoncowy-1);
		koncowa.set(GregorianCalendar.DAY_OF_MONTH, dzienKoncowy);
		koncowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaKoncowy);
		koncowa.set(GregorianCalendar.MINUTE, minutaKoncowy);
		koncowa.set(GregorianCalendar.SECOND, 0);
		
		int dzienAktualny = c.get(Calendar.DAY_OF_MONTH);
		int miesiacAktualny = c.get(Calendar.MONTH);//uwaga - styczen to miesiac numer 0, nie wiem czemu tak wymyslili, tak wiec np czerwiec to tutaj miesiac 5
		int rokAktualny = c.get(Calendar.YEAR);
		//int d8 = c.getFirstDayOfWeek();
		int godzinaAktualny = c.get(Calendar.HOUR_OF_DAY);
		int minutaAktualny = c.get(Calendar.MINUTE);
		//int sekundaAktualny = c.get(Calendar.SECOND);
		//int d3 = c.get(Calendar.DAY_OF_WEEK);
		miesiacAktualny +=1;//zeby bylo 5-maj
		
		//String dzienTygodnia = (String) android.text.format.DateFormat.format("EEEE", c.getTime()); 
		//Toast.makeText(getApplicationContext(), "Maj " + Calendar.MAY,  Toast.LENGTH_SHORT).show();
		System.out.println("Data pocz: " + dzienPoczatkowy +"." + miesiacPoczatkowy +"."+rokPoczatkowy+ ", czas: "+ godzinaPoczatkowy +":" + minutaPoczatkowy );
		System.out.println("Data: " + dzienAktualny +"." + miesiacAktualny +"."+rokAktualny+ ", czas: "+ godzinaAktualny +":" + minutaAktualny );
		System.out.println("Data kon: " + dzienKoncowy +"." + miesiacKoncowy +"."+rokKoncowy+ ", czas: "+ godzinaKoncowy +":" + minutaKoncowy);
		//TextView text = (TextView) findViewById(R.id.textTime);
		//text.setText(c.toString());
		//text.setText("Data: " + d1 +"." + dq +"."+d5+ ", czas: "+ d2 +":" + d6 +":"+d7 + ", dzien tygodnia: "+ d3 +" czyli " + dzienTygodnia);
		
		
		if(poczatkowa.before(c) && koncowa.after(c))
			return true;
		
		return false;
	}
	
	public boolean sprawdzDate(String id_add_con, String params){
		
		//String params = "" + dateSinceDay + "/~/" + dateSinceMonth + "/~/" + dateSinceYear + "/~/" + dateToDay + "/~/" + dateToMonth + "/~/" + dateToYear;
		
		String [] parametry = dbMethods.convertParamsIntoTab(params);
		if(parametry.length == 6){
			String dateSinceDay = parametry[0];
			String dateSinceMonth = parametry[1];
			String dateSinceYear = parametry[2];
			String dateToDay = parametry[3];
			String dateToMonth = parametry[4];
			String dateToYear = parametry[5];
			
			int dzienPoczatkowy = Integer.valueOf(dateSinceDay);
			int miesiacPoczatkowy = Integer.valueOf(dateSinceMonth);
			int rokPoczatkowy = Integer.valueOf(dateSinceYear); 
			int dzienKoncowy = Integer.valueOf(dateToDay);  
			int miesiacKoncowy = Integer.valueOf(dateToMonth);  
			int rokKoncowy = Integer.valueOf(dateToYear);
			
			return dzienIGodzina(dzienPoczatkowy, miesiacPoczatkowy, rokPoczatkowy, dzienKoncowy, miesiacKoncowy, rokKoncowy);
			
		}
		return false;
	}
	
	public boolean dzienIGodzina(int dzienPoczatkowy, int miesiacPoczatkowy, int rokPoczatkowy, 
									int dzienKoncowy,  int miesiacKoncowy,  int rokKoncowy){
		
		int godzinaPoczatkowy=0;
		int godzinaKoncowy=0;
		int minutaPoczatkowy=0;
		int minutaKoncowy=0;
		
		/*pobranie z bazy  przedzialow czasowych
		
		int dzienPoczatkowy=0;
		int dzienKoncowy=0;
		int miesiacPoczatkowy=0;
		int miesiacKoncowy=0;
		int rokPoczatkowy=0;
		int rokKoncowy=0;
		
		try{
		String dataPoczatkowy = "";
		String dataKoncowy = "";
		
		String dataPocz [];
		String dataKon [];
		
		String cosPocz[];
		String cosKon[];
		
		Cursor cu = mDbHelper.fetchAllDiy();
		if (cu.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
					if(cu.getInt(cu.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
						//if(column.equals(DiyDbAdapter.KEY_TRIGGER_DATE)) {
							//SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID));
							dataPoczatkowy = cu.getString(cu.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_FROM));
							dataKoncowy = cu.getString(cu.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_TO));
							//System.out.println(" trigger czy podlaczono do danej sieci wifi "+SSID_szukane);
						
						//}
					}
					
				}
			} while (cu.moveToNext());
		}
		
		dataPocz = dataPoczatkowy.split("\\:");
		dataKon = dataKoncowy.split("\\:");
		
		rokPoczatkowy = Integer.parseInt(dataPocz[0]);
		rokKoncowy = Integer.parseInt(dataKon[0]);
		
		miesiacPoczatkowy = Integer.parseInt(dataPocz[1]);
		miesiacKoncowy = Integer.parseInt(dataKon[1]);
		
		cosPocz = dataPocz[2].split(" ");
		cosKon = dataKon[2].split(" ");
		
		dzienPoczatkowy = Integer.parseInt(cosPocz[0]);
		dzienKoncowy = Integer.parseInt(cosKon[0]);
		
		
		godzinaPoczatkowy = Integer.parseInt(cosPocz[1]);
		godzinaKoncowy = Integer.parseInt(cosKon[1]);
		
		minutaPoczatkowy = Integer.parseInt(dataPocz[3]);
		minutaKoncowy = Integer.parseInt(dataKon[3]);
		}
		catch(Exception e){System.out.println(e.toString());}
		
		koniec pobierania*/

		Calendar c = Calendar.getInstance();
		
		Calendar poczatkowa  = GregorianCalendar.getInstance();
		poczatkowa.set(GregorianCalendar.YEAR, rokPoczatkowy);
		poczatkowa.set(GregorianCalendar.MONTH, miesiacPoczatkowy);
		poczatkowa.set(GregorianCalendar.DAY_OF_MONTH, dzienPoczatkowy);
		poczatkowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaPoczatkowy);
		poczatkowa.set(GregorianCalendar.MINUTE, minutaPoczatkowy);
		poczatkowa.set(GregorianCalendar.SECOND, 0);

		Calendar koncowa  = GregorianCalendar.getInstance();
		koncowa.set(GregorianCalendar.YEAR, rokKoncowy);
		koncowa.set(GregorianCalendar.MONTH, miesiacKoncowy);
		koncowa.set(GregorianCalendar.DAY_OF_MONTH, dzienKoncowy);
		koncowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaKoncowy);
		koncowa.set(GregorianCalendar.MINUTE, minutaKoncowy);
		koncowa.set(GregorianCalendar.SECOND, 0);
	
		if(poczatkowa.before(c) && koncowa.after(c))
			return true;
		
		return false;
	}
	
	public boolean sprawdzCzas(String id_add_con, String params){
		
		//int timeSinceHour, int timeSinceMinutes, int timeToHour, int timeToMinutes, boolean [] days){
		
		
		String [] parametry = dbMethods.convertParamsIntoTab(params);
		
		String timeSinceHour = parametry[0];
		String timeSinceMinutes = parametry[1];
		String timeToHour = parametry[2];
		String timeToMinutes = parametry[3];
		Log.d("karolChuj", "czas params[4] "+parametry[4]);
		String [] days = parametry[4].split(",");
		
		String pon = days[0];
		String wt = days[1];
		String sr = days[2];
		String czw = days[3];
		String pia = days[4];
		String sob = days[5];
		String niedz = days[6];
		
		int minutaPoczatkowy = Integer.valueOf(timeSinceMinutes);
		int godzinaPoczatkowy = Integer.valueOf(timeSinceHour);
		int minutaKoncowy = Integer.valueOf(timeToMinutes); 
		int godzinaKoncowy = Integer.valueOf(timeToHour);  

		boolean czyPon = pon.equals("true") ? true : false;
		boolean czyWt = wt.equals("true") ? true : false;
		boolean czySr = sr.equals("true") ? true : false; 
		boolean czyCzw = czw.equals("true") ? true : false; 
		boolean czyPia = pia.equals("true") ? true : false; 
		boolean czySob = sob.equals("true") ? true : false; 
		boolean czyNiedz = niedz.equals("true") ? true : false;
		
		return dzienTygodniaIGodzina(czyPon, czyWt, czySr, czyCzw, czyPia, czySob, czyNiedz, minutaPoczatkowy, godzinaPoczatkowy, minutaKoncowy, godzinaKoncowy);
	}
	
	
	public boolean dzienTygodniaIGodzina(boolean czyPon, boolean czyWt, boolean czySr, boolean czyCzw, 
										boolean czyPia, boolean czySob, boolean czyNiedz, int minutaPoczatkowy,
										int godzinaPoczatkowy, int minutaKoncowy, int godzinaKoncowy){

		/*pobranie z bazy  przedzialow czasowych*/
		/*
		boolean czyPon=false;
		boolean czyWt=false;
		boolean czySr=false; 
		boolean czyCzw=false; 
		boolean czyPia=false; 
		boolean czySob=false; 
		boolean czyNiedz=false;
		int minutaPoczatkowy=0;
		int godzinaPoczatkowy=0; 
		int minutaKoncowy=0; 
		int godzinaKoncowy=0;
		
		/*koniec pobierania*/
		
		Calendar c = Calendar.getInstance();
		
		int dzienAktualny = c.get(Calendar.DAY_OF_MONTH);
		int miesiacAktualny = c.get(Calendar.MONTH);//uwaga - styczen to miesiac numer 0, nie wiem czemu tak wymyslili, tak wiec np czerwiec to tutaj miesiac 5
		int rokAktualny = c.get(Calendar.YEAR);
		//int d8 = c.getFirstDayOfWeek();
		int godzinaAktualny = c.get(Calendar.HOUR_OF_DAY);
		int minutaAktualny = c.get(Calendar.MINUTE);
		//int sekundaAktualny = c.get(Calendar.SECOND);
		int dzienTygodniaAktualny = c.get(Calendar.DAY_OF_WEEK);
		
		Calendar poczatkowa  = GregorianCalendar.getInstance();
		poczatkowa.set(GregorianCalendar.YEAR, rokAktualny);
		poczatkowa.set(GregorianCalendar.MONTH, miesiacAktualny);
		poczatkowa.set(GregorianCalendar.DAY_OF_MONTH, dzienAktualny);
		poczatkowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaPoczatkowy);
		poczatkowa.set(GregorianCalendar.MINUTE, minutaPoczatkowy);
		poczatkowa.set(GregorianCalendar.SECOND, 0);

		Calendar koncowa  = GregorianCalendar.getInstance();
		koncowa.set(GregorianCalendar.YEAR, rokAktualny);
		koncowa.set(GregorianCalendar.MONTH, miesiacAktualny);
		koncowa.set(GregorianCalendar.DAY_OF_MONTH, dzienAktualny);
		koncowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaKoncowy);
		koncowa.set(GregorianCalendar.MINUTE, minutaKoncowy);
		koncowa.set(GregorianCalendar.SECOND, 0);

		ArrayList<Integer> czyDni = new ArrayList<Integer>();
		
		if(czyPon){czyDni.add(Calendar.MONDAY);}if(czyWt){czyDni.add(Calendar.TUESDAY);}if(czySr){czyDni.add(Calendar.WEDNESDAY);}
		if(czyCzw){czyDni.add(Calendar.THURSDAY);}if(czyPia){czyDni.add(Calendar.FRIDAY);}if(czySob){czyDni.add(Calendar.SATURDAY);}
		if(czyNiedz){czyDni.add(Calendar.SUNDAY);}
		
		boolean dzienTygodniaDobry = false;
		
		for(Integer iterator : czyDni){
			System.out.println("dzien dodany " + iterator);
			if(iterator.intValue() == dzienTygodniaAktualny)
				dzienTygodniaDobry = true;
		}
		
		if(dzienTygodniaDobry)
			if(poczatkowa.before(c) && koncowa.after(c))
				return true;
		
		
		return false;
	}
	
	public boolean dzienTygodniaIGodzinaTest(boolean czyPon, boolean czyWt, boolean czySr, boolean czyCzw, boolean czyPia, boolean czySob, boolean czyNiedz,
												int minutaPoczatkowy, int godzinaPoczatkowy, int minutaKoncowy, int godzinaKoncowy){
		
		//tu chyba w bazie bedzie zapisana lista dni tygodnia, ale to trzeba ustalic, dlatego nizej nic nie jest zrobione
		
		Calendar c = Calendar.getInstance();
		
		/*int d9 = c.getFirstDayOfWeek();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int d10 = Calendar.MONDAY;
		*/
		
		
		int dzienAktualny = c.get(Calendar.DAY_OF_MONTH);
		int miesiacAktualny = c.get(Calendar.MONTH);//uwaga - styczen to miesiac numer 0, nie wiem czemu tak wymyslili, tak wiec np czerwiec to tutaj miesiac 5
		int rokAktualny = c.get(Calendar.YEAR);
		//int d8 = c.getFirstDayOfWeek();
		int godzinaAktualny = c.get(Calendar.HOUR_OF_DAY);
		int minutaAktualny = c.get(Calendar.MINUTE);
		//int sekundaAktualny = c.get(Calendar.SECOND);
		int dzienTygodniaAktualny = c.get(Calendar.DAY_OF_WEEK);
		
		Calendar poczatkowa  = GregorianCalendar.getInstance();
		poczatkowa.set(GregorianCalendar.YEAR, rokAktualny);
		poczatkowa.set(GregorianCalendar.MONTH, miesiacAktualny);
		poczatkowa.set(GregorianCalendar.DAY_OF_MONTH, dzienAktualny);
		poczatkowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaPoczatkowy);
		poczatkowa.set(GregorianCalendar.MINUTE, minutaPoczatkowy);
		poczatkowa.set(GregorianCalendar.SECOND, 0);

		Calendar koncowa  = GregorianCalendar.getInstance();
		koncowa.set(GregorianCalendar.YEAR, rokAktualny);
		koncowa.set(GregorianCalendar.MONTH, miesiacAktualny);
		koncowa.set(GregorianCalendar.DAY_OF_MONTH, dzienAktualny);
		koncowa.set(GregorianCalendar.HOUR_OF_DAY, godzinaKoncowy);
		koncowa.set(GregorianCalendar.MINUTE, minutaKoncowy);
		koncowa.set(GregorianCalendar.SECOND, 0);
		
		
		
		System.out.println("Akrualny " + dzienTygodniaAktualny + " Pon " + Calendar.MONDAY + " wt "+ Calendar.TUESDAY + " sr "+ Calendar.WEDNESDAY + " czw "+ Calendar.THURSDAY + " piat "+ Calendar.FRIDAY + " sob "+ Calendar.SATURDAY + " niedz "+ Calendar.SUNDAY);
		
		//String dzienTygodnia = (String) android.text.format.DateFormat.format("EEEE", c.getTime()); 
		//Toast.makeText(getApplicationContext(), "Maj " + Calendar.MAY,  Toast.LENGTH_SHORT).show();
		
		ArrayList<Integer> czyDni = new ArrayList<Integer>();
		
		if(czyPon){czyDni.add(Calendar.MONDAY);}if(czyWt){czyDni.add(Calendar.TUESDAY);}if(czySr){czyDni.add(Calendar.WEDNESDAY);}
		if(czyCzw){czyDni.add(Calendar.THURSDAY);}if(czyPia){czyDni.add(Calendar.FRIDAY);}if(czySob){czyDni.add(Calendar.SATURDAY);}
		if(czyNiedz){czyDni.add(Calendar.SUNDAY);}
		
		boolean dzienTygodniaDobry = false;
		
		for(Integer iterator : czyDni){
			System.out.println("dzien dodany " + iterator);
			if(iterator.intValue() == dzienTygodniaAktualny)
				dzienTygodniaDobry = true;
		}
		
		System.out.println(dzienTygodniaDobry);
		
		System.out.println(poczatkowa.get(Calendar.YEAR) + " " + c.get(Calendar.YEAR) + " " +koncowa.get(Calendar.YEAR) + " " +poczatkowa.get(Calendar.MONTH) + " " +c.get(Calendar.MONTH) + " " +koncowa.get(Calendar.MONTH) + " " +
							poczatkowa.get(Calendar.DAY_OF_MONTH) + " " +c.get(Calendar.DAY_OF_MONTH) + " " +koncowa.get(Calendar.DAY_OF_MONTH) + " " +poczatkowa.get(Calendar.HOUR_OF_DAY) + " " +c.get(Calendar.HOUR_OF_DAY) + " " +koncowa.get(Calendar.HOUR_OF_DAY) + " " +
								poczatkowa.get(Calendar.MINUTE) + " " +c.get(Calendar.MINUTE) + " " +koncowa.get(Calendar.MINUTE) + " " +poczatkowa.get(Calendar.SECOND) + " " +c.get(Calendar.SECOND) + " " +koncowa.get(Calendar.SECOND));
		
		
		if(dzienTygodniaDobry)
			if(poczatkowa.before(c) && koncowa.after(c))
				return true;
		
		
		return false;
	}
	
	public boolean sprawdzWifi(String id_add_con, String params){
		String [] parametry = dbMethods.convertParamsIntoTab(params);
		if(parametry.length==1){
			if(parametry[0].equals("true")){
				return czyWifiWlaczone();
			}

		}
		else{//czyli jest nazwa sieci
			return czyWifiPodloczoneDoDanejSieci(parametry[1]);
		}	
		return false;
	}
	
	public boolean czyWifiWlaczone(){
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		if(wifimanager.isWifiEnabled()==true)
			return true;
		
		//wifimanager.getConnectionInfo();
		//wifimanager.getConnectionInfo().getSSID(); //pobiera ssid danej sieci czyli nazwe chyba
		
		return false;
		
	}
	
	public boolean czyWifiPodloczoneDoDanejSieciTest(String SSID_szukane){
		
		/*pobranie z bazy*/
		
		//String SSID_szukane = "";
		//String BSSID_szukane = "";
		
		/*koniec pobierania*/
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		try{
			if(wifimanager.isWifiEnabled()==true){
				String SSID_obecne = wifimanager.getConnectionInfo().getSSID();
				SSID_obecne = "\"" + SSID_obecne + "\"";
				System.out.println(SSID_obecne + " "+ SSID_szukane);
				//Toast.makeText(getApplicationContext(), SSID_obecne +" "+ SSID_szukane,  Toast.LENGTH_SHORT).show();
				System.out.println("myka");
				//String BSSID_obecne = wifimanager.getConnectionInfo().getBSSID();
				if(SSID_szukane.equals(SSID_obecne) == true){
					System.out.println("myk");
					return true;
					
				}
				//else if (BSSID_szukane.equals(BSSID_obecne) == true){
				//	return true;
				//}
			}
		}
		catch(Exception e){Toast.makeText(mc.getApplicationContext(), e.toString(),  Toast.LENGTH_SHORT).show();}
		
		//wifimanager.getConnectionInfo();
		//wifimanager.getConnectionInfo().getSSID(); //pobiera ssid danej sieci czyli nazwe chyba
		
		return false;
		
	}
	
	
	public boolean czyWifiPodloczoneDoDanejSieci(String SSID_szukane){
		
		/*pobranie z bazy
		
		String SSID_szukane = "";
		
		Cursor c = mDbHelper.fetchAllDiy();
		if (c.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
					if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
						//if(column.equals(DiyDbAdapter.KEY_TRIGGER_WIFI)) {
							SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID));

							System.out.println(" trigger czy podlaczono do danej sieci wifi "+SSID_szukane);
						
						//}
					}
					
				}
			} while (c.moveToNext());
		}
		
		
		koniec pobierania*/
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		try{
			if(wifimanager.isWifiEnabled()==true){
				String SSID_obecne = wifimanager.getConnectionInfo().getSSID();
				SSID_obecne = "\"" + SSID_obecne + "\"";

				if(SSID_szukane.equals(SSID_obecne) == true){
					return true;
					
				}
			}
		}
		catch(Exception e){Toast.makeText(mc.getApplicationContext(), e.toString(),  Toast.LENGTH_SHORT).show();}
		
		return false;
	}
	
	public boolean sprawdzGPS(String id_add_con, String params){
		String [] parametry = dbMethods.convertParamsIntoTab(params);
		
		String lat = parametry[0];
		String lng = parametry[1];
		String rad = parametry[2];
		String outside = parametry[3];
		
		double latitude_szukane = Double.valueOf(lat);
		double longitude_szukane = Double.valueOf(lng);
		double promien_szukane = Double.valueOf(rad);//w metrach
		boolean czyNaZewnatrz = outside.equals("true") ? true : false;
		
		return czyWDanymMiejscu(latitude_szukane, longitude_szukane, promien_szukane, czyNaZewnatrz);
	}
	
	public boolean czyWDanymMiejscu(double latitude_szukane, double longitude_szukane, double promien_szukane, 
									boolean czyNaZewnatrz){
		
		/*pobranie z bazy*/
		/*
		double latitude_szukane = 0;
		double longitude_szukane = 0;
		double promien_szukane = 0;//w metrach
		/*
		Cursor c = mDbHelper.fetchAllDiy();
		if (c.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
					if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
						//if(column.equals(DiyDbAdapter.KEY_TRIGGER_LOCATION)) {
							//SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID));

							latitude_szukane = c.getDouble(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LATITUDE));
							longitude_szukane = c.getDouble(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE));
							promien_szukane = c.getDouble(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_AREA)); 
							
							System.out.println(" trigger czy w danym miejscu " + latitude_szukane +" "+ longitude_szukane + " " + promien_szukane);
						
						//}
					}
					
				}
			} while (c.moveToNext());
		}
		
		/*koniec pobierania*/
		
		LocationManager locationManager;
		locationManager = (LocationManager) mc.getSystemService(Context.LOCATION_SERVICE);
		String providerStr = LocationManager.NETWORK_PROVIDER;//nie trzeba odswiezac samemu, zzera mniej baterii niz gps

		int a=0;
		
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, PendingIntent.getService(mc.getApplicationContext(), a, getIntent(), a));
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, PendingIntent.getService(mc.getApplicationContext(), a, getIntent(), a));
		//to wyzej dwa razy, bo czasami nie odswieza od razu
		Location location = locationManager.getLastKnownLocation(providerStr);
		
		double latitude = location.getLatitude();//n-s
		double longitude = location.getLongitude();//e-w
		
		//promien_szukane /= 1000;
		
		Location locationA = new Location("Obecny");

		locationA.setLatitude(latitude);
		locationA.setLongitude(longitude);

		Location locationB = new Location("Szukany");

		locationB.setLatitude(latitude_szukane);
		locationB.setLongitude(longitude_szukane);

		double distance = locationA.distanceTo(locationB);
		
		
		if(distance < promien_szukane){
			if(!czyNaZewnatrz)
				return true;
		}
		
		return false;
		
	}
	
		public boolean czyWDanymMiejscuTest(double latitude_szukane, double longitude_szukane, double promien_szukane){
		
		/*pobranie z bazy*/
		/*
		double latitude_szukane = 0;
		double longitude_szukane = 0;
		int promien_szukane = 0;
		
		/*koniec pobierania*/
		
			System.out.println("qwe1");
		LocationManager locationManager;
		locationManager = (LocationManager) mc.getSystemService(Context.LOCATION_SERVICE);
		String providerStr = LocationManager.NETWORK_PROVIDER;//nie trzeba odswiezac samemu, zzera mniej baterii niz gps
		//String providerStr = LocationManager.PASSIVE_PROVIDER;
		int a=0;
		System.out.println("qwe2");
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, PendingIntent.getService(mc.getApplicationContext(), a, getIntent(), a));
		locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, PendingIntent.getService(mc.getApplicationContext(), a, getIntent(), a));
		//to wyzej dwa razy, bo czasami nie odswieza od razu
		System.out.println("qwe3");
		Location location = locationManager.getLastKnownLocation(providerStr);
		System.out.println("qwe4");
		//location.set(new Location(providerStr));
		//locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, PendingIntent.getService(getApplicationContext(), a, getIntent(), a));
		//location = locationManager.getLastKnownLocation(providerStr);
		double latitude = location.getLatitude();//n-s
		double longitude = location.getLongitude();//e-w
		System.out.println("qwe5");
		//location.getSpeed();//predkosc
		//location.toString();
		//Toast.makeText(getApplicationContext(), location.getProvider(),  Toast.LENGTH_SHORT).show();
		//TextView text = (TextView) findViewById(R.id.textGPS);
    	//text.setText(location.toString());
		
		//jesli roznica pomiedzy wspolrzednymi jest mniejsza niz promien, to true
		
		if(Math.abs(latitude-latitude_szukane) < promien_szukane && Math.abs(longitude-longitude_szukane) < promien_szukane){
			System.out.println("qwe6");
			return true;
		}
		System.out.println("qwe0");
		return false;
		
	}
	
}
