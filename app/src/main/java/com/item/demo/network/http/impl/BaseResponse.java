package com.item.demo.network.http.impl;

import com.item.demo.network.http.IResponse;

/**
 * Created by wuzongjie on 2017/10/28.
 * 请求返回的数据
 */

public class BaseResponse implements IResponse {
    private int code;
    private String data;

    public BaseResponse(int code, String data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }
}
