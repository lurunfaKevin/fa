package com.clerence.hipartydemo.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * MyProgressDialog     2017-03-25
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class MyProgressDialog {
    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在连接服务器");
        return progressDialog;
    }
    public static ProgressDialog createDownloadDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在初始化数据");
        return progressDialog;
    }
}
