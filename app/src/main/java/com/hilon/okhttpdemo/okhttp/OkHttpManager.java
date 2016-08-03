package com.hilon.okhttpdemo.okhttp;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
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

    public void get(String url, ResultCallBack callback, String tag) {
        executeRequest(url, Method.GET, null, null, callback, tag);
    }


    private void executeRequest(String url, Method method, Map<String, String> requestParams, RequestBody requestBody, ResultCallBack callback, String tag) {
        if (!TextUtils.isEmpty(url)) {
            RequestTask requestTask = new RequestTask(url, method, requestParams, requestBody, callback, tag);
            requestTask.execute();
        }
    }

    public void cancel(String tag) {
        cancelRequest(mOkHttpClient, tag);
    }

    private void cancelRequest(OkHttpClient client, Object tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) call.cancel();
        }
    }


}
