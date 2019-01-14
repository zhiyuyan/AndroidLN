package com.example.yzy.androidln.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpDemoActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    private static final String TAG = "OkhttpDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousGet();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }).start();
        try {
            asynchronousGet();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void synchronousGet() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(TAG, responseHeaders.name(i) + ":" + responseHeaders.value(i));
            }

            Log.d(TAG, response.body().string());
        }
    }

    private void asynchronousGet() throws Exception {
        final Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, responseHeaders.name(1) + ":" + responseHeaders.value(i));
                    }
                    Log.d(TAG, responseBody.string());
                }
            }
        });
    }
}
