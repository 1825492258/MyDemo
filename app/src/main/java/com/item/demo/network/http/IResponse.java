package com.item.demo.network.http;

/**
 * Created by wuzongjie on 2017/10/28.
 * 接口返回
 */

public interface IResponse {
    // 状态码
    int getCode();

    // 数据体
    String getData();
}
