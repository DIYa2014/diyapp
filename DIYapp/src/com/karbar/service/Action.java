package com.karbar.service;


import com.karbar.dbPack.DbMethods;
import com.karbar.diyapp.R;
import com.karbar.diyapp.utils.Constant;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Action  /*extends Activity*/{

	Context mc;
	
	DbMethods dbMethods;
	
	public Action(Context c, DbMethods dbMethods) {
		// TODO Auto-generated constructor stub
		this.mc = c;
		this.dbMethods = dbMethods;
	}
	
	
	public boolean zmienStanWifi(String id_add_act, String params, boolean czyPrzywracam){
		
		//zczytywanie before
		{
			String before = "";
			if(!czyPrzywracam){
				WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
				boolean stan = wifimanager.isWifiEnabled();
				before += stan +"/~/";
				if(stan){
					WifiInfo wifiInfo = wifimanager.getConnectionInfo();
					String siec = wifiInfo.getSSID();
					before += siec ;
				}
			}
			dbMethods.updateAddedAction(Long.valueOf(id_add_act), "", before);
		}
		
		String [] parametry = dbMethods.convertParamsIntoTab(params);
		if(parametry.length==1){
			if(parametry[0].equals("true")){
				return wlaczWifi();
			}
			else{
				return wylaczWifi();
			}
		}
		else{//czyli jest nazwa sieci
			return podlaczWifiDoDanejSieci(parametry[1]);
		}
	
	}
	public boolean wlaczWifi(){
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		
		if(wifimanager.isWifiEnabled()==true){
			return true;
		}	
		
		wifimanager.setWifiEnabled(true);
		return true;
		
	
	}
	
	public boolean wylaczWifi(){
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		
		if(wifimanager.isWifiEnabled()==false){
			return true;
		}	
		
		wifimanager.setWifiEnabled(false);
		return true;
	}
	
	
	public boolean podlaczWifiDoDanejSieci(String SSID_szukane){
		
		/*pobieram z bazy
		
		String SSID_szukane = "";
		
		Cursor c = mDbHelper.fetchAllDiy();
		if (c.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
					if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
						//if(column.equals(DiyDbAdapter.KEY_TRIGGER_WIFI)) {
							SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_WIFI_PARAM_SSID));

							System.out.println(" trigger czy podlaczono do danej sieci wifi "+SSID_szukane);
						
						//}
					}
					
				}
			} while (c.moveToNext());
		}
		
		koniec pobierania*/
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		if(wifimanager.isWifiEnabled()==false){
			
			wifimanager.setWifiEnabled(true);
			
		}
		
		if(wifimanager.isWifiEnabled()==true){
			
			int warunek = 0;
			
			int siec=-1;

			for(WifiConfiguration iterator : wifimanager.getConfiguredNetworks()){
				
				if(iterator.SSID.equals("\"" + SSID_szukane + "\"")){
					siec = iterator.networkId;
					warunek = 1;
				}
			}

			if(warunek == 1 && siec >= 0){
				boolean ew = wifimanager.enableNetwork(siec, true);//true oznacza, ze jesli jest sie podlaczonym do jakiejs sieci, a podlaczenie do sieci o id siec
														// sie nie powiedzie, to wifi jest rozlaczane, jesli false, to zostaje jak bylo
				if(!ew){
					wifimanager.setWifiEnabled(false);
				}
				
				return ew;
			}
			
			if(warunek == 0){
				wifimanager.setWifiEnabled(false);
				return false;
			}
		}
		
		return false;
	}
	
	public boolean podlaczWifiDoDanejSieciTest(String SSID_szukane){
		
		WifiManager wifimanager = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		
		if(wifimanager.isWifiEnabled()==false){
			
			wifimanager.setWifiEnabled(true);
			
		}
		
		if(wifimanager.isWifiEnabled()==true){
			
			/*pobieram z bazy*/
			
			//String SSID_szukane = "";
			//String BSSID_szukane = "";
			
			
			/*koniec pobierania*/
			
			int warunek = 0;
		
			int siec=-1;

			for(WifiConfiguration iterator : wifimanager.getConfiguredNetworks()){
				
				if(iterator.SSID.equals(SSID_szukane)){//nie wiem czmeu equals zawsze mi zwraca flase, dlatego nizej sztucznie sa ustawione id sieci i warunek
					System.out.println("bla1");
					siec = iterator.networkId;
					System.out.println("bla2 " + iterator.networkId);
					warunek = 1;
					System.out.println("bla3" + warunek);
				}
				
				/*albo szukanie po bssid, nie wiem co lepsze, chyba bssid, bo jak sie zmieni nazwa to dupa, a bssid to cos w rodzaju adresu mac sieci
				 */ 
				System.out.println(iterator.SSID + " " +  SSID_szukane);
				
				// if(iterator.BSSID.equals(BSSID_szukane)){
				//	 siec = iterator.networkId;
				//	 warunek = 1;
				// }

			}

			if(warunek == 1 && siec >= 0){
				boolean ew = wifimanager.enableNetwork(siec, true);//true oznacza, ze jesli jest sie podlaczonym do jakiejs sieci, a podlaczenie do sieci o id siec
														// sie nie powiedzie, to wifi jest rozlaczane, jesli false, to zostaje jak bylo
				if(!ew){
					wifimanager.setWifiEnabled(false);
				}
				
				return ew;
			}
			
			if(warunek == 0){
				wifimanager.setWifiEnabled(false);
				return false;
			}
		}
		
		return false;
	}
	
	public boolean ustawWibracje(String id_add_act, String params, boolean czyPrzywracam){
		
		{
			String before = "";
			if(!czyPrzywracam){
				AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
				before = "" + audiomanager.getRingerMode() +","+ audiomanager.getStreamVolume(AudioManager.STREAM_RING);
				
				
			}
			dbMethods.updateAddedAction(Long.valueOf(id_add_act), "", before);
		}
		
		if(!czyPrzywracam){
			return glosnoscWibracje();
		}
		else {
			String [] parametry = params.split(",");
			int glosnosc_zadana = Integer.valueOf(parametry[0]);
			int mode = Integer.valueOf(parametry[1]);
			return przywrocDzwiek(glosnosc_zadana, mode);
		}
	}
	
	public boolean glosnoscWibracje(){
		System.out.println("celina1");
		AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
		audiomanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);//tryb dzwonka (cos ala profil na starych nokiach) tu akurat wibracje
		System.out.println("celina2");
		return true;
	}
	
	public boolean ustawDzwiek(String id_add_act, String params, boolean czyPrzywracam){
		
		{
			String before = "";
			if(!czyPrzywracam){
				AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
				before = "" + audiomanager.getRingerMode() +","+ audiomanager.getStreamVolume(AudioManager.STREAM_RING);
				
				
			}
			dbMethods.updateAddedAction(Long.valueOf(id_add_act), "", before);
		}
		
		if(!czyPrzywracam){
			return glosnoscDzwiek(Integer.valueOf(params));
		}
		else {
			String [] parametry = params.split(",");
			int glosnosc_zadana = Integer.valueOf(parametry[0]);
			int mode = Integer.valueOf(parametry[1]);
			return przywrocDzwiek(glosnosc_zadana, mode);
		}
	}
	
	public boolean glosnoscDzwiek(int glosnosc_zadana){
		
		/*pobieram z bazy*/
		
		//int glosnosc_zadana = 0;
		System.out.println("basia1");
		/*
		Cursor c = mDbHelper.fetchAllDiy();
		System.out.println("basia1.5");
		if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
			System.out.println("basia1.9");
			glosnosc_zadana = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME));
			System.out.println("basia2");
			System.out.println(glosnosc_zadana);
		}

		
		/*koniec pobierania*/
		
		int mode = AudioManager.RINGER_MODE_NORMAL;
		System.out.println("basia3");
		AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
		audiomanager.setRingerMode(mode);//tryb dzwonka (cos ala profil na starych nokiach) tu akurat normalny
		System.out.println("basia4");
		int maxVol = audiomanager.getStreamMaxVolume(AudioManager.STREAM_RING);
		System.out.println("basia5");
		int flags=0;
		
		int ustaw_glosnosc = (int) (glosnosc_zadana * maxVol) / 100;
		
		if(ustaw_glosnosc >= maxVol){
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, maxVol, flags);
			System.out.println("basia6");
			return true;
		}
		else{
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, ustaw_glosnosc, flags);
			System.out.println("basia7");
			return true;
		}
		
	}
	
	public boolean przywrocDzwiek(int glosnosc_zadana, int mode){
		
		/*pobieram z bazy*/
		
		//int glosnosc_zadana = 0;
		System.out.println("basia1");
		/*
		Cursor c = mDbHelper.fetchAllDiy();
		System.out.println("basia1.5");
		if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
			System.out.println("basia1.9");
			glosnosc_zadana = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME));
			System.out.println("basia2");
			System.out.println(glosnosc_zadana);
		}

		
		/*koniec pobierania*/
		
		//int mode = AudioManager.RINGER_MODE_NORMAL;
		System.out.println("basia3");
		AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
		audiomanager.setRingerMode(mode);//tryb dzwonka (cos ala profil na starych nokiach) tu akurat normalny
		System.out.println("basia4");
		int maxVol = audiomanager.getStreamMaxVolume(AudioManager.STREAM_RING);
		System.out.println("basia5");
		int flags=0;
		
		
		
		if(glosnosc_zadana >= maxVol){
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, maxVol, flags);
			System.out.println("basia6");
			return true;
		}
		else{
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, glosnosc_zadana, flags);
			System.out.println("basia7");
			return true;
		}
		
	}
	
	public boolean glosnoscDzwiekTest(int glosnosc_zadana){
		
		/*pobieram z bazy*/
		
		//int glosnosc_zadana = 0;
		
		/*koniec pobierania*/
		
		AudioManager audiomanager = (AudioManager) mc.getSystemService(Context.AUDIO_SERVICE);
		audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);//tryb dzwonka (cos ala profil na starych nokiach) tu akurat normalny
		
		int maxVol = audiomanager.getStreamMaxVolume(AudioManager.STREAM_RING);
		//int obecny = audiomanager.getStreamVolume(AudioManager.STREAM_RING);
		
		int flags=0;
		if(glosnosc_zadana >= maxVol){
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, maxVol, flags);
			return true;
		}
		else{
			audiomanager.setStreamVolume(AudioManager.STREAM_RING, glosnosc_zadana, flags);
			return true;
		}
	}	
	
	public boolean wlaczPowiadomienie(String id_add_act, String params, boolean czyPrzywracam){

		if(!czyPrzywracam){
			
			int MY_NOTIFICATION = 1519 + Integer.valueOf(id_add_act);
			String [] parametry = dbMethods.convertParamsIntoTab(params);
			
			String tickerText_pobrany = parametry[0];
			String notificationTitle_pobrany = parametry[1];
			String notificationText_pobrany = parametry[2];
			
			dbMethods.updateAddedAction(Long.valueOf(id_add_act), "", ""+MY_NOTIFICATION);
			
			return wyswietlPowiadomienie(tickerText_pobrany, notificationTitle_pobrany, notificationText_pobrany, MY_NOTIFICATION);
		}
		else{
			dbMethods.updateAddedAction(Long.valueOf(id_add_act), "", "");
			return anulujPowiadomienie(Integer.valueOf(params));
		}
			
	}
	
	public boolean anulujPowiadomienie(int id){
		
		NotificationManager notificationManager = (NotificationManager) mc.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(id);
		
		return true;
	}
	
	public boolean wyswietlPowiadomienie(String tickerText_pobrany, String notificationTitle_pobrany,
										String notificationText_pobrany, int MY_NOTIFICATION){
		
		/*pobieram z bazy*/
		/*
		String tickerText_pobrany = "";
		String notificationTitle_pobrany = "";
		String notificationText_pobrany = "";*/
		int czyWWW = 0; //0-nie wyswietla strony, 1 - wyswietla www
		String adresStrony_pobrany = "";
		/*
		Cursor c = mDbHelper.fetchAllDiy();
		if (c.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
					if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID)) == idDIY){
						//SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID));
						tickerText_pobrany = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_NOTIFICATION_PARAM_TEXT));
						notificationTitle_pobrany = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_NOTIFICATION_PARAM_TEXT));
						notificationText_pobrany = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_NOTIFICATION_PARAM_TITLE));
					}
					
				}
			} while (c.moveToNext());
		}
		
		/*koniec pobierania*/
		
		NotificationManager notificationManager = (NotificationManager) mc.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int icon = R.drawable.ic_launcher;

	    long when = 0;

	    //Notification notification = new Notification(icon, tickerText_pobrany, when);
	    

	    
	      /*
	       * Flagi powiadomieñ

			Kolejnym wa¿nym elementem s¹ flagi naszego powiadomienia. Odpowiadaj¹ one za kilka ró¿nych ustawieñ. Oto niektóre z nich:

	    		Notification.FLAG_AUTO_CANCEL – sprawia, ¿e powiadomienie znika zaraz po klikniêciu,
	    		Notification.FLAG_NO_CLEAR – powiadomienie nie zostanie usuniête po klikniêciu w przycisk Clear/Wyczyœæ,
	    		Notification.FLAG_FOREGROUND_SERVICE – powiadomienie które przychodzi od aktualnie dzia³aj¹cego serwisu,
	    		Notification.FLAG_ONGOING_EVENT – powiadomienie przychodz¹ce z ci¹gle jeszcze dzia³aj¹cego Ÿród³a (oczekuj¹ce po³¹czenie telefoniczne).

	       */
	    long tab[] = {400, 200, 440};
	    int ledARGB = Color.RED; 
		int ledOnMS = 800;
		int ledOffMS = 400;
		Notification notification;
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		
	    if(czyWWW == 1){
	    
		    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adresStrony_pobrany));
		    PendingIntent pendingIntent = PendingIntent.getActivity(mc.getApplicationContext(), 0, intent, 0);
		    
		    notification = new Notification.Builder(mc)
		    								.setAutoCancel(true)
		    								.setContentTitle(notificationTitle_pobrany)
		    								.setContentText(notificationText_pobrany)
		    								.setSmallIcon(icon)
		    						        .setContentIntent(pendingIntent)
		    						        .setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1"))
		    						        .setTicker(tickerText_pobrany)
		    						        .setVibrate(tab)
		    						        .setLights(ledARGB, ledOnMS, ledOffMS)
		    						        .setSound(alarmSound)
		    						        .getNotification();
		    
		    //notification.flags |= Notification.FLAG_AUTO_CANCEL;//powiadomienie zniknie gdy kliniemy na nie
		    //notification.setLatestEventInfo(mc.getApplicationContext(), notificationTitle_pobrany, notificationText_pobrany, pendingIntent);
	    }
	    
	    else{
	    	
	    	Intent intent = null;
	    	PendingIntent pendingIntent = PendingIntent.getActivity(mc.getApplicationContext(), 0, intent, 0);
		    
	    	notification = new Notification.Builder(mc)
			.setAutoCancel(true)
			.setContentTitle(notificationTitle_pobrany)
			.setContentText(notificationText_pobrany)
			.setSmallIcon(icon)
	        .setContentIntent(pendingIntent)
	        .setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1"))
	        .setTicker(tickerText_pobrany)
	        .setVibrate(tab)
	        .setSound(alarmSound)
	        .getNotification();
	    	
	    	//notification.setLatestEventInfo(mc.getApplicationContext(), notificationTitle_pobrany, notificationText_pobrany, pendingIntent);
	    }
	      
//			notification.defaults = Notification.DEFAULT_VIBRATE;
//			przy wibracji nalezy pamietac o dodaniu do manifestu <uses-permission android:name="android.permission.VIBRATE" />
		 //400 ms dziala 200 ms nie dziala 440 ms dziala
		//notification.vibrate = tab;
		//notification.flags |= Notification.FLAG_SHOW_LIGHTS;//uzywanie diody led
		//notification.ledARGB = Color.RED; 
		//notification.ledOnMS = 800;
		//notification.ledOffMS = 400;
		//notification.defaults |= Notification.DEFAULT_SOUND;
//			notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");
//			notification.sound = Uri.parse("file:///sdcard/.ringtonetrimmer/ringtones/ring.mp3"); -- dowolny dzwiek z tel
		
		
		
		notificationManager.notify(MY_NOTIFICATION, notification);
		
		
		
		return true;
	}
	
	public boolean wyswietlPowiadomienieTest(String tickerText_pobrany, String notificationTitle_pobrany, String notificationText_pobrany, int czyWWW, String adresStrony_pobrany){
		
		/*pobieram z bazy*/
		/*
		String tickerText_pobrany = "";
		String notificationTitle_pobrany = "";
		String notificationText_pobrany = "";
		int czyWWW = 0; //0-nie wyswietla strony, 1 - wyswietla www
		String adresStrony_pobrany = "";
		
		/*koniec pobierania*/
		
		NotificationManager notificationManager = (NotificationManager) mc.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int icon = R.drawable.ic_launcher;
	    //String tickerText = "Powiadomionko";
	    long when = 0;
	    //Notification notification = new Notification(icon, tickerText, when);
	    Notification notification = new Notification(icon, tickerText_pobrany, when);
	    
	    //notification.number = 3;
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;//powiadomienie zniknie gdy kliniemy na nie
	      /*
	       * Flagi powiadomieñ

			Kolejnym wa¿nym elementem s¹ flagi naszego powiadomienia. Odpowiadaj¹ one za kilka ró¿nych ustawieñ. Oto niektóre z nich:

	    		Notification.FLAG_AUTO_CANCEL – sprawia, ¿e powiadomienie znika zaraz po klikniêciu,
	    		Notification.FLAG_NO_CLEAR – powiadomienie nie zostanie usuniête po klikniêciu w przycisk Clear/Wyczyœæ,
	    		Notification.FLAG_FOREGROUND_SERVICE – powiadomienie które przychodzi od aktualnie dzia³aj¹cego serwisu,
	    		Notification.FLAG_ONGOING_EVENT – powiadomienie przychodz¹ce z ci¹gle jeszcze dzia³aj¹cego Ÿród³a (oczekuj¹ce po³¹czenie telefoniczne).

	       */
	    //String notificationTitle = "Takie sobie";
	    //String notificationText = "Klikniêcie w³¹cza iSODa w przegl¹darce";
	    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.isod.ee.pw.edu.pl"));
	    
	    if(czyWWW == 1){
	    
		    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adresStrony_pobrany));
		    PendingIntent pendingIntent = PendingIntent.getActivity(mc.getApplicationContext(), 0, intent, 0);
		    notification.setLatestEventInfo(mc.getApplicationContext(), notificationTitle_pobrany, notificationText_pobrany, pendingIntent);
	    }
	    
	    else{
	    	
	    	Intent intent = null;
	    	PendingIntent pendingIntent = PendingIntent.getActivity(mc.getApplicationContext(), 0, intent, 0);
		    notification.setLatestEventInfo(mc.getApplicationContext(), notificationTitle_pobrany, notificationText_pobrany, pendingIntent);
	    }
	      
//			notification.defaults = Notification.DEFAULT_VIBRATE;
//			przy wibracji nalezy pamietac o dodaniu do manifestu <uses-permission android:name="android.permission.VIBRATE" />
		long tab[] = {400, 200, 440}; //400 ms dziala 200 ms nie dziala 440 ms dziala
		notification.vibrate = tab;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;//uzywanie diody led
		notification.ledARGB = Color.RED; 
		notification.ledOnMS = 800;
		notification.ledOffMS = 400;
		notification.defaults |= Notification.DEFAULT_SOUND;
//			notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");
//			notification.sound = Uri.parse("file:///sdcard/.ringtonetrimmer/ringtones/ring.mp3"); -- dowolny dzwiek z tel
		
		int MY_NOTIFICATION = 1;
		
		notificationManager.notify(MY_NOTIFICATION, notification);
		
		return true;
	}
	
}
