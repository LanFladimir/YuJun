package com.fladimir.jutils.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by NingJiang on 2017/6/16.
 * Class Note:
 */

public class VolleyHelper {
    /**
     * 用于发送 Get 请求的封装方法
     *
     * @param context  Activity 的实例
     * @param url      请求的地址
     * @param callback 用于网络回调的接口
     */
    public static void sendHttpGet(Context context, String url, final VolleyResponseCallback callback) {
        //使用Okhttp做底层
        RequestQueue requestQueue = Volley.newRequestQueue(context, new OkHttpStack());

        StringRequest stringRequest = new StringRequest(url
                , response -> {
            callback.onSuccess(response);
        }, error -> {
            callback.onError(error);
        });
        requestQueue.add(stringRequest);
    }
}
