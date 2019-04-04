package com.hexg.okhttpdemo.http.bean;

import com.alibaba.fastjson.JSON;
import com.hexg.okhttpdemo.http.listener.CallbackListener;
import com.hexg.okhttpdemo.http.listener.IHttpRequuest;
import com.hexg.okhttpdemo.http.manager.ThreadPoolManager;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequuest mHttpRequest;
    private long delayTime;
    private int retryCount;

    public HttpTask(String url, T requestData, IHttpRequuest httpRequest, CallbackListener listener) {
        mHttpRequest = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.setListener(listener);
        String content = JSON.toJSONString(requestData);
        httpRequest.setData(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        if (mHttpRequest != null) {
            try {
                mHttpRequest.execute();
            } catch (Exception e) {
                e.printStackTrace();
                ThreadPoolManager.getInstance().addDelayTask(this);
            }
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime + System.currentTimeMillis();
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }
}
