package com.estebanzignego.testapp.utils;

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

    private OkHttpClient client = new OkHttpClient();
    private Request.Builder builder;

    public void get(String url, HttpCallback cb) {
        call("GET", url, cb);
    }

    public void put(String url, HttpCallback cb) {
        call("PUT", url, cb);
    }

    public void post(String url, HttpCallback cb) {
        call("POST", url, cb);
    }

    private void call(String method, String url, final HttpCallback callback) {
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
                callback.onFailure(null, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(response, null);
                    return;
                }
                callback.onSuccess(response);
            }

        });
    }


    public interface HttpCallback  {

        public void onFailure(Response response, Throwable throwable);
        public void onSuccess(Response response);
    }

}