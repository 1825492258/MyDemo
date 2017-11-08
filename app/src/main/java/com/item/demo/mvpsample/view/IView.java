package com.item.demo.mvpsample.view;

/**
 * Created by wuzongjie on 2017/11/8.
 */

public interface IView {
    /**
     * 显示Loading
     */
    void showLoading();

    /**
     * 取消Loading
     */
    void dissimssLoading();

    /**
     * 显示错误
     * @param code 错误码
     */
    void showError(int code);
}
