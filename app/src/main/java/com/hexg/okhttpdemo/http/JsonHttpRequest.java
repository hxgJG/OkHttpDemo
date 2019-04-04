package com.hexg.okhttpdemo.http;

import com.hexg.okhttpdemo.http.listener.CallbackListener;
import com.hexg.okhttpdemo.http.listener.ErrorCode;
import com.hexg.okhttpdemo.http.listener.IHttpRequuest;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequuest {

    private String mUrl;
    private byte[] mData;
    private CallbackListener mListener;
    private HttpURLConnection httpConn;

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public void setData(byte[] data) {
        mData = data;
    }

    @Override
    public void setListener(CallbackListener listener) {
        mListener = listener;
    }

    @Override
    public void execute() {
        try {
            URL url = new URL(mUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(3000);
            httpConn.setUseCaches(true);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Connect-Type", "application/json;charset=UTF-8");
            httpConn.connect();

            OutputStream os = httpConn.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(mData);
            bos.flush();
            os.close();
            bos.close();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream ins = httpConn.getInputStream();
                mListener.onSuccess(ins);
            } else {
                throw new RuntimeException("request error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("request error");
        } finally {
            httpConn.disconnect();
        }
    }
}
