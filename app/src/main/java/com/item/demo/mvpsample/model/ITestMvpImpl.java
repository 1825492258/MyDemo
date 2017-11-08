package com.item.demo.mvpsample.model;

import android.util.Log;

import com.item.demo.mvpsample.model.bean.BaseModelCallBack;
import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.impl.BaseRequest;
import com.item.demo.network.http.impl.BaseResponse;
import com.item.demo.network.http.impl.OKHttpClientImp;
import com.item.demo.utils.HttpConstants;

/**
 * Created by wuzongjie on 2017/11/8.
 * Model的实现类
 */

public class ITestMvpImpl implements ITestMvpModel {
    @Override
    public void login(String phone, String code, final BaseModelCallBack baseModelCallBack) {
        IRequest request = new BaseRequest(HttpConstants.LOGIN_CONSUMER_URL);
        request.setBody("phoneNumber", phone);
        request.setBody("verificationCode", code);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.d("jiejie","login" + response.getCode() + "  " + response.getData());
                baseModelCallBack.onResponse(response);
            }

            @Override
            public void onFailure(int code) {
                Log.d("jiejie","login onFail" + code);
                baseModelCallBack.onFailure(code);
            }
        });
    }
}
