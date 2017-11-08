package com.item.demo.mvpsample.view;

/**
 * Created by wuzongjie on 2017/11/8.
 * View层的接口定义
 */

public interface ITestMvpView extends IView{
    /**
     * 显示成功获取的数据
     * @param info
     */
    void onInfoUpdate(String info);
}
