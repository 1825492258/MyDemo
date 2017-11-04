package com.item.demo.activity.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.item.demo.R;
import com.item.demo.entity.MSMcode;
import com.item.demo.entity.UserInfo;
import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.IResponse;
import com.item.demo.network.http.impl.BaseRequest;
import com.item.demo.network.http.impl.BaseResponse;
import com.item.demo.network.http.impl.OKHttpClientImp;
import com.item.demo.utils.HttpConstants;
import com.item.demo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpOneActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_http_one)
    Button btnOneHttp; // 测试发送验证码
    @BindView(R.id.btn_http_two)
    Button btnTwoHttp; // 测试登录
    @BindView(R.id.btn_http_three)
    Button btnThreeJson;
    @BindView(R.id.btn_http_four)
    Button btnFourHttp;
    @BindView(R.id.btn_http_five)
    Button btnFiveHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_one);
        ButterKnife.bind(this);

        btnOneHttp.setOnClickListener(this);
        btnTwoHttp.setOnClickListener(this);
        btnThreeJson.setOnClickListener(this);
        btnFourHttp.setOnClickListener(this);
        btnFiveHttp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_http_one: // 获取验证码
                fetchSMSCode("18256025758");
                break;
            case R.id.btn_http_two: // 登录
                login("18256025758", codes);
                break;
            case R.id.btn_http_three: // JSON解析
                ceshiJson(json1);
                break;
            case R.id.btn_http_four: // 钱包支付 需要token
                setHttpPay();
                break;
            case R.id.btn_http_five:
                setFiveHttp();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void fetchSMSCode(String phone) {
        IRequest request = new BaseRequest(HttpConstants.VERIFICATION_CODE_URL + phone);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
//                Log.d("jiejie", response.getCode() + "   " + response.getData());
               ToastUtils.showToast(response.getCode() + "  " + response.getData());
//                MSMcode base = new Gson().fromJson(response.getData(), MSMcode.class);
//                codes = base.getData().getVerificationCode();
            }

            @Override
            public void onFailure(int code) {
                ToastUtils.showToast("获取验证码失败");
            }
        });
    }

    private String codes= "";
    private String token = "";

    private void login(String phone, String code) {
        IRequest request = new BaseRequest(HttpConstants.LOGIN_CONSUMER_URL);
        request.setBody("phoneNumber", phone);
        request.setBody("verificationCode", code);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                ToastUtils.showToast(response.getCode() + "  " + response.getData());
//                Log.d("jiejie", "onSuccess" + response.getCode() + "   " + response.getData());
//                UserInfo userInfo = new Gson().fromJson(response.getData(), UserInfo.class);
//                if (userInfo.getStatus().equals("SUCCESS")) {
//                    token = userInfo.getData().getUserInfo().getId() + "_" + userInfo.getData().getToken();
//                }
//                Log.d("jiejie", "token:" + token);
            }

            @Override
            public void onFailure(int code) {
                Log.d("jiejie", "onFailure " + code);
                ToastUtils.showToast("登录失败请重试");
            }
        });
    }

    private void setFiveHttp() {
        IRequest request = new BaseRequest(HttpConstants.MY_WALLET_URL);
        request.setHeader("XR-AUTH", token);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                ToastUtils.showToast(response.getCode() + "  " + response.getData());
                Log.d("jiejie", "response " + response.getCode() + "  " + response.getData());
            }

            @Override
            public void onFailure(int code) {
                Log.d("jiejie", "response" + code);
            }
        });
    }

    /**
     * 押金充值
     */
    private void setHttpPay() {
        IRequest request = new BaseRequest(HttpConstants.PAY_URL);
        request.setHeader("XR-AUTH", token);
        request.setBody("phoneNumber", "18256025758");
        request.setBody("transactionObjectType", "1");
        request.setBody("type", "1");
        request.setBody("orderName", "押金充值");
        request.setBody("tradeAmount", "299");
        OKHttpClientImp.getInstance().post(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                ToastUtils.showToast(response.getCode() + "  " + response.getData());
                Log.d("jiejie", "response" + response.getCode() + "  " + response.getData());
            }

            @Override
            public void onFailure(int code) {
               ToastUtils.showToast("返回错误码" + code);
                Log.d("jiejie", "onFailure" + code);
            }
        });
    }

    private String json = "{\"status\":\"SUCCESS\",\"error\":\"\",\"data\":{\"verificationCode\":\"2521\"}}";
    private String json1 = "{\"status\":\"ERROR\",\"error\":\"90秒内仅能获取一次短信验证码，请稍后再试\",\"data\":{}}";

    private void ceshiJson(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MSMcode base = new Gson().fromJson(json, MSMcode.class);
                Log.d("jiejie", base.getStatus() + "   " + base.getData().getVerificationCode());
            }
        }).start();
    }
}
