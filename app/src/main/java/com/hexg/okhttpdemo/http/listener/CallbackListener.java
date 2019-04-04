package com.hexg.okhttpdemo.http.listener;

import java.io.InputStream;

public interface CallbackListener {
    void onSuccess(InputStream inputStream);

    void onFailure(ErrorCode errorCode);
}
