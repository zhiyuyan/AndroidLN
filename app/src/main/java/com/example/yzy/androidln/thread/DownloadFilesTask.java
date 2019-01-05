package com.example.yzy.androidln.thread;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by yzy on 2019/1/5 0005.
 */

public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

    @Override
    protected Long doInBackground(URL... urls) {
        publishProgress();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
