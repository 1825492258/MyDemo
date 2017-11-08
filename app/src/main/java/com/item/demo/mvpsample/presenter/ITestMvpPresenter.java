package com.item.demo.mvpsample.presenter;



/**
 * Created by wuzongjie on 2017/11/8.
 * Presenter 是个大忙人，因为要同时对View和Model对接，所以
 * 内部必须持有他们的接口的引用
 */

public interface ITestMvpPresenter {
    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     */
    void requestLogin(String phone,String code);
}
