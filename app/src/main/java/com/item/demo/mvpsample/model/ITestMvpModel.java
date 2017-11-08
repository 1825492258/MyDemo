package com.item.demo.mvpsample.model;

import com.item.demo.mvpsample.model.bean.BaseModelCallBack;

/**
 * Created by wuzongjie on 2017/11/8.
 */

public interface ITestMvpModel {
    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     */
    void login(String phone, String code, BaseModelCallBack baseModelCallBack);
}
