package com.hilon.okhttpdemo.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Henry.Ren on 16/8/3.
 */
public class RequestTask implements Callback {
    private static final String TAG = "RequestTask";
    private Handler mDelivery = new Handler(Looper.getMainLooper());
    private Gson mGson = new GsonBuilder().create();

    private String url;
    private Method method;
    private Map<String, String> requestParams;
    private RequestBody requestBody;
    private ResultCallBack callBack;
    private Headers headers;

    public RequestTask(String url, Method method, Map<String, String> requestParams, RequestBody requestBody, ResultCallBack callback) {
        this.url = url;
        this.method = method;
        this.requestParams = requestParams;
        this.requestBody = requestBody;
        this.callBack = callback;
    }

    private RequestTask() {

    }

    protected void execute() {
        if (callBack != null) {
            callBack.onBefore();
        }
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    protected void run() throws Exception {
        Request.Builder builder = new Request.Builder();

        switch (method) {
            case GET:
                if (requestParams != null) {
                    url = makeUrlParams(url, requestParams);
                }
                builder.get();
                break;

            case POST:
                if (requestBody != null) {
                    builder.post(requestBody);
                }
                break;

        }
        builder.url(url);
        Request request = builder.build();
        Call call = OkHttpManager.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        ResponseData responseData = new ResponseData();
        if (e instanceof SocketTimeoutException) {
            responseData.setTimeout(true);
        }
        handlerResponse(responseData, null);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        ResponseData responseData = new ResponseData();
        handlerResponse(responseData, response);
    }

    private void handlerResponse(final ResponseData responseData, Response response) {
        if (response != null) {
            responseData.setResponseNull(false);
            responseData.setCode(response.code());
            responseData.setMessage(response.message());
            responseData.setSuccess(response.isSuccessful());
            String responseBody = "";
            try {
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            responseData.setResponse(responseBody);
            responseData.setHeaders(response.headers());
        } else {
            responseData.setResponseNull(true);
            responseData.setCode(ResultCallBack.ERROR_RESPONSE_UNKNOWN);
            if (responseData.isTimeout()) {
                responseData.setMessage("request timeout");
            } else {
                responseData.setMessage("http exception");
            }
        }
        responseData.setHttpResponse(response);

        // 主线程处理
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(responseData);
            }
        });
    }

    protected void onPostExecute(ResponseData responseData) {
        int code = responseData.getCode();
        String msg = responseData.getMessage();
        if (!responseData.isResponseNull()) {
            // 有响应
            if (responseData.isSuccess()) {
                //请求成功
                parseResponseBody(responseData, callBack);
            } else {
                // 请求失败
                if (callBack != null) {
                    callBack.onError(code, msg);
                }
            }
        } else {
            if (callBack != null) {
                callBack.onError(code, msg);
            }
        }

        if (callBack != null) {
            callBack.onAfter();
        }
    }

    /**
     * 解析响应数据
     *
     * @param responseData 请求的response
     * @param callback     请求回调
     */
    private void parseResponseBody(ResponseData responseData, ResultCallBack callback) {
        if (callback == null) {
            return;
        }
        String result = responseData.getResponse();

        if (TextUtils.isEmpty(result)) {
            Log.e(TAG, "response is empty!!");
        }

        if (callback.mModelClassType == String.class) {
            callback.onSuccess(result);
            return;
        } else {
            Object obj = null;
            try {
                obj = mGson.fromJson(result, callback.mModelClassType);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            if (obj != null) {
                callback.onSuccess(obj);
                return;
            }
        }
        callback.onError(ResultCallBack.ERROR_RESPONSE_DATA_PARSE_EXCEPTION, "Data parse exception");
    }


    public static String makeUrlParams(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(key).append("=").append(value);
        }
        if (url.endsWith("?")) {
            return url + stringBuilder.toString();
        } else {
            return url + "?" + stringBuilder.toString();
        }
    }
}
