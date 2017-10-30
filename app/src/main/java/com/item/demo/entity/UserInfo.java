package com.item.demo.entity;

import com.item.demo.network.http.biz.BaseBizResponse;

/**
 * Created by wuzongjie on 2017/10/30.
 */

public class UserInfo extends BaseBizResponse {
    private UserInfos data;

    public UserInfos getData() {
        return data;
    }

    public void setData(UserInfos data) {
        this.data = data;
    }

    public class UserInfos {
        private MyInfo userInfo;
        private String token;

        public MyInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(MyInfo userInfo) {
            this.userInfo = userInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public class MyInfo {
            private int id;
            private String nickName;
            private String phoneNumber;
            private String profileImage;
            private String cardId;
            private boolean deposit;
            private boolean authenticated;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public boolean isDeposit() {
                return deposit;
            }

            public void setDeposit(boolean deposit) {
                this.deposit = deposit;
            }

            public boolean isAuthenticated() {
                return authenticated;
            }

            public void setAuthenticated(boolean authenticated) {
                this.authenticated = authenticated;
            }
        }
    }
}
