package com.katerinachinnappan.homework2;

import android.content.Context;
import android.content.SharedPreferences;

public class AppInfo {

    private static AppInfo instance = null;
    //tags for each string
    private static final String COLOR_NAME1 = "color1";
    private static final String COLOR_NAME2 = "color2";
    private static final String COLOR_NAME3 = "color3";

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    //these are the string fields for each string in each activity
    public String sharedString1;
    public String sharedString2;
    public String sharedString3;

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if (instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            //setting my shared preferences
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            //when i reload the app, i want to save my previous input
            instance.sharedString1 = settings.getString(COLOR_NAME1, null);
            instance.sharedString2 = settings.getString(COLOR_NAME2, null);
            instance.sharedString3 = settings.getString(COLOR_NAME3, null);
        }
        return instance;
    }

    //set my string Shared preferences for string 1 in Activity1
    public void setString1(String c) {
        instance.sharedString1 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME1, c);
        editor.commit();
    }

    //set my string Shared preferences for string 2 in Activity2
    public void setString2(String c) {
        instance.sharedString2 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME2, c);
        editor.commit();
    }

    //set my string Shared preferences for string 3 in Activity3
    public void setString3(String c) {
        instance.sharedString3 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME3, c);
        editor.commit();


    }
}
