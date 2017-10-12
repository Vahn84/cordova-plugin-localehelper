package com.vahn.cordova.localehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.share.Share;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by vahn on 12/10/2017.
 */

public class LocaleHelperPlugin extends CordovaPlugin {
    public static final String SETLOCALE = "setLocale";
    public static final String GETLOCALE = "getLocale";
    public static final String GEAVAILABLELANGUAGES= "getAvailableLanguages";
    LocaleHelper localeHelper;
    Context context;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        context = cordova.getActivity().getApplicationContext();
        localeHelper = LocaleHelper.getInstance();
        localeHelper.init(context);

        if(action.equalsIgnoreCase(SETLOCALE)){
            String locale = args.getString(0);
            localeHelper.setLocale(locale);
            pluginCallback(callbackContext, context, localeHelper.getCurrentLocale());

        } else if(action.equalsIgnoreCase(GETLOCALE)){
            pluginCallback(callbackContext, context, localeHelper.getCurrentLocale());
        } else if(action.equalsIgnoreCase(GEAVAILABLELANGUAGES)){
            HashMap<Integer, String> result = localeHelper.getAvailableLanguages();
            JSONArray json = new JSONArray(result);
            pluginCallback(callbackContext, context, json.toString());
        }

        return true;
    }


    public void pluginCallback(CallbackContext callbackContext, Context context, String response){
        JSONObject parameter = new JSONObject();

        try {

            parameter.put("response", response);
            PluginResult result = new PluginResult(PluginResult.Status.OK, parameter);
            result.setKeepCallback(true);
            callbackContext.sendPluginResult(result);

        } catch(JSONException jex) {
            Log.println(Log.ASSERT, "JSON ERROR", jex.getMessage());
        }

    }
}
