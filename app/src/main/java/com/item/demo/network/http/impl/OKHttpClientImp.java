package com.item.demo.network.http.impl;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuzongjie on 2017/10/28.
 * OKHttp的封装
 */

public class OKHttpClientImp implements IHttpClient {

    private static volatile OKHttpClientImp mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHanded;

    private OKHttpClientImp() {
        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置写入超时时间
                .build();
        // 获取主线程的Handler
        mHanded = new Handler(Looper.getMainLooper());
    }

    public static OKHttpClientImp getInstance() {
        if (mInstance == null) {
            synchronized (OKHttpClientImp.class) {
                if (mInstance == null) {
                    mInstance = new OKHttpClientImp();
                }
            }
        }
        return mInstance;
    }

//    @Override
//    public IResponse get(IRequest request, boolean forceCache) {
//        Request.Builder builder = new Request.Builder();
//        // 指定请求的方式
//        request.setMethod("GET");
//        // 解析头部
//        Map<String, String> header = request.getHeader();
//        for (String key : header.keySet()) {
//            builder.header(key, header.get(key));
//        }
//        // 获取URL
//        builder.url(request.getUrl()).get();
//        Request OKRequest = builder.build();
//        return execute(OKRequest);
//    }


//    @Override
//    public IResponse post(IRequest request, boolean forceCache) {
//        Request.Builder builder = new Request.Builder();
//        // 指定请求方式
//        request.setMethod("POST");
//        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(mediaType, request.getBody().toString());
//        Map<String, String> header = request.getHeader();
//        for (String key : header.keySet()) {
//            builder.header(key, header.get(key));
//        }
//        builder.url(request.getUrl())
//                .put(body);
//        Request OKRequest = builder.build();
//        return execute(OKRequest);
//    }

    @Override
    public void get(IRequest request, boolean forceCache, RequestCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        // 指定请求的方式
        request.setMethod("GET");
        // 解析头部
        Map<String, String> header = request.getHeader();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        // 获取URL
        builder.url(request.getUrl()).get();
        Request OkRequest = builder.build();
        execute(OkRequest, callBack);
    }

    @Override
    public void post(IRequest request, boolean forceCache, RequestCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        // 指定请求方式
        request.setMethod("POST");
        // POST发送JSON数据
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, request.getBody().toString());
        // 解析头部
        Map<String, String> header = request.getHeader();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl()).post(body);
        Request OkRequest = builder.build();
        execute(OkRequest, callBack);
    }

    private void execute(Request okRequest, final RequestCallBack callBack) {
        mOkHttpClient.newCall(okRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                setFailureCall(1111, callBack);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    setSuccessCall(response.code(), response.body().string(), callBack);
                } else {
                    Log.d("jiejie", response.code() + "  " + response.body().string());
                    setFailureCall(response.code(), callBack);
                }
            }
        });
    }

    private void setFailureCall(final int code, final RequestCallBack callBack) {
        mHanded.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(code);
                }
            }
        });
    }

    private void setSuccessCall(final int code, final String string, final RequestCallBack callBack) {
        mHanded.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onSuccess(new BaseResponse(code, string));
                }
            }
        });
    }


//    /**
//     * 请求执行过程
//     *
//     * @param request Request
//     * @return IResponse
//     */
//    private IResponse execute(Request request) {
//        // final BaseResponse commonResponse = new BaseResponse();
//        Log.d("jiejie", "----");
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
////                BaseResponse commonResponse = new BaseResponse();
////                commonResponse.setCode(1111);
////                commonResponse.setData(e.getMessage());
//
//                Log.d("jiejie", "onFailure");
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.d("jiejie", "onResponse" + response.body().string());
//                // BaseResponse commonResponse = new BaseResponse();
//
//                // return commonResponse;
//            }
//        });
//        return null;
//    }
}
