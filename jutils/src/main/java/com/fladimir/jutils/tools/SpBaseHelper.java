package com.fladimir.jutils.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NingJiang on 2017/5/3.
 * Class Note:
 */

public class SpBaseHelper {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public SpBaseHelper(Context mContext) {
        this.sp = mContext.getSharedPreferences("oneyear_data",
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 存String
     *
     * @param key   存放数据的健
     * @param value 存放数据的值
     */
    public static void setStringData(
            String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 存Int
     *
     * @param key
     * @param value
     */
    public static void setIntData(
            String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 存Boolean
     *
     * @param key
     * @param value
     */
    public static void setBooleanData(
            String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getStringData(String key) {
        return sp.getString(key, "");
    }

    public static int getIntData(String key) {
        return sp.getInt(key, 0);
    }

    public boolean getBooleanData(String key) {
        return sp.getBoolean(key, false);
    }


}
