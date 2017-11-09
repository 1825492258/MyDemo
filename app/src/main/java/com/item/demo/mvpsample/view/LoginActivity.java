package com.item.demo.mvpsample.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.item.demo.R;

import butterknife.BindView;

/**
 * 登录的界面 使用MVP的模式写的
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_login_phone)
    EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
