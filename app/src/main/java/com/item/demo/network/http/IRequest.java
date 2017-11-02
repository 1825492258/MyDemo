package com.item.demo.network.http;

import java.util.Map;

/**
 * Created by wuzongjie on 2017/10/28.
 * 定义请求数据的封装方式
 */

public interface IRequest {
    /**
     * 指定请求方式
     */
    void setMethod(String method);

    /**
     * 指定请求头部
     */
    void setHeader(String key, String value);

    /**
     * 指定请求参数
     *
     * @param key
     * @param value
     */
    void setBody(String key, Object value);

    /**
     * 提供给执行库请求行URL
     */
    String getUrl();

    /**
     * 提供给执行库请求头部
     */
    Map<String, String> getHeader();

    /**
     * 提供给执行库请求参数
     */
    Object getBody();
}
