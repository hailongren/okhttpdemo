package com.hilon.okhttpdemo.okhttp;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Henry.Ren on 16/8/3.
 */
public final class OkHttpManager {
    // 单例
    private static OkHttpManager sInstance;

    private OkHttpClient mOkHttpClient;


    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

    }

    public static OkHttpManager getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpManager.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpManager();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void get(String url, ResultCallBack callback) {
        executeRequest(url, Method.GET, null, null, callback);
    }


    private void executeRequest(String url, Method method, Map<String, String> requestParams, RequestBody requestBody, ResultCallBack callback) {
        if (!TextUtils.isEmpty(url)) {
            RequestTask requestTask = new RequestTask(url, method, requestParams, requestBody, callback);
            requestTask.execute();
        }
    }


}
