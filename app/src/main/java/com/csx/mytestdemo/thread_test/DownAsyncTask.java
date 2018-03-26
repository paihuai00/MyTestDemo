package com.csx.mytestdemo.thread_test;

import android.os.AsyncTask;

/**
 * @Created by cuishuxiang
 * @date 2018/3/26.
 * @description:
 */

public class DownAsyncTask extends AsyncTask<String, Integer, Boolean> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return false;
    }
}
