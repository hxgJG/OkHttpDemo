package com.hexg.okhttpdemo;

import android.os.Bundle;
import android.util.Log;

import com.hexg.okhttpdemo.http.XOkHttp;
import com.hexg.okhttpdemo.http.bean.TestBean;
import com.hexg.okhttpdemo.http.listener.ErrorCode;
import com.hexg.okhttpdemo.http.listener.IJsonDataListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    private static final String JSON_TEST_URL = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb52107206585ab074f5e59a8c73875b";
    private static final String JSON_TEST_URL = "http://v.juhe.cn/historyy=bb52107206585ab074f5e59a8c73=====";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doRequest();
    }

    private void doRequest() {
        XOkHttp.requestForJson(null, JSON_TEST_URL, TestBean.class, new IJsonDataListener<TestBean>() {
            @Override
            public void onResponse(TestBean test) {
                Log.i("hxg", "result = " + test);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                Log.e("hxg", "request error --> " + errorCode);
            }
        });
    }
}
