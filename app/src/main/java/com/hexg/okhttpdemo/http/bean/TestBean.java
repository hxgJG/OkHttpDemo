package com.hexg.okhttpdemo.http.bean;

public class TestBean {
    private String resultcode;
    private String reason;
    private String error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", error_code='" + error_code + '\'' +
                '}';
    }
}
