package com.example.yzy.androidln.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yzy.androidln.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yzy on 2019/1/6 0006.
 */

public class AsyncTaskOrderTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_order_test);
    }

    public void onBtnClick(View view) {
//        new MyAsyncTask("AsyncTask#1").execute("");
//        new MyAsyncTask("AsyncTask#2").execute("");
//        new MyAsyncTask("AsyncTask#3").execute("");
//        new MyAsyncTask("AsyncTask#4").execute("");
//        new MyAsyncTask("AsyncTask#5").execute("");

        new MyAsyncTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("AsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("AsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private static final String TAG = "MyAsyncTask";

        private String mName = "AsyncTask";

        public MyAsyncTask(String name) {
            super();
            mName = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TAG, s + "execute finish at " + df.format(new Date()));
        }
    }
}
