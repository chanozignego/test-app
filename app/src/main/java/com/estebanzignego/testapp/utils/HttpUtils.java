package com.estebanzignego.testapp.utils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class HttpUtils {

    private static OkHttpClient client = new OkHttpClient();
    private static Request.Builder builder = new Request.Builder();

    public static void get(String url, HttpCallback cb) {
        call("GET", url, cb);
    }

    public void put(String url, HttpCallback cb) {
        call("PUT", url, cb);
    }

    public void post(String url, HttpCallback cb) {
        call("POST", url, cb);
    }

    private static void call(String method, String url, final HttpCallback callback) {
        Request request = builder.url(url).method(method, method.equals("GET") ? null : new RequestBody() {

            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        }).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    callback.onFailure(null, e);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    try {
                        callback.onFailure(response, null);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    return;
                }
                callback.onSuccess(response);
            }

        });
    }


    public interface HttpCallback  {

        public void onFailure(Response response, Throwable throwable) throws Throwable;
        public void onSuccess(Response response);
    }

}