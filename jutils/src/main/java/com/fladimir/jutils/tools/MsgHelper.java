package com.fladimir.jutils.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

/**
 * @author run h
 */
public class MsgHelper {

    /**
     * RH 支持传字符串
     *
     * @param mHandler
     * @param putK     键
     * @param putV     值
     * @param message  标记
     */
    public static void sendMessage(Handler mHandler, String putK, String putV,
                                   int message) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(putK, putV);
        msg.what = message;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public static void sendMessage(Handler mHandler, String putK, int putV,
                                   int message) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt(putK, putV);
        msg.what = message;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * @param mHandler
     * @param putK
     * @param list
     * @param message
     */
    @SuppressWarnings("unchecked")
    public static <T> void sendMessage(Handler mHandler, String putK,
                                       ArrayList<T> list, int message) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(putK,
                (ArrayList<? extends Parcelable>) list);
        msg.what = message;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * 显示提示框
     *
     * @param context     上下文
     * @param title       标题
     * @param text        内容
     * @param no          取消按钮
     * @param yes         确定按钮
     * @param listenerYes
     * @param listenerNo
     */
    public static void showQuestionDialog(Context context, String title,
                                          String text, String no, String yes,
                                          final OnClickNoListener listenerNo,
                                          final OnClickYesListener listenerYes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listenerNo != null) {
                    listenerNo.onClickNo();
                }
            }
        });
        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listenerYes != null) {
                    listenerYes.onClickYes();
                }
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }


    /**
     * Listener
     */
    interface OnClickYesListener {
        void onClickYes();
    }

    /**
     * Listener
     */
    interface OnClickNoListener {
        void onClickNo();
    }


}
