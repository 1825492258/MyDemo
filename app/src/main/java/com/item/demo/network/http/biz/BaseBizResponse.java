package com.item.demo.network.http.biz;

/**
 * Created by wuzongjie on 2017/10/28.
 * 返回数据的公共格式
 */

public class BaseBizResponse<T> {

    private String status; // 返回SUCCESS  ERROR
    private String error; // 返回msg

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
