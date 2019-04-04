package com.hexg.okhttpdemo.http.listener;

import com.hexg.okhttpdemo.http.listener.CallbackListener;

public interface IHttpRequuest {
    void setUrl(String url);
    void setData(byte[] data);
    void setListener(CallbackListener listener);
    void execute();
}
