package com.item.demo.mvpsample.view;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.item.demo.R;
import com.item.demo.mvpsample.presenter.ILoginPresenter;
import com.item.demo.mvpsample.presenter.LoginPresenterImpl;
import com.item.demo.utils.ToastUtils;
import com.item.demo.utils.dialog.MyLoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录的界面 使用MVP的模式写的
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    @BindView(R.id.edt_login_phone)
    EditText edtPhone; // 填写的手机号码
    @BindView(R.id.edt_login_code)
    EditText edtCode; // 填写的Code
    @BindView(R.id.btn_login_code)
    Button btnCode; // 获取验证码按钮
    @BindView(R.id.btn_login)
    Button btnLogin; // 点击登录

    private ILoginPresenter mPresenter;
    private MyLoadDialog mDialog; // 登录的弹窗
    /**
     * 验证码倒计时
     */
    private CountDownTimer mCountDownTime = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            // 开始计时
            btnCode.setEnabled(false);
            btnCode.setText(l / 1000 + "");
        }

        @Override
        public void onFinish() {
            // 倒计时结束了
            btnCode.setEnabled(true);
            btnCode.setText(getResources().getString(R.string.get_code));
            cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenterImpl(this);
        btnCode.setEnabled(false);
        btnLogin.setEnabled(false);
        btnCode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        // 手机号输入框监听检查手机号输入是否合法
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean legal = ToastUtils.checkMobile(edtPhone.getText().toString().trim());
                btnCode.setEnabled(legal);
            }
        });
        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edtCode.length() >2 && edtPhone.length() >=11){
                    btnLogin.setEnabled(true);
                }else {
                    btnLogin.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTime.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_code: // 点击获取验证码
                mPresenter.getCode(edtPhone.getText().toString());
                break;
            case R.id.btn_login: // 点击登录
                mPresenter.requestLogin(edtPhone.getText().toString().trim(),edtCode.getText().toString().trim());
                break;
        }
    }

    /**
     * 获取验证码成功
     * @param code 返回的验证码
     */
    @Override
    public void getCode(String code) {
        edtCode.setText(code);
        mCountDownTime.start();
    }

    @Override
    public void getErrorToast(String message) {
        ToastUtils.showToast(message);
    }

    /**
     * 登录成功
     */
    @Override
    public void getLogin() {
        ToastUtils.showToast("登录成功");
    }

    @Override
    public void showLoading() {
        if(mDialog == null){
            mDialog = new MyLoadDialog(this);
        }
        mDialog.show();
    }

    @Override
    public void dissimssLoading() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void showError(int code) {

    }
}
