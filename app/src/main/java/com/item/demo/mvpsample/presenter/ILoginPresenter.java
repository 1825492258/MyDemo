package com.item.demo.mvpsample.presenter;

/**
 * Created by wuzongjie on 2017/11/9.
 * 登录的Presenter
 */

public interface ILoginPresenter {
    /**
     * 获取验证码
     * @param code
     */
    void getCode(String code);

    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     */
    void requestLogin(String phone,String code);
}
