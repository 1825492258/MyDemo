package com.item.demo.mvpsample.view;

/**
 * Created by wuzongjie on 2017/11/9.
 */

public interface ILoginView extends IView{
    void getCode(String code);
    void getErrorToast(String message);
    void getLogin();
}
