package com.innerman.emotracker.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by petrpopov on 09.03.14.
 */
public class SimpleDataStorage {

    private Context context;
    private String configName;

    public SimpleDataStorage(Context context, String configName) {

        this.context = context;
        this.configName = configName;
    }


    public String getString(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(configName, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public void setString(String key, String value) {

        SharedPreferences.Editor editor = getEditor();

        editor.putString(key, value);
        editor.commit();
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences sharedPref = context.getSharedPreferences(configName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        return editor;
    }
}
