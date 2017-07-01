package com.fladimir.jutils.tools;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fladimir.jutils.R;
import com.fladimir.jutils.tools.Logger.Logger;
import com.fladimir.jutils.ui.toast.Toasty;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主要功能: 管理和回收Act
 *
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil
 * @author: AbrahamCaiJin
 * @date: 2017年05月03日 16:37
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */
public class ActivityTools {


    //存储ActivityStack
    private static Stack<Activity> activityStack = new Stack<Activity>();

    //单例模式
    private static ActivityTools instance;


    /**
     * 单列堆栈集合对象
     *
     * @return ActivityTools 单利堆栈集合对象
     */
    public static ActivityTools getInstance() {
        if (instance == null) {
            instance = new ActivityTools();
        }
        return instance;
    }


    /**
     * 堆栈中销毁并移除
     *
     * @param activity 指定Act对象
     */
    public void removeActivity(Activity activity) {
        Logger.i("ActivityTools-->>removeActivity", activity != null ? activity.toString() : "");
        if (null != activity) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            //创建临时集合对象
            Stack<Activity> activityTemp = new Stack<Activity>();
            for (Activity activity : activityStack) {
                if (null != activity) {
                    //添加到临时集合中
                    activityTemp.add(activity);
                    //结束Activity
                    activity.finish();
                }
            }
            activityStack.removeAll(activityTemp);
        }
        Logger.i("ActivityTools-->>removeAllActivity", "removeAllActivity");
        System.gc();
        System.exit(0);
    }


    /**
     * 获取当前Act对象
     *
     * @return Activity 当前act
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        Logger.i("ActivityTools-->>currentActivity", activity + "");
        return activity;
    }


    /**
     * 获得当前Act的类名
     *
     * @return String
     */
    public String getCurrentActivityName() {
        String actSimpleName = "";
        if (!activityStack.empty()) {
            actSimpleName = activityStack.lastElement().getClass().getSimpleName();
        }
        Logger.i("ActivityTools-->>getCurrentActivityName", actSimpleName);
        return actSimpleName;
    }


    /**
     * 将Act纳入推栈集合中
     *
     * @param activity Act对象
     */
    public void addActivity(Activity activity) {
        Logger.i("ActivityTools-->>addActivity", activity != null ? activity.toString() : "");
        if (null == activityStack) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }


    /**
     * 退出栈中所有Activity
     *
     * @param cls
     * @return void
     */
    public void exitApp(Class<?> cls) {
        Logger.i("ActivityTools-->>exitApp", "exitApp-->>占用内存：" + Runtime.getRuntime().totalMemory());
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
        }
        System.gc();
        System.exit(0);
    }

    private static Boolean isExit = false;

    /**
     * 退出App程序应用
     *
     * @param context 上下文
     * @return boolean True退出|False提示
     */
    public static boolean exitApp(Context context) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            //信息提示
            Toasty.nomal(context, context.getResources().getString(R.string.sys_exit_tip));
            //创建定时器
            tExit = new Timer();
            //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    //取消退出
                    isExit = false;
                }
            }, 2000);
        } else {
            ActivityTools.getInstance().removeAllActivity();
            //创建ACTION_MAIN
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Context content = ((Activity) context);
            //启动ACTION_MAIN
            content.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        Logger.i("AppExit2Back-->>exitApp", isExit + "");
        Logger.i("AppExit2Back-->>exitApp", "最大内存：" + Runtime.getRuntime().maxMemory());
        Logger.i("AppExit2Back-->>exitApp", "占用内存：" + Runtime.getRuntime().totalMemory());
        Logger.i("AppExit2Back-->>exitApp", "空闲内存：" + Runtime.getRuntime().freeMemory());
        return isExit;
    }


}
