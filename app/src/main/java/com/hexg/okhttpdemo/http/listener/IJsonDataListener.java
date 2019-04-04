package com.hexg.okhttpdemo.http.listener;

public interface IJsonDataListener<T> {
    void onResponse(T t);

    void onFailure(ErrorCode errorCode);
}
