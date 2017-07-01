package com.fladimir.jutils.tools;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.fladimir.jutils.tools.Logger.Logger;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by NingJiang on 2017/6/7.
 * Class Note:
 */

public class SystyemTools {


    private static SystyemTools phoneUtil;

    public static SystyemTools getInstance() {
        if (phoneUtil == null) {
            synchronized (SystyemTools.class) {
                if (phoneUtil == null) {
                    phoneUtil = new SystyemTools();
                }
            }
        }
        return phoneUtil;
    }

    /**
     * get App versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * get App Name
     *
     * @param context
     * @return String
     */
    public static String getAppName(Context context) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException var4) {
            var4.printStackTrace();
            return "unKnown";
        }
    }


    /**
     * 获取应用图标
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Drawable appIcon = null;
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
            appIcon = applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    /**
     * 获取应用第一次安装日期
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getAppFirstInstallTime(Context context, String packageName) {
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            lastUpdateTime = packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }

    /**
     * 获取应用更新日期
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getAppLastUpdateTime(Context context, String packageName) {
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }

    /**
     * 获取应用大小
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getAppSize(Context context, String packageName) {
        long appSize = 0;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            appSize = new File(applicationInfo.sourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appSize;
    }

    /**
     * 获取应用apk文件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppApk(Context context, String packageName) {
        String sourceDir = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            sourceDir = applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sourceDir;
    }

    /**
     * 获取应用的安装市场
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppInstaller(Context context, String packageName) {
        return context.getPackageManager().getInstallerPackageName(packageName);
    }

    /**
     * 获取应用签名
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppSign(Context context, String packageName) {
        try {
            PackageInfo pis = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(SystyemTools.class.getName() + "the " + packageName + "'s application not found");
        }
    }

    public static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用兼容sdk
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getAppTargetSdkVersion(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            return applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取应用uid
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getAppUid(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            return applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取Cpu内核数
     *
     * @return
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }

            });
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获得root权限
     *
     * @param context
     * @return
     */
    public static boolean getRootPermission(Context context) {
        String packageCodePath = context.getPackageCodePath();
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + packageCodePath;
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 获取应用的所有权限
     *
     * @param context
     * @param packname
     * @return
     */
    public static String[] getAppPermissions(Context context, String packname) {
        String[] requestedPermissions = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
            requestedPermissions = info.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return requestedPermissions;
    }

    /**
     * 是否有权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        if (context != null && !TextUtils.isEmpty(permission)) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context
                            .getPackageName())) {
                        return true;
                    }
                    Log.d("AppUtils", "Have you  declared permission " + permission + " in AndroidManifest.xml ?");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications) {
            if (packageName.equals(in.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }

    /**
     * 安装应用
     *
     * @param context
     * @param filePath
     * @return
     */
    public static boolean installApk(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean uninstallApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 是否是系统应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSystemApp(Context context, String packageName) {
        boolean isSys = false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
            if (applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                isSys = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isSys = false;
        }
        return isSys;
    }

    /**
     * 服务是否在运行
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo si : servicesList) {
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 停止服务
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }

    /**
     * 结束进程
     *
     * @param context
     * @param pid
     * @param processName
     */
    public static void killProcesses(Context context, int pid, String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName;
        try {
            if (!processName.contains(":")) {
                packageName = processName;
            } else {
                packageName = processName.split(":")[0];
            }
            activityManager.killBackgroundProcesses(packageName);
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行脚本
     *
     * @param script
     * @return
     */
    public static String runScript(String script) {
        String sRet;
        try {
            final Process m_process = Runtime.getRuntime().exec(script);
            final StringBuilder sbread = new StringBuilder();
            Thread tout = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getInputStream()),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sbread.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            tout.start();

            final StringBuilder sberr = new StringBuilder();
            Thread terr = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getErrorStream()),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sberr.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            terr.start();

            m_process.waitFor();
            while (tout.isAlive()) {
                Thread.sleep(50);
            }
            if (terr.isAlive())
                terr.interrupt();
            String stdout = sbread.toString();
            String stderr = sberr.toString();
            sRet = stdout + stderr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sRet;
    }

    /**
     * 启动应用
     *
     * @param context
     * @param packagename
     */
    public static void runApp(Context context, String packagename) {
        context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage(packagename)));
    }


    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * 获取手机型号
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机宽度
     */
    @SuppressWarnings("deprecation")
    public int getPhoneWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取手机高度
     *
     * @param context
     */
    @SuppressWarnings("deprecation")
    public int getPhoneHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取手机imei串号 ,GSM手机的 IMEI 和 CDMA手机的 MEID.
     *
     * @param context
     */
    public String getPhoneImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getDeviceId();
    }

    /**
     * 获取手机sim卡号
     *
     * @param context
     */
    public String getPhoneSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getSubscriberId();
    }

    /**
     * 获取手机号
     *
     * @param context
     */
    public String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getLine1Number();
    }

    /**
     * 判断sd卡是否挂载
     */
    public boolean isSDCardMount() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取sd卡剩余空间的大小
     */
    @SuppressWarnings("deprecation")
    public long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory(); // 取得SD卡文件路径
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize(); // 获取单个数据块的大小(Byte)
        long freeBlocks = sf.getAvailableBlocks();// 空闲的数据块的数量
        // 返回SD卡空闲大小
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 获取sd卡空间的总大小
     */
    @SuppressWarnings("deprecation")
    public long getSDAllSize() {
        File path = Environment.getExternalStorageDirectory(); // 取得SD卡文件路径
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize(); // 获取单个数据块的大小(Byte)
        long allBlocks = sf.getBlockCount(); // 获取所有数据块数
        // 返回SD卡大小
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 判断是否是平板
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断一个apk是否安装
     *
     * @param context
     * @param packageName
     */
    public boolean isApkInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            return false;
        }
        return true;
    }

//    /**
//     * 拨打电话
//     *
//     * @param context
//     * @param phoneNum
//     */
//    public void call(Context context, String phoneNum) throws Exception {
//        if (phoneNum != null && !phoneNum.equals("")) {
//            Uri uri = Uri.parse("tel:" + phoneNum);
//            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//            context.startActivity(intent);
//        }
//    }

//    /**
//     * 打开网页
//     */
//    public void openWeb(Context context, String url) {
//        try {
//            Uri uri = Uri.parse(url);
//            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 获取应用权限 名称列表
     */
    public String[] getAppPermissions(Context context)
            throws NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),
                PackageManager.GET_PERMISSIONS);
        return getAppPermissions(packageInfo);
    }

    public String[] getAppPermissions(PackageInfo packageInfo)
            throws NameNotFoundException {
        return packageInfo.requestedPermissions;
    }

    /**
     * 获取手机内安装的应用
     */
    public List<PackageInfo> getInstalledApp(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.getInstalledPackages(0);
    }

    /**
     * 获取手机安装非系统应用
     */
    @SuppressWarnings("static-access")
    public List<PackageInfo> getUserInstalledApp(Context context) {
        List<PackageInfo> infos = getInstalledApp(context);
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        for (PackageInfo info : infos) {
            if ((info.applicationInfo.flags & info.applicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(info);
            }
        }
        infos.clear();
        infos = null;
        return apps;
    }

    /**
     * 获取安装应用的信息
     */
    public Map<String, Object> getInstalledAppInfo(Context context,
                                                   PackageInfo info) {
        Map<String, Object> appInfos = new HashMap<String, Object>();
        PackageManager pm = context.getPackageManager();
        ApplicationInfo aif = info.applicationInfo;
        appInfos.put("icon", pm.getApplicationIcon(aif));
        appInfos.put("lable", pm.getApplicationLabel(aif));
        appInfos.put("packageName", aif.packageName);
        return appInfos;
    }

    /**
     * 打开指定包名的应用
     */
    public void startAppPkg(Context context, String pkg) {
        Intent startIntent = context.getPackageManager()
                .getLaunchIntentForPackage(pkg);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startIntent);
    }

    /**
     * 卸载指定包名的应用
     */
    public void unInstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(uri);
        context.startActivity(intent);
    }

//    /**
//     * 手机号判断
//     */
//    public static boolean isMobileNO(String mobile) {
//        Pattern p = Pattern
//            .compile("^((145|147)|(15[^4])|(17[0-9])|((13|18)[0-9]))\\d{8}$");
//        Matcher m = p.matcher(mobile);
//        return m.matches();
//    }

    /**
     * 直接呼叫指定的号码(需要<uses-permission
     * android:name="android.permission.CALL_PHONE"/>权限)
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    public static void callPhone(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_CALL, uri);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mContext.startActivity(call);
    }

    /**
     * 跳转至拨号界面
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    public static void toCallPhoneActivity(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent call = new Intent(Intent.ACTION_DIAL, uri);
        mContext.startActivity(call);
    }

    /**
     * 直接调用短信API发送信息(设置监听发送和接收状态)
     *
     * @param strPhone      手机号码
     * @param strMsgContext 短信内容
     */
    public static void sendMessage(final Context mContext,
                                   final String strPhone, final String strMsgContext) {

        // 处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendIntent = PendingIntent.getBroadcast(mContext, 0,
                sentIntent, 0);
        // register the Broadcast Receivers
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext, "短信发送成功", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        // 处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent backIntent = PendingIntent.getBroadcast(mContext, 0,
                deliverIntent, 0);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                Toast.makeText(mContext, strPhone + "已经成功接收",
                        Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        // 拆分短信内容（手机短信长度限制）
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> msgList = smsManager.divideMessage(strMsgContext);
        for (String text : msgList) {
            smsManager.sendTextMessage(strPhone, null, text, sendIntent,
                    backIntent);
        }
    }

    /**
     * 跳转至发送短信界面(自动设置接收方的号码)
     *
     * @param mContext      Activity
     * @param strPhone      手机号码
     * @param strMsgContext 短信内容
     */
    public static void toSendMessageActivity(Context mContext, String strPhone,
                                             String strMsgContext) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(strPhone)) {
            Uri uri = Uri.parse("smsto:" + strPhone);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            sendIntent.putExtra("sms_body", strMsgContext);
            mContext.startActivity(sendIntent);
        }
    }

    /**
     * 跳转至联系人选择界面
     *
     * @param mContext    上下文
     * @param requestCode 请求返回区分代码
     */
    public static void toChooseContactsList(Activity mContext, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取选择的联系人的手机号码
     *
     * @param mContext   上下文
     * @param resultCode 请求返回Result状态区分代码
     * @param data       onActivityResult返回的Intent
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getChoosedPhoneNumber(Activity mContext,
                                               int resultCode, Intent data) {
        // 返回结果
        String phoneResult = "";
        if (Activity.RESULT_OK == resultCode) {
            Uri uri = data.getData();
            Cursor mCursor = mContext.managedQuery(uri, null, null, null, null);
            mCursor.moveToFirst();

            int phoneColumn = mCursor
                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = mCursor.getInt(phoneColumn);
            if (phoneNum > 0) {
                // 获得联系人的ID号
                int idColumn = mCursor
                        .getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = mCursor.getString(idColumn);
                // 获得联系人的电话号码的cursor;
                Cursor phones = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                if (phones.moveToFirst()) {
                    // 遍历所有的电话号码
                    for (; !phones.isAfterLast(); phones.moveToNext()) {
                        int index = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phone_type = phones.getInt(typeindex);
                        String phoneNumber = phones.getString(index);
                        if (phone_type == 2) {
                            phoneResult = phoneNumber;
                        }
                    }
                    if (!phones.isClosed()) {
                        phones.close();
                    }
                }
            }
            // 关闭游标
            mCursor.close();
        }

        return phoneResult;
    }

    /**
     * 跳转至拍照程序界面
     *
     * @param mContext    上下文
     * @param requestCode 请求返回Result区分代码
     */
    public static void toCameraActivity(Activity mContext, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转至相册选择界面
     *
     * @param mContext    上下文
     * @param requestCode
     */
    public static void toImagePickerActivity(Activity mContext, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * 获得选中相册的图片
     *
     * @param mContext 上下文
     * @param data     onActivityResult返回的Intent
     * @return
     */

    @SuppressWarnings({"deprecation", "unused"})
    public static Bitmap getChoosedImage(Activity mContext, Intent data) {
        if (data == null) {
            return null;
        }

        Bitmap bm = null;

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = mContext.getContentResolver();

        // 此处的用于判断接收的Activity是不是你想要的那个
        try {
            Uri originalUri = data.getData(); // 获得图片的uri
            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
            // 这里开始的第二部分，获取图片的路径：
            String[] proj = {MediaStore.Images.Media.DATA};
            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = mContext.managedQuery(originalUri, proj, null,
                    null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            String path = cursor.getString(column_index);
            // 不用了关闭游标
            cursor.close();
        } catch (Exception e) {
            Logger.e("ToolPhone", e.getMessage());
        }

        return bm;
    }

    /**
     * 调用本地浏览器打开一个网页
     *
     * @param mContext   上下文
     * @param strSiteUrl 网页地址
     */
    public static void openWebSite(Context mContext, String strSiteUrl) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strSiteUrl));
        mContext.startActivity(webIntent);
    }

    /**
     * 跳转至系统设置界面
     *
     * @param mContext 上下文
     */
    public static void toSettingActivity(Context mContext) {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        mContext.startActivity(settingsIntent);
    }

    /**
     * 跳转至WIFI设置界面
     *
     * @param mContext 上下文
     */
    public static void toWIFISettingActivity(Context mContext) {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        mContext.startActivity(wifiSettingsIntent);
    }

    /**
     * 启动本地应用打开PDF
     *
     * @param mContext 上下文
     * @param filePath 文件路径
     */
    public static void openPDFFile(Context mContext, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "未检测到可打开PDF相关软件", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 启动本地应用打开PDF
     *
     * @param mContext 上下文
     * @param filePath 文件路径
     */
    public static void openWordFile(Context mContext, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(path, "application/msword");
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "未检测到可打开Word文档相关软件", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 调用WPS打开office文档 http://bbs.wps.cn/thread-22349340-1-1.html
     *
     * @param mContext 上下文
     * @param filePath 文件路径
     */
    public static void openOfficeByWPS(Context mContext, String filePath) {

        try {

            // 文件存在性检查
            File file = new File(filePath);
            if (!file.exists()) {
                Toast.makeText(mContext, filePath + "文件路径不存在",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查是否安装WPS
            String wpsPackageEng = "cn.wps.moffice_eng";// 普通版与英文版一样
            // String wpsActivity =
            // "cn.wps.moffice.documentmanager.PreStartActivity";
            String wpsActivity2 = "cn.wps.moffice.documentmanager.PreStartActivity2";// 默认第三方程序启动

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setClassName(wpsPackageEng, wpsActivity2);

            Uri uri = Uri.fromFile(new File(filePath));
            intent.setData(uri);
            mContext.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "本地未安装WPS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "打开文档失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否安装指定包名的APP
     *
     * @param mContext    上下文
     * @param packageName 包路径
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isInstalledApp(Context mContext, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }

        try {
            ApplicationInfo info = mContext.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断是否存在指定的Activity
     *
     * @param mContext    上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return
     */
    public static boolean isExistActivity(Context mContext, String packageName,
                                          String className) {

        Boolean result = true;
        Intent intent = new Intent();
        intent.setClassName(packageName, className);

        if (mContext.getPackageManager().resolveActivity(intent, 0) == null) {
            result = false;
        } else if (intent.resolveActivity(mContext.getPackageManager()) == null) {
            result = false;
        } else {
            List<ResolveInfo> list = mContext.getPackageManager()
                    .queryIntentActivities(intent, 0);
            if (list.size() == 0) {
                result = false;
            }
        }

        return result;
    }


    /**
     * 获取移动终端类型
     *
     * @param context 上下文
     * @return 手机制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为GSM，移动和联通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为CDMA，电信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }


    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取手机状态信息
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @param context 上下文
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * honeType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 获取手机短信并保存到xml中
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
     *
     * @param context 上下文
     */
    public static void getAllSMS(Context context) {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int DEFAULT_THREAD_POOL_SIZE = getSysDefaultThreadPoolSize();


    /**
     * 获得客户端操作系统名称
     *
     * @return
     */
    public static String getSysClientOs() {
        String OsName = android.os.Build.ID;
        return OsName;
    }

    /**
     * 获取当前操作系统的sdk版本
     *
     * @return String 系统SDK版本
     */
    public static String getSysSdk() {
        String sdkVersion = android.os.Build.VERSION.SDK;
        Logger.i("AppSysMgr-->>getSysLanguage", sdkVersion);
        return sdkVersion;
    }

    /**
     * 获取当前操作系统的语言
     *
     * @return String 系统语言
     */
    public static String getSysLanguage() {
        String language = Locale.getDefault().getLanguage();
        Logger.i("AppSysMgr-->>getSysLanguage", language);
        return language;
    }


    /**
     * 获取手机型号
     *
     * @return String 手机型号
     */
    public static String getSysModel() {
        String model = android.os.Build.MODEL;
        Logger.i("AppSysMgr-->>getSysModel", model);
        return model;
    }


    /**
     * 获取操作系统的版本号
     *
     * @return String 系统版本号
     */
    public static String getSysRelease() {
        String release = android.os.Build.VERSION.RELEASE;
        Logger.i("AppSysMgr-->>getSysRelease", release);
        return release;
    }


    /**
     * 读取SIM卡序列号
     *
     * @param content 上下文
     * @return String SIM卡序列号
     */
    public static String getSysSIMSerialNum(Context content) {
        String simSerialNumber = getSysTelephonyManager(content).getSimSerialNumber();
        Logger.i("AppSysMgr-->>getSysSIMSerialNum", simSerialNumber);
        return simSerialNumber;
    }


    /**
     * 获取手机CPU序列号
     *
     * @return String cpu序列号(16位) 读取失败为"0000000000000000"
     */
    public static String getSysCPUSerialNum() {
        String str = "", strCPU = "", cpuSerialNum = "0000000000000000";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        //提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        //去空格
                        cpuSerialNum = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("AppSysMgr-->>getSysCPUSerialNum", e.getMessage().toString());
        }
        return cpuSerialNum;
    }


    /**
     * 获得电话管理实例对象
     *
     * @param content 上下文
     * @return TelephonyManager 电话管理实例对象
     */
    private static TelephonyManager getSysTelephonyManager(Context content) {
        TelephonyManager telephonyManager = null;
        telephonyManager = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        Logger.i("AppSysMgr-->>getSysTelephonyManager", telephonyManager + "");
        return telephonyManager;
    }


    /**
     * 读唯一的设备ID(唯一的设备ID【GSM手机的IMEI】和【CDMA手机的 MEID】,如果获取不到返回一个默认字符串)
     *
     * @param content 上下文
     * @return String 获取设备序列号
     */
    public static String getSysTelephoneSerialNum(Context content) {
        String deviceId = getSysTelephonyManager(content).getDeviceId();
        Logger.i("AppSysMgr-->>getSysTelephoneSerialNum", deviceId + "");
        return deviceId;
    }


    /**
     * 获取运营商信息(三大运营商)
     *
     * @param content 上下文
     * @return String 获取运营商名称
     */
    public static String getSysCarrier(Context content) {
        String moblieType = "";
        TelephonyManager telephonyManager = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //中国移动
                moblieType = "China Mobile";
            } else if (imsi.startsWith("46001")) {
                //中国联通
                moblieType = "China Unicom";
            } else if (imsi.startsWith("46003")) {
                //中国电信
                moblieType = "China Telecom";
            }
        }
        Logger.i("AppSysMgr-->>getSysCarrier", moblieType);
        return moblieType;
    }


    /**
     * 获取手机状态(0：无活动 1：响铃 2：待机)
     *
     * @param context 上下文
     * @return Integer 手机状态
     */
    public static Integer getSysPhoneState(Context context) {
        Integer callState = getSysTelephonyManager(context).getCallState();
        Logger.i("AppSysMgr-->>getSysPhoneState", callState + "");
        return callState;
    }


    /**
     * 获得手机方位
     *
     * @param context 上下文
     * @return CellLocation 手机方位
     */
    public static CellLocation getSysPhoneLoaction(Context context) {
        CellLocation cellLocation = getSysTelephonyManager(context).getCellLocation();
        Logger.i("AppSysMgr-->>getSysPhoneLoaction", cellLocation + "");
        return cellLocation;
    }


    /**
     * 获得设备的软件版本号(注：the IMEI/SV(software version) for GSM phones 不支持返回“not available”)
     *
     * @param context 上下文
     * @return String 设备软件版本号
     */
    public static String getSysDeviceSoftVersion(Context context) {
        String deviceSoftwareVersion = getSysTelephonyManager(context).getDeviceSoftwareVersion();
        Logger.i("AppSysMgr-->>getSysDeviceSoftVersion", deviceSoftwareVersion + "");
        return deviceSoftwareVersion;
    }


    /**
     * 获得手机号
     *
     * @param context 上下文
     * @return String 手机号
     */
    public static String getSysPhoneNumber(Context context) {
        String phoneNumber = getSysTelephonyManager(context).getLine1Number();
        Logger.i("AppSysMgr-->>getSysPhoneNumber", phoneNumber + "");
        return phoneNumber;
    }


    /**
     * 获得SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字。(注：SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断))
     *
     * @param context 上下文
     * @return String SIM移动国家编码和移动网络编码
     */
    public static String getSysSimCode(Context context) {
        String code = "";
        if (getSysTelephonyManager(context).getSimState() == 5) {
            code = getSysTelephonyManager(context).getSimOperator();
        }
        Logger.i("AppSysMgr-->>getSysSimCode", code + "");
        return code;
    }


    /**
     * 服务商名称(注:例如：中国移动、联通SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断)).
     *
     * @param context 上下文
     * @return String 服务商名称
     */
    public static String getSysSimPrivatorName(Context context) {
        String simOperatorName = "";
        if (getSysTelephonyManager(context).getSimState() == 5) {
            simOperatorName = getSysTelephonyManager(context).getSimOperatorName();
        }
        Logger.i("AppSysMgr-->>getSysSimPrivatorName", simOperatorName);
        return simOperatorName;
    }


    /**
     * 唯一的用户ID (注：例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE)
     *
     * @param context 上下文
     * @return String 获取国际移动用户GSM识别码
     */
    public static String getSysUserPhoneId(Context context) {
        String subscriberId = getSysTelephonyManager(context).getSubscriberId();
        Logger.i("AppSysMgr-->>getSysUserPhoneId", subscriberId);
        return subscriberId;
    }


    /**
     * 获取WindowManager对象
     *
     * @param context 上下文对象
     * @return WindowManager
     */
    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }


    /**
     * 获取屏幕管理类
     *
     * @param activity activity对象
     * @return DisplayMetrics 屏幕管理实例
     */
    public static DisplayMetrics getSysDisplayMetrics(Activity activity) {
        DisplayMetrics displayMetrics = null;
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
        }
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Logger.i("AppSysMgr-->>getSysDisplayMetrics", "获取屏幕管理对象为：" + displayMetrics);
        return displayMetrics;
    }


    /**
     * 获取屏幕宽度和高度并返回数组
     *
     * @param context 上下文对象
     * @return int[] 宽和高
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //手机屏幕的宽度
        int width = wm.getDefaultDisplay().getWidth();
        //手机屏幕的高度
        int height = wm.getDefaultDisplay().getHeight();
        Logger.i("AppSysMgr-->>getScreenDispaly-->>width", "获取屏幕宽度为：" + width);
        Logger.i("AppSysMgr-->>getScreenDispaly-->>height", "获取屏幕高度为：" + height);
        int result[] = {width, height};
        return result;
    }


    /**
     * 获取屏幕宽度和高度并返回数组
     *
     * @param context 上下文对象
     * @return int[] 宽和高
     */
    public static int[] getScreenDispaly8(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //手机屏幕的宽度
        int width = wm.getDefaultDisplay().getWidth() / 10 * 8;
        //手机屏幕的高度
        int height = wm.getDefaultDisplay().getHeight() / 10 * 8;
        Logger.i("AppSysMgr-->>getScreenDispaly-->>width", "获取屏幕宽度为：" + width);
        Logger.i("AppSysMgr-->>getScreenDispaly-->>height", "获取屏幕高度为：" + height);
        int result[] = {width, height};
        return result;
    }


    /**
     * 获取屏幕宽度
     *
     * @param context 上下文对象
     * @return Integer 屏幕宽度
     */
    public static Integer getSysScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        Logger.i("AppSysMgr-->>getSysScreenWidth", "获取屏幕宽度为：" + displayMetrics.widthPixels);
        return displayMetrics.widthPixels;
    }


    /**
     * 获取屏幕高度
     *
     * @param context 上下文对象
     * @return Integer 屏幕高度
     */
    public static Integer getSysScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        Logger.i("AppSysMgr-->>getSysScreenHeight", "获取屏幕高度为：" + displayMetrics.heightPixels);
        return displayMetrics.heightPixels;
    }


    /**
     * 获取屏幕状态栏目高度
     *
     * @param context 上下文对象
     * @return Integer 状态栏高度
     */
    public static Integer getSysScreenStatusHeight(Context context) {
        int statusHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
            Logger.i("AppSysMgr-->>getSysScreenStatusHeight", "获取屏幕状态栏高度为：" + statusHeight);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("AppSysMgr-->>getSysScreenStatusHeight", "获取屏幕状态栏高度失败！" + e.getMessage());
        }
        return statusHeight;
    }


    /**
     * 获得系统配置相符的线程池大小
     *
     * @return Integer 返回系统配置相符合线程大小
     */
    public static Integer getSysDefaultThreadPoolSize() {
        Integer availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        availableProcessors = availableProcessors > 8 ? 8 : availableProcessors;
        Logger.i("AppSysMgr-->>getSysDefaultThreadPoolSize", availableProcessors + "");
        return availableProcessors;
    }


    /**
     * 获取当前APP应用的SampleSize大小
     *
     * @param options        BitmapFactory.Options对象
     * @param minSideLength  计算最小值
     * @param maxNumOfPixels 计算最大值
     * @return Integer  返回SampleSize大小
     */
    public static Integer getSysSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        Integer initialSize = calculateSysInitialSampleSize(options, minSideLength, maxNumOfPixels);
        Integer roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        Logger.i("AppSysMgr-->>getSysSampleSize", roundedSize + "");
        return roundedSize;
    }


    /**
     * 计算公式
     *
     * @param options        BitmapFactory.Options对象
     * @param minSideLength  计算最小值
     * @param maxNumOfPixels 计算最大值
     * @return Integer
     */
    private static Integer calculateSysInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        Integer lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        Integer upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * 获取震动器对象
     *
     * @param context 上下文对象
     * @return Vibrator 震动器对象
     */
    public static Vibrator getVibrator(Context context) {
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }


    /**
     * 获取手机IP地址
     *
     * @return String 手机IP地址
     */
    public String getSysLocalIpAddress() {
        String hostAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        hostAddress = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            Logger.e("AppSysMgr-->>getSysLocalIpAddress", e.getMessage().toString());
        }
        Logger.i("AppSysMgr-->>getSysLocalIpAddress", hostAddress);
        return hostAddress;
    }

    /**
     * 获取AndroidID
     *
     * @param ctx
     * @return
     */
    public static String getAndroidID(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备IMSI码
     *
     * @param ctx
     * @return
     */
    public static String getIMSI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId() != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取网络IP地址(优先获取wifi地址)
     *
     * @param ctx
     * @return
     */
    public static String getIP(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled() ? getWifiIP(wifiManager) : getGPRSIP();
    }

    /**
     * 获取WIFI连接下的ip地址
     *
     * @param wifiManager
     * @return
     */
    public static String getWifiIP(WifiManager wifiManager) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ip = intToIp(wifiInfo.getIpAddress());
        return ip != null ? ip : "";
    }

    /**
     * 获取GPRS连接下的ip地址
     *
     * @return
     */
    public static String getGPRSIP() {
        String ip = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                for (Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip = null;
        }
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取设备序列号
     *
     * @return
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * 获取SIM序列号
     *
     * @param ctx
     * @return
     */
    public static String getSIMSerial(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取网络运营商 46000,46002,46007 中国移动,46001 中国联通,46003 中国电信
     *
     * @param ctx
     * @return
     */
    public static String getMNC(Context ctx) {
        String providersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            providersName = telephonyManager.getSimOperator();
            providersName = providersName == null ? "" : providersName;
        }
        return providersName;
    }

    /**
     * 获取网络运营商：中国电信,中国移动,中国联通
     *
     * @param ctx
     * @return
     */
    public static String getCarrier(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName().toLowerCase(Locale.getDefault());
    }

    /**
     * 获取硬件型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取编译厂商
     *
     * @return
     */
    public static String getBuildBrand() {
        return Build.BRAND;
    }

    /**
     * 获取编译服务器主机
     *
     * @return
     */
    public static String getBuildHost() {
        return Build.HOST;
    }

    /**
     * 获取描述Build的标签
     *
     * @return
     */
    public static String getBuildTags() {
        return Build.TAGS;
    }

    /**
     * 获取系统编译时间
     *
     * @return
     */
    public static long getBuildTime() {
        return Build.TIME;
    }

    /**
     * 获取系统编译作者
     *
     * @return
     */
    public static String getBuildUser() {
        return Build.USER;
    }

    /**
     * 获取编译系统版本(5.1)
     *
     * @return
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取开发代号
     *
     * @return
     */
    public static String getBuildVersionCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取源码控制版本号
     *
     * @return
     */
    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * 获取编译的SDK
     *
     * @return
     */
    public static int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取修订版本列表(LMY47D)
     *
     * @return
     */
    public static String getBuildID() {
        return Build.ID;
    }

    /**
     * CPU指令集
     *
     * @return
     */
    public static String[] getSupportedABIS() {
        String[] result = new String[]{"-"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            result = Build.SUPPORTED_ABIS;
        }
        if (result == null || result.length == 0) {
            result = new String[]{"-"};
        }
        return result;
    }

    /**
     * 获取硬件制造厂商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取系统启动程序版本号
     *
     * @return
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    /**
     * @param ctx
     * @return
     */
    public static String getScreenDisplayID(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return String.valueOf(wm.getDefaultDisplay().getDisplayId());
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getDisplayVersion() {
        return Build.DISPLAY;
    }

    /**
     * 获取语言
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取国家
     *
     * @param ctx
     * @return
     */
    public static String getCountry(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        Locale locale = Locale.getDefault();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY ? tm.getSimCountryIso().toLowerCase(Locale.getDefault()) : locale.getCountry().toLowerCase(locale);
    }

    /**
     * 获取系统版本:5.1.1
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取GSF序列号
     *
     * @param context
     * @return
     */
    //<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
    public static String getGSFID(Context context) {
        String result;
        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";
        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (c == null || !c.moveToFirst() || c.getColumnCount() < 2) {
            return null;
        } else {
            result = Long.toHexString(Long.parseLong(c.getString(1)));
        }
        c.close();
        return result;
    }

    /**
     * 获取蓝牙地址
     *
     * @param context
     * @return
     */
    //<uses-permission android:name="android.permission.BLUETOOTH"/>
    @SuppressWarnings("MissingPermission")
    public static String getBluetoothMAC(Context context) {
        String result = null;
        try {
            if (context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH)
                    == PackageManager.PERMISSION_GRANTED) {
                BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
                result = bta.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Android设备物理唯一标识符
     *
     * @return
     */
    public static String getPsuedoUniqueID() {
        String devIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            devIDShort += (Build.SUPPORTED_ABIS[0].length() % 10);
        } else {
            devIDShort += (Build.CPU_ABI.length() % 10);
        }
        devIDShort += (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            serial = "ESYDV000";
        }
        return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 构建标识,包括brand,name,device,version.release,id,version.incremental,type,tags这些信息
     *
     * @return
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取硬件信息
     *
     * @return
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取产品信息
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取主板信息
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取基带版本(无线电固件版本 Api14以上)
     *
     * @return
     */
    public static String getRadioVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ? Build.getRadioVersion() : "";
    }

    /**
     * 获取的浏览器指纹(User-Agent)
     *
     * @param ctx
     * @return
     */
    public static String getUA(Context ctx) {
        final String system_ua = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return new WebView(ctx).getSettings().getDefaultUserAgent(ctx) + "__" + system_ua;
        } else {
            return new WebView(ctx).getSettings().getUserAgentString() + "__" + system_ua;
        }
    }

    /**
     * 获取得屏幕密度
     *
     * @param ctx
     * @return
     */
    public static String getDensity(Context ctx) {
        String densityStr = null;
        final int density = ctx.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_400:
                densityStr = "XMHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI";
                break;
        }
        return densityStr;
    }

    /**
     * 获取google账号
     *
     * @param ctx
     * @return
     */
    //<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    @SuppressWarnings("MissingPermission")
    public static String[] getGoogleAccounts(Context ctx) {
        if (ctx.checkCallingOrSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(ctx).getAccountsByType("com.google");
            String[] result = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                result[i] = accounts[i].name;
            }
            return result;
        }
        return null;
    }

}
