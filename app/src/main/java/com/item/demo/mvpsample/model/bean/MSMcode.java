package com.item.demo.mvpsample.model.bean;

import com.item.demo.network.http.biz.BaseBizResponse;

/**
 * Created by wuzongjie on 2017/10/28.
 */

public class MSMcode extends BaseBizResponse {
    private Verification data;

    public Verification getData() {
        return data;
    }

    public void setData(Verification data) {
        this.data = data;
    }

    public static class Verification {
        private String verificationCode;

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }
    }
}
