package com.item.demo.activity.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.item.demo.R;
import com.item.demo.entity.MSMcode;
import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.IResponse;
import com.item.demo.network.http.impl.BaseRequest;
import com.item.demo.network.http.impl.BaseResponse;
import com.item.demo.network.http.impl.OKHttpClientImp;
import com.item.demo.utils.HttpConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpOneActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_http_one)
    Button btnOneHttp; // 测试发送验证码
    @BindView(R.id.btn_http_two)
    Button btnTwoHttp; // 测试登录
    @BindView(R.id.btn_http_three)
    Button btnThreeJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_one);
        ButterKnife.bind(this);

        btnOneHttp.setOnClickListener(this);
        btnTwoHttp.setOnClickListener(this);
        btnThreeJson.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_http_one:
                fetchSMSCode("18256025758");
                break;
            case R.id.btn_http_two:
                login("18256025758", "1111");
                break;
            case R.id.btn_http_three:
                ceshiJson(json1);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void fetchSMSCode(String phone) {
        IRequest request = new BaseRequest(HttpConstants.VERIFICATION_CODE_URL + phone);
//        IResponse response = OKHttpClientImp.getInstance(this).get(request, false);
//        Log.d("jiejie", response.getCode() + "   " + response.getData());
//        OKHttpClientImp.getInstance(this).myGet(request, false, new IHttpClient.MyCallBack() {
//            @Override
//            public void onMyCallBack(BaseResponse response) {
//                Log.d("jiejie", response.getCode() + "     " + response.getData());
//            }
//        });
        OKHttpClientImp.getInstance(this).myGet(request, false, new IHttpClient.MyCallBack() {
            @Override
            public void onMyCallBack(BaseResponse response) {
                Log.d("jiejie", response.getCode() + "   " + response.getData());
            }

            @Override
            public void onMyFial(int code) {
                Log.d("jiejie", "shibai" + code);
            }
        });
    }

    private void login(String phone, String code) {
        IRequest request = new BaseRequest(HttpConstants.LOGIN_CONSUMER_URL);
        request.setBody("phoneNumber", phone);
        request.setBody("verificationCode", code);
        //request.setBody("phone",phone);
        OKHttpClientImp.getInstance(this).myGet(request, false, new IHttpClient.MyCallBack() {
                    @Override
                    public void onMyCallBack(BaseResponse response) {
                        Log.d("jiejie", response.getCode() + "   " + response.getData());
                    }

                    @Override
                    public void onMyFial(int code) {
                        Log.d("jiejie", "shibai" + code);
                    }
                }
        );
    }

    private String json = "{\"status\":\"SUCCESS\",\"error\":\"\",\"data\":{\"verificationCode\":\"2521\"}}";
    private String json1 = "{\"status\":\"ERROR\",\"error\":\"90秒内仅能获取一次短信验证码，请稍后再试\",\"data\":{}}";

    private void ceshiJson(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MSMcode base = new Gson().fromJson(json, MSMcode.class);
                Log.d("jiejie", base.getStatus() + "  ==" + base.getData().getVerificationCode() + base.getError());
            }
        }).start();
    }
}
