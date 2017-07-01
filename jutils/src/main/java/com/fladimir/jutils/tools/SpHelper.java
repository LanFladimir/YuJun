package com.fladimir.jutils.tools;

import android.content.Context;

/**
 * Created by NingJiang on 2017/5/3.
 * Class Note:SP数据保存
 */

public class SpHelper {


    /**
     * 是否初次登陆
     *
     * @return
     */
    public static boolean firstOpen(Context context) {
        return new SpBaseHelper(context).getBooleanData("firstopen");
    }

    public static void setFirstOpen(Context context, boolean f) {
        new SpBaseHelper(context).setBooleanData("firstopen", f);
    }

    /**
     * 软键盘高度
     *
     * @param hight
     */
    public static void setSoftInputHight(Context context, int hight) {
        new SpBaseHelper(context).setIntData("inputHight", hight);
    }

    public static int getSoftInputHight(Context context) {
        return new SpBaseHelper(context).getIntData("inputHight");
    }
}
