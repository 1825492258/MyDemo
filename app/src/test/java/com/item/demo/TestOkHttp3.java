package com.item.demo;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
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
    public void testGet() {
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
            System.out.println("response:" + response.code() + " " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试OkHttp 异步GET方法
     */
    @Test
    public void testAnyGet() {
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
                System.out.println("response:" + response.code() + " " + response.body().string());
            }
        });
    }

    /**
     * 测试 OKHttp 同步POST方法
     */
    @Test
    public void testPost() {
        // 创建OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        // 创建Request对象
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\\\"name\\\": \\\"dalimao\\\"}");
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
            System.out.println("异常-----" + e);
            e.printStackTrace();
        }
    }

    @Test
    public void testInterceptor() {
        // 定义拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long start = System.currentTimeMillis();
                Request request = chain.request();
                Response response = chain.proceed(request);
                long end = System.currentTimeMillis();
                System.out.println("interceptor: cost time = " + (end - start));
                return response;
            }
        };
        // 创建OkHttpClient 对象
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        // 创建Request 对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .build();
        // OkHttpClient 执行Request
        try {
            Response response = client.newCall(request).execute();
            System.out.print("response:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("异常-----" + e);
        }
    }

    /**
     * 设置缓存
     */
    @Test
    public void testCache() {
        // 创建缓存对象
        Cache cache = new Cache(new File("cache.chache"), 1024 * 1024); // 设置可用缓存10M
        // 创建OKHttpClient 对象
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        // 创建Request对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                //.cacheControl(CacheControl.FORCE_NETWORK) // 从网上获取
                .cacheControl(CacheControl.FORCE_CACHE) // 从缓存获取
                .build();
        // OKHttpClient 执行Request
        try {
            Response response = client.newCall(request).execute();
            Response responseCache = response.cacheResponse();
            Response responseNet = response.networkResponse();
            if (responseCache != null) {
                // 从缓存响应
                System.out.println("response from cache  缓存获取");
            }
            if (responseNet != null) {
                // 从网络响应
                System.out.println("response from net 网络获取");
            }
            System.out.println("response:--" + response.body().string());
        } catch (IOException e) {
            System.out.println("异常-----" + e);
            e.printStackTrace();
        }
    }

    /**
     * 设置缓存加拦截器 （从这抄的）
     * http://blog.csdn.net/ljd2038/article/details/51046512
     */
    @Test
    public void testCache2() {
        // 创建缓存对象
        Cache cache = new Cache(new File("cache.chache"), 1024 * 1024); // 设置可用缓存1M
        // 创建拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!true) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (true) {
                    int maxAge = 30; // 在线缓存30秒
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge).build();
                } else {
                    int maxStale = 60 * 60 * 24 * 4 * 7; // 离线缓存4周
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };
        // 创建OKHttpClient 对象
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
        // 创建Request对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=ic?code=3")
                //.cacheControl(CacheControl.FORCE_NETWORK)
                // .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        // OKHttpClient 执行Request
        try {
            Response response = client.newCall(request).execute();
            Response responseCache = response.cacheResponse();
            Response responseNet = response.networkResponse();
            if (responseCache != null) {
                // 从缓存响应
                System.out.println("response from cache 缓存");
            }
            if (responseNet != null) {
                // 从网络响应
                System.out.println("response from net 网络");
            }
            System.out.println("response:--" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
