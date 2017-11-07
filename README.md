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
    5.2 对高德地图的封装,将MapView看做View,建立ILbsLayer接口封装功能
        实现了定位,获取定位的信息
        搜索的功能,主要用到根据坐标搜索附近和根据搜索内容来搜素
        添加Marker，和在地图中心添加一个Marker
        设置Zoom 移动地图中心到某个点 移动地图在2点的可视范围
        路径的规划,获取路径并画出来
    5.3 注:定位的时候需要添加定位的动态权限 ACCESS_COARSE_LOCATION

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

### 8.获取网络的状态的切换
    8.1 定义一个BroadcastReceiver
    8.2 实例化过滤器
    8.3 绑定
         // 定义并实例化过滤器
         IntentFilter filter = new IntentFilter();
         // 添加过滤器的值
         filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
         //filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
         // filter.addAction("android.net.wifi.STATE_CHANGE");
         // 实例化广播监听器
         mReceiver = new NetworkConnectChangedReceiver();

### 9.轮播图的实现
    9.1 用UltraViewPager来实现  https://github.com/alibaba/UltraViewPager


