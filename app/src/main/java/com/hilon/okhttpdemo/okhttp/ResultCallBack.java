package com.hilon.okhttpdemo.okhttp;

import java.lang.reflect.Type;

/**
 * Created by Henry.Ren on 16/8/3.
 */
public class ResultCallBack<T> {

    public static final int ERROR_RESPONSE_UNKNOWN = 1001;
    public static final int ERROR_RESPONSE_DATA_PARSE_EXCEPTION = 1002;

    protected Type mModelClassType;

    public ResultCallBack() {
        mModelClassType = ClassTypeReflect.getModelClazz(getClass());
    }

    public void onSuccess(T response) {

    }

    public void onError(int errCode, String errMassage) {

    }

    public void onBefore() {

    }

    public void onAfter() {

    }
}
