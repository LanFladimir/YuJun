package com.fladimir.jutils.ui.toast;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fladimir.jutils.R;


/**
 * Created by NingJiang on 2017/6/2.
 * Class Note:
 */

public class Toasty {
    protected static Toast toast = null;
    protected static Toast Themetoast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static String oldMsg;
    private static long ThemeoneTime = 0;
    private static long ThemetwoTime = 0;
    private static String ThemeoldMsg;

    /**
     * 可快速显示，可覆盖的Toast工具
     */
    public static void nomal(Context context, String text) {
        // 用于判断是否已有Toast执行
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text,
                    Toast.LENGTH_SHORT);
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > 1000) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                toast.setText(text);
                toast.show();
            }
        }
        toast.show();
    }

    /*public static void nomal(Fragment context, String text) {
        // 用于判断是否已有Toast执行
        if (toast == null) {
            toast = Toast.makeText(context.getActivity(), text,
                    Toast.LENGTH_SHORT);
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > 1000) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                toast.setText(text);
                toast.show();
            }
        }
        toast.show();
    }*/

    /**
     * 背景色、圆角、icon
     *
     * @param context
     * @param toastInfo
     * @param ico       0
     * @param bg        0
     */
    public static void withTheme(Context context, String toastInfo, int ico, int bg) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_theme_success, null);
        TextView toast_themeS_info = (TextView) view.findViewById(R.id.toast_themeS_info);
        CardView toast_themeS_card = (CardView) view.findViewById(R.id.toast_themeS_card);
        ImageView toast_themeS_ico = (ImageView) view.findViewById(R.id.toast_themeS_ico);
        if (ico != 0) {
            toast_themeS_ico.setVisibility(View.VISIBLE);
            toast_themeS_ico.setImageResource(ico);
        }
        if (bg == 0)
            toast_themeS_card.setCardBackgroundColor(bg);
        else toast_themeS_card.setCardBackgroundColor(Color.GRAY);

        toast_themeS_info.setText(toastInfo);

        if (Themetoast == null) {
            Themetoast = new Toast(context);
            Themetoast.setView(view);
            Themetoast.setDuration(Toast.LENGTH_SHORT);
            Themetoast.show();
            ThemeoneTime = System.currentTimeMillis();
        } else {
            Themetoast.setView(view);
            Themetoast.setDuration(Toast.LENGTH_SHORT);

            ThemetwoTime = System.currentTimeMillis();
            if (toastInfo.equals(ThemeoldMsg)) {
                if (ThemetwoTime - ThemeoneTime > 1000) {
                    Themetoast.show();
                }
            } else {
                ThemeoldMsg = toastInfo;
                toast_themeS_info.setText(toastInfo);
                Themetoast.show();
            }
        }
    }

    /**
     * @param context
     * @param toastInfo
     * @param ico
     * @param bg
     * @param gravity
     */
    public static void withTheme(Context context, String toastInfo, int ico, int bg, int gravity) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_theme_success, null);
        TextView toast_themeS_info = (TextView) view.findViewById(R.id.toast_themeS_info);
        CardView toast_themeS_card = (CardView) view.findViewById(R.id.toast_themeS_card);
        ImageView toast_themeS_ico = (ImageView) view.findViewById(R.id.toast_themeS_ico);
        if (ico != 0) {
            toast_themeS_ico.setVisibility(View.VISIBLE);
            toast_themeS_ico.setImageResource(ico);
        }
        if (bg == 0)
            toast_themeS_card.setCardBackgroundColor(bg);
        else toast_themeS_card.setCardBackgroundColor(Color.GRAY);

        toast_themeS_info.setText(toastInfo);

        if (Themetoast == null) {
            Themetoast = new Toast(context);
            Themetoast.setView(view);
            Themetoast.setGravity(gravity, 0, 0);
            Themetoast.setDuration(Toast.LENGTH_SHORT);
            Themetoast.show();
            ThemeoneTime = System.currentTimeMillis();
        } else {
            Themetoast.setView(view);
            Themetoast.setGravity(gravity, 0, 0);
            Themetoast.setDuration(Toast.LENGTH_SHORT);

            ThemetwoTime = System.currentTimeMillis();
            if (toastInfo.equals(ThemeoldMsg)) {
                if (ThemetwoTime - ThemeoneTime > 1000) {
                    Themetoast.show();
                }
            } else {
                ThemeoldMsg = toastInfo;
                toast_themeS_info.setText(toastInfo);
                Themetoast.show();
            }
        }
    }

    /*public static void withTheme(Fragment context, String toastInfo, int ico, int bg) {
        View view = LayoutInflater.from(context.getActivity()).inflate(R.layout.toast_theme_success, null);
        TextView toast_themeS_info = (TextView) view.findViewById(R.id.toast_themeS_info);
        LinearLayout toast_themeS_bg = (LinearLayout) view.findViewById(R.id.toast_themeS_bg);
        ImageView toast_themeS_ico = (ImageView) view.findViewById(R.id.toast_themeS_ico);
        toast_themeS_ico.setImageResource(ico);
        toast_themeS_bg.setBackgroundResource(bg);
        toast_themeS_info.setText(toastInfo);

        if (Themetoast == null) {
            Themetoast = new Toast(context.getActivity());
            ThemeoneTime = System.currentTimeMillis();
        } else {
            Themetoast.setView(view);
            Themetoast.setDuration(Toast.LENGTH_SHORT);
            Themetoast.setGravity(Gravity.CENTER, 0, -60);

            ThemetwoTime = System.currentTimeMillis();
            if (toastInfo.equals(ThemeoldMsg)) {
                if (ThemetwoTime - ThemeoneTime > 1000) {
                    Themetoast.show();
                }
            } else {
                ThemeoldMsg = toastInfo;
                toast_themeS_info.setText(toastInfo);
                Themetoast.show();
            }
        }
    }*/
}