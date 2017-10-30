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
    // IResponse get(IRequest request, boolean forceCache);

    /**
     * 对外提供POST请求方式
     *
     * @param request    请求体
     * @param forceCache 是否缓存
     * @return IResponse（状态码及数据体）
     */
    // IResponse post(IRequest request, boolean forceCache);

    /**
     * 对外提供GET请求方式
     *
     * @param request    请求体
     * @param forceCache 是否缓存
     * @param callBack   回调函数
     */
    void get(IRequest request, boolean forceCache, RequestCallBack callBack);

    /**
     * 对外提供POST请求方式
     *
     * @param request    请求体
     * @param forceCache 是否缓存
     * @param callBack   回调函数
     */
    void post(IRequest request, boolean forceCache, RequestCallBack callBack);

    /**
     * 网络请求的回调
     */
    interface RequestCallBack {

        void onSuccess(BaseResponse response); // 成功返回状态码及数据体

        void onFailure(int code); // 失败这里只返回状态码
    }
}
