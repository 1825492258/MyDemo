# MyDemo
随便写的demo


### 1.ButterKnife的使用
     ButterKnife.bind(this);
     @BindView(R.id.btn_http_four)
     Button btnFourHttp;
     @BindView(R.id.btn_http_five)
     Button btnFiveHttp;
     static class ViewHolder {
         @BindView(R.id.item_search_title)
         TextView tvTitle;
         @BindView(R.id.item_search_snippet)
         TextView tvSnippet;

         private ViewHolder(View view) {
             ButterKnife.bind(this, view);
         }
     }

### 2.BaseRecyclerViewAdapterHelper 基本使用
    使RecyclerView写法更加简单，并封装了下拉刷新和上拉加载
    https://github.com/CymChad/BaseRecyclerViewAdapterHelper

### 3.SmartRefreshLayout的基本使用
    用于刷新的框架 可选的种类比较多
    https://github.com/scwang90/SmartRefreshLayout

### 4.友盟推送的使用

### 5.高德地图简单实用
    5.1 获取kay F:\WorkPlace\MyDemo>keytool -list -v -keystore app/keystore/debug.jks

### 6.OkHttp3.0的基本的使用
    6.1 项目中GET的封装
    6.2 项目中POST的封装
     private void login(String phone, String code) {
            IRequest request = new BaseRequest(HttpConstants.LOGIN_CONSUMER_URL);
            request.setBody("phoneNumber", phone);
            request.setBody("verificationCode", code);
            OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
                @Override
                public void onSuccess(BaseResponse response) {
                    Log.d("jiejie", "onSuccess" + response.getCode() + "   " + response.getData());
                    UserInfo userInfo = new Gson().fromJson(response.getData(), UserInfo.class);
                    if (userInfo.getStatus().equals("SUCCESS")) {
                        token = userInfo.getData().getUserInfo().getId() + "_" + userInfo.getData().getToken();
                    }
                    Log.d("jiejie", "token:" + token);
                }

                @Override
                public void onFailure(int code) {
                    Log.d("jiejie", "onFailure " + code);
                    ToastUtils.showToast("登录失败请重试");
                }
            });
        }

### 7.更换头像
    7.1 第一种使用，调用系统的方法来实现
    7.2 第二种使用，使用第三方库PictureSelector来实现
    7.3 第三种使用，以前写的http://www.cnblogs.com/wangfengdange/p/7443389.html来实现



