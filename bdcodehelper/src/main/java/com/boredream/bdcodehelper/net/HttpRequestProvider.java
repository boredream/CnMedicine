package com.boredream.bdcodehelper.net;


import android.os.AsyncTask;

import com.boredream.bdcodehelper.utils.UserInfoKeeper;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class HttpRequestProvider {
    // LeanCloud
    public static final String HOST = "https://api.leancloud.cn";

    private static final String APP_ID_NAME = "X-LC-Id";
    private static final String API_KEY_NAME = "X-LC-Key";
    public static final String SESSION_TOKEN_KEY = "X-LC-Session";

    private static final String APP_ID_VALUE = "YeWYXUgKCPByvToJj3u1mUUw-gzGzoHsz";
    private static final String API_KEY_VALUE = "Ypx2LgIxvScVwgzPI0UcNSgM";

    protected static Retrofit retrofit;
    protected static OkHttpClient httpClient;

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }

    static {
        // OkHttpClient
        httpClient = new OkHttpClient();

        // 统一添加的Header
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader(APP_ID_NAME, APP_ID_VALUE)
                        .addHeader(API_KEY_NAME, API_KEY_VALUE)
                        .addHeader(SESSION_TOKEN_KEY, UserInfoKeeper.getToken())
                        .build();
                return chain.proceed(request);
            }
        });

        // log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        // Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create()) // gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // rxjava 响应式编程
                .client(httpClient)
                .callbackExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                .build();
    }
}

