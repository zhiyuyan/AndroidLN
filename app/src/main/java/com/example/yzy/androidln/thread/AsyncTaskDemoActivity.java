package com.example.yzy.androidln.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2019/1/6 0006.
 */

public class AsyncTaskDemoActivity extends AppCompatActivity {

    private TextView mTvContent;
    private DownloadFilesTask mDownloadFilesTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_demo);
        mTvContent = findViewById(R.id.tv_content);
        mDownloadFilesTask = new DownloadFilesTask();
        mDownloadFilesTask.execute("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownloadFilesTask != null) {
            mDownloadFilesTask.cancel(false);
        }
    }

    class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

        private static final String TAG = "DownloadFilesTask";

        @Override
        protected Long doInBackground(String... urls) {
            long result = 0;
            for (int i = 0; i <= 100; i++) {
                result = i;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
                // 如果调用cancel()，早点退出
                if (isCancelled()) {
                    break;
                }
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate: " + values[0] + "%");
            if (mTvContent != null) {
                mTvContent.setText(String.valueOf(values[0]));
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "onPostExecute(): " + aLong);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute()");
        }

        @Override
        protected void onCancelled(Long aLong) {
            super.onCancelled(aLong);
            Log.d(TAG, "onCancelled(): " + aLong);
        }
    }
}
