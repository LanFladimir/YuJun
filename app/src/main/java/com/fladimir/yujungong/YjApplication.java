package com.fladimir.yujungong;

import android.app.Application;
import android.content.Context;

/**
 * Created by NingJiang on 2017/6/23.
 * Class Note:
 */

public class YjApplication extends Application {

    private static Application instance;

    public static Context instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
