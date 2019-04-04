package com.hexg.okhttpdemo.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.hexg.okhttpdemo.http.listener.CallbackListener;
import com.hexg.okhttpdemo.http.listener.ErrorCode;
import com.hexg.okhttpdemo.http.listener.IJsonDataListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClass;
    private IJsonDataListener<T> responseListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> responseClass, IJsonDataListener<T> listener) {
        this.responseClass = responseClass;
        responseListener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        // inputStream -> responseClass
        String response = getContent(inputStream);
        if (response != null) {

            final T t = JSON.parseObject(response, responseClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    responseListener.onResponse(t);
                }
            });
        }
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sBuilder.append(line).append("\n");
                }
            } finally {
                inputStream.close();
                reader.close();
            }
            content = sBuilder.toString();
        } catch (Exception e) {
            responseListener.onFailure(ErrorCode.IO_ERROR);
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onFailure(ErrorCode errorCode) {
        responseListener.onFailure(errorCode);
    }
}
