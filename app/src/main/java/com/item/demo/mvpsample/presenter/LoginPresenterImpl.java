package com.item.demo.mvpsample.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.item.demo.entity.UserInfo;
import com.item.demo.mvpsample.model.ITestMvpImpl;
import com.item.demo.mvpsample.model.ITestMvpModel;
import com.item.demo.mvpsample.model.bean.BaseModelCallBack;
import com.item.demo.mvpsample.model.bean.MSMcode;
import com.item.demo.mvpsample.view.ILoginView;
import com.item.demo.network.http.impl.BaseResponse;

/**
 * Created by wuzongjie on 2017/11/9.
 */

public class LoginPresenterImpl implements ILoginPresenter {
    private final String TAG = "jiejie";
    private ILoginView mView;
    private ITestMvpModel mModel;

    public LoginPresenterImpl(ILoginView view) {
        this.mView = view;
        mModel = new ITestMvpImpl();
    }

    /**
     * 发送短信
     *
     * @param phone 发送号码
     */
    @Override
    public void getCode(String phone) {

        mModel.getCode(phone, new BaseModelCallBack() {
            @Override
            public void onResponse(BaseResponse response) {
                Log.d(TAG, "onSuccess" + response.getCode() + "  " + response.getData());
                MSMcode msMcode = new Gson().fromJson(response.getData(),MSMcode.class);
                if("SUCCESS".equals(msMcode.getStatus())){
                    mView.getCode(msMcode.getData().getVerificationCode());
                }else {
                    mView.getErrorToast(msMcode.getError());
                }
            }

            @Override
            public void onFailure(int code) {
                Log.d(TAG, "onFailure" + code);

            }
        });
    }

    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     */
    @Override
    public void requestLogin(String phone, String code) {
        if (mView != null) {
            mView.showLoading();
        }
        mModel.login(phone, code, new BaseModelCallBack() {
            @Override
            public void onResponse(BaseResponse response) {
                mView.dissimssLoading();
                UserInfo userInfo = new Gson().fromJson(response.getData(),UserInfo.class);
                if("SUCCESS".equals(userInfo.getStatus())){
                    UserInfo.UserInfos.MyInfo info = userInfo.getData().getUserInfo();
                    Log.d(TAG,info.getId() + "  " + userInfo.getData().getToken());
                    mView.getLogin();
                }else {
                    mView.getErrorToast(userInfo.getError());
                }
                Log.d(TAG, "onSuccess" + response.getCode() + "  " + response.getData());
              //  mView.onInfoUpdate("成功" + response.getCode() + "  " + response.getData());
            }

            @Override
            public void onFailure(int code) {
                mView.dissimssLoading();
                mView.showError(code);
            }
        });
    }

}
