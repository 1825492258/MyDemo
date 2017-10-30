package com.item.demo;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuzongjie on 2017/10/28.
 * OKHttp3的测试
 */

public class TestOkHttp3 {
    /**
     * 测试OkHttp 同步GET方法
     */
    @Test
    public void testGet(){
        // 创建OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建Request 对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .build();
        // OkHttpClient 执行 Request
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println("同步GET" + Thread.currentThread().getId());
            System.out.println("response:" + response.code() + " "+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试OkHttp 异步GET方法
     */
    @Test
    public void testAnyGet(){
        // 创建OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建Request 对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .build();
        // OkHttpClient 执行 Request
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response: 失败" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("我是异步GET" + Thread.currentThread().getId());
                System.out.println("response:" + response.code() + " "+response.body().string());
            }
        });
    }
    /**
     * 测试 OKHttp 同步POST方法
     */
    @Test
    public void testPost(){
        // 创建OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建Request对象
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,"{\\\"name\\\": \\\"dalimao\\\"}");
        Request request = new Request.Builder()
                .url("http://httpbin.org/post")// 请求行
                //.header();
                .post(body) // 请求体
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println("Post response" + response.code() + "   " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
