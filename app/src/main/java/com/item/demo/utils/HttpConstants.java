package com.item.demo.utils;

/**
 * Created by wuzongjie on 2017/10/28.
 */

public class HttpConstants {
    /**
     * 服务器地址
     */
    private final static String SERVER_URL = "http://192.168.3.20:8083/smarttransport-bms/";
    /**
     * 2.1.3 获取验证码
     * get
     * http://192.168.3.20:8083/smarttransport-bms/verification-code/phoneNumber
     */
    public final static String VERIFICATION_CODE_URL = SERVER_URL + "verification-code/";
    /**
     * 2.2.1 用户登录
     * get
     * http://192.168.3.20:8083/smarttransport-bms/login-consumer?phoneNumber=18356025758&verificationCode=9009
     */
    public final static String LOGIN_CONSUMER_URL = SERVER_URL + "login-consumer?phoneNumber=${phoneNumber}&verificationCode=${verificationCode}";
    public  final static String JJ = "http://cloud.bmob.cn/f34e28da5816433d/getMsgCode?phone=${phone}";
}
