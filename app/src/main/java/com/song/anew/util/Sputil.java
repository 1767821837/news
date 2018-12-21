package com.song.anew.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Sputil {

   static SharedPreferences sp;

    public static boolean getBoolean(Context context, String key, boolean defau) {
        sp = context.getSharedPreferences("cofig", Context.MODE_PRIVATE);

        return sp.getBoolean(key, defau);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("cofig", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static void setString(Context context, String key, String  value) {
        sp = context.getSharedPreferences("cofig", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value);
        edit.commit();
    }
    public static String  getString(Context context, String key, String defau) {
        sp = context.getSharedPreferences("cofig", Context.MODE_PRIVATE);

        return sp.getString(key, defau);
    }
}
