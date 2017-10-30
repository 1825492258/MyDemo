package com.item.demo.network.http.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.item.demo.network.http.IHttpClient;
import com.item.demo.network.http.IRequest;
import com.item.demo.network.http.IResponse;

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

    private OKHttpClientImp(Context context) {
        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, java.util.concurrent.TimeUnit.SECONDS)//设置写入超时时间
                .build();
    }

    public static OKHttpClientImp getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OKHttpClientImp.class) {
                if (mInstance == null) {
                    Log.d("jiejie", "----- getInstance");
                    mInstance = new OKHttpClientImp(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public IResponse get(IRequest request, boolean forceCache) {
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
        Request OKRequest = builder.build();
        return execute(OKRequest);
    }


    @Override
    public IResponse post(IRequest request, boolean forceCache) {
        Request.Builder builder = new Request.Builder();
        // 指定请求方式
        request.setMethod("POST");
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, request.getBody().toString());
        Map<String, String> header = request.getHeader();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl())
                .put(body);
        Request OKRequest = builder.build();
        return execute(OKRequest);
    }

    @Override
    public void myGet(IRequest request, boolean forceCache, final MyCallBack callBack) {
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
        Request OKRequest = builder.build();
        mOkHttpClient.newCall(OKRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("jiejie","onFailure" );
                callBack.onMyFial(1111);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("jiejie","onResponse " + response.code() );
                if(response.isSuccessful()){
                    callBack.onMyCallBack(new BaseResponse(response.code(), response.body().string()));
                }else {
                    callBack.onMyFial(response.code());
                }

            }
        });
    }


    /**
     * 请求执行过程
     *
     * @param request Request
     * @return IResponse
     */
    private IResponse execute(Request request) {
        // final BaseResponse commonResponse = new BaseResponse();
        Log.d("jiejie", "----");
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                BaseResponse commonResponse = new BaseResponse();
//                commonResponse.setCode(1111);
//                commonResponse.setData(e.getMessage());

                Log.d("jiejie", "onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("jiejie", "onResponse" + response.body().string());
                // BaseResponse commonResponse = new BaseResponse();

                // return commonResponse;
            }
        });
        return null;
    }
}
