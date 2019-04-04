package com.hexg.okhttpdemo.http;

import com.hexg.okhttpdemo.http.bean.HttpTask;
import com.hexg.okhttpdemo.http.listener.CallbackListener;
import com.hexg.okhttpdemo.http.listener.IHttpRequuest;
import com.hexg.okhttpdemo.http.listener.IJsonDataListener;
import com.hexg.okhttpdemo.http.manager.ThreadPoolManager;

public class XOkHttp {
    public static<T, M> void requestForJson(T requestData, String url, Class<M> responseClass, IJsonDataListener<M> listener) {
        IHttpRequuest requuest = new JsonHttpRequest();
        CallbackListener callback = new JsonCallbackListener<>(responseClass, listener);
        HttpTask task = new HttpTask(url, requestData, requuest, callback);
        ThreadPoolManager.getInstance().addTask(task);
    }
}
