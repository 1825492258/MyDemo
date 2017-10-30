package com.item.demo.network.http;

import com.item.demo.network.http.impl.BaseResponse;

/**
 * Created by wuzongjie on 2017/10/28.
 * HttpClient 对外抽象的接口
 */

public interface IHttpClient {
    /**
     * 对外提供GET请求方式
     *
     * @param request    请求体
     * @param forceCache 是否缓存
     * @return IResponse（状态码及数据体）
     */
    IResponse get(IRequest request, boolean forceCache);

    /**
     * 对外提供POST请求方式
     *
     * @param request    请求体
     * @param forceCache 是否缓存
     * @return IResponse（状态码及数据体）
     */
    IResponse post(IRequest request, boolean forceCache);

    void myGet(IRequest request,boolean forceCache,MyCallBack callBack);

    interface MyCallBack{
        void onMyCallBack(BaseResponse response);
        void onMyFial(int code);
    }
}
