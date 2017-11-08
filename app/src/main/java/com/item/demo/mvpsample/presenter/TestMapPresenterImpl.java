package com.item.demo.mvpsample.presenter;

import android.util.Log;

import com.item.demo.mvpsample.model.ITestMvpImpl;
import com.item.demo.mvpsample.model.ITestMvpModel;
import com.item.demo.mvpsample.model.bean.BaseModelCallBack;
import com.item.demo.mvpsample.view.ITestMvpView;
import com.item.demo.network.http.impl.BaseResponse;

/**
 * Created by wuzongjie on 2017/11/8.
 * 1.完成presenter的实现 这里面主要是Model层和View层的交互和操作
 * 2.presenter 里面还有个接口
 * 其在Presenter层实现，给Model层回调，更改View层的状态
 * 确保Model层不能直接操作View层，如果没有这一接口实现的话
 * TestMapPresenterImpl只有View和Model的引用那么Model怎么把结果告诉View呢
 */

public class TestMapPresenterImpl implements ITestMvpPresenter {
    private ITestMvpView mView;
    private ITestMvpModel mModel;

    public TestMapPresenterImpl(ITestMvpView view) {
        this.mView = view;
        this.mModel = new ITestMvpImpl();
    }

    @Override
    public void requestLogin(String phone, String code) {
        if (mView != null) {
            mView.showLoading();
        }
        mModel.login(phone, code, new BaseModelCallBack() {
            @Override
            public void onResponse(BaseResponse response) {
                Log.d("jiejie","----------");
                mView.onInfoUpdate("成功" +response.getCode() + "  " + response.getData());
            }

            @Override
            public void onFailure(int code) {
                mView.showError(code);
            }
        });
    }
}
