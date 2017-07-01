package com.fladimir.jutils.volley;

import com.android.volley.VolleyError;

/**
 * Created by NingJiang on 2017/6/16.
 * Class Note:
 */

public interface VolleyResponseCallback {
    void onSuccess(String response);
    void onError(VolleyError error);
}
