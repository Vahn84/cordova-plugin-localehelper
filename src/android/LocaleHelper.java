package com.vahn.cordova.localehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleHelper {

	private static volatile LocaleHelper localeHelper;
    private static JSONObject localeMap;
	private Context context;
	private static String filename = "locale_test.json";
	private static File file;
    private static String currentLocale = "en";
    private static SharedPreferences sharedPreferences;

	private LocaleHelper() {
		if (localeHelper != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of the LocaleHelper class.");
        }
	}

	public static LocaleHelper getInstance(){
        if (localeHelper == null){ //if there is no instance available... create new one
              
              synchronized (LocaleHelper.class) {   //Check for the second time.
              //if there is no instance available... create new one
              if (localeHelper == null) {
                  localeHelper = new LocaleHelper();
              }

            }
        }

        return localeHelper;
    }

    public static void init (Context context) {
        if(localeHelper.localeMap == null) {
            localeHelper.context = context;
            localeHelper.file = new File(context.getFilesDir() + "/" + localeHelper.filename);
            localeHelper.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String tmplocale = localeHelper.sharedPreferences.getString("currentLocale", null);
            if(tmplocale!=null){
                localeHelper.currentLocale = tmplocale;
            } else {
                localeHelper.currentLocale = Locale.getDefault().getLanguage().toString();
            }
            tmplocale = null;

            try {
                FileInputStream fis = context.openFileInput(localeHelper.filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                localeHelper.localeMap = new JSONObject(sb.toString());

            } catch (Exception ex) {

            }
        }
    }

    public static String getCurrentLocale() {
        return localeHelper.currentLocale;
    }

    public static HashMap<Integer,String> getAvailableLanguages() {
        HashMap<Integer,String> languages = new HashMap<Integer, String>();
        try {
            for (int i = 0; i < localeHelper.localeMap.names().length(); i++) {
                Log.e("AVAILABLE LANGUAGES", localeHelper.localeMap.names().getString(i));
                languages.put(i,localeHelper.localeMap.names().getString(i));
            }
        } catch (JSONException jex){
            Log.e("errore json", jex.getMessage());
        }

        return languages;
    }

    public static void setLocale(String currentLocale){
        localeHelper.sharedPreferences.edit().putString("currentLocale", currentLocale).commit();
        localeHelper.currentLocale = currentLocale;
    }

    public static String getText(String varIndex) {
        String text = "";
        try{
            text = localeHelper.localeMap.getJSONObject(localeHelper.currentLocale).getString(varIndex);
        } catch (JSONException jex) {
            Log.e("errore json", jex.getMessage());
        }
        return text;
    }

}