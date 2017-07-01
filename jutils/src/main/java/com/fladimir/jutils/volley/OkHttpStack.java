package com.fladimir.jutils.volley;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * Created by NingJiang on 2017/6/16.
 * Class Note:
 */

public class OkHttpStack extends HurlStack {

    private final OkUrlFactory mFactory;

    /**
     * Create a OkHttpStack with default OkHttpClient.
     */
    public OkHttpStack() {
        this(new OkHttpClient());
    }

    /**
     * Create a OkHttpStack with a custom OkHttpClient
     *
     * @param okHttpClient Custom OkHttpClient, NonNull
     */
    public OkHttpStack(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new NullPointerException("Client must not be null.");
        }
        mFactory = new OkUrlFactory(okHttpClient);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        //OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return mFactory.open(url);
    }
}