package com.item.demo.mvpsample.model.bean;

import com.item.demo.network.http.impl.BaseResponse;

/**
 * Created by wuzongjie on 2017/11/8.
 * model 层逻辑结束的回调
 */

public interface BaseModelCallBack {
    void onResponse(BaseResponse response);

    void onFailure(int code); // 失败这里只返回状态码
}
