package com.clerence.hipartydemo.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Utils.AndroidHelp;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * LoginActivity     2017-03-19
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String URL = Constant.HTTPCONTEXT+"/user/login";
    private static final int RESULT_LOGIN = 200;
    private static final String FAIL = "PASSWORD FAILED";
    private static final String NO_USER = "NO USER";
    private EditText mEditAccount,mEditPsw;
    private BeanLab mBeanLab = BeanLab.getBeanLab();
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    private void initView() {
        mEditAccount = (EditText) findViewById(R.id.login_et_username);
        mEditPsw = (EditText) findViewById(R.id.login_et_password);
        Button btnLogin = (Button) findViewById(R.id.login_bt_login);
        btnLogin.setOnClickListener(this);
        Button btnSign = (Button) findViewById(R.id.login_bt_enroll);
        btnSign.setOnClickListener(this);
        Button btnForpas = (Button) findViewById(R.id.login_bt_forpas);
        btnForpas.setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.login_iv_qq);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_bt_login:
                String account = mEditAccount.getText().toString();
                String psw = mEditPsw.getText().toString();
                if (TextUtils.isEmpty(account)){
                    Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
                    break;
                }
                mBeanLab.setUserId(account);
                if (TextUtils.isEmpty(psw)){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!AndroidHelp.isNetworkAvailable(this)){
                    Toast.makeText(this,"网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                Logger.d("网络可用"+AndroidHelp.isNetworkAvailable(this));
                login(account,psw);
                break;
            case R.id.login_bt_enroll:
                break;
            case R.id.login_iv_qq:
              //  testPicker();
            default:
                break;
        }
    }

    private void testPicker() {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "第一项", "第二项", "这是一个很长很长很长很长很长很长很长很长很长的很长很长的很长很长的项"
        });
        picker.setCycleDisable(false);
        picker.setLineVisible(false);
        picker.setShadowVisible(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                Logger.d("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    private void login(String account, String psw) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("userId",account);
        builder.add("password",psw);
        Request request = new Request.Builder()
                .url(URL)
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Logger.d(e.getMessage());
                Logger.e("出问题",e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.obj = result;
                message.what = RESULT_LOGIN;
                handler.sendMessage(message);
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RESULT_LOGIN:
                    Chater result = Json2Chater.json2Chater((String) msg.obj);
                    if (result!=null){
                    handleResult(result);}
                    break;
                default:
                    break;
            }
        }
    };

    private void handleResult(Chater result) {
        switch (result.getMessage()){
            case Constant.SUCCEED:
                //跳转界面
                BeanLab.getBeanLab().setUserId(result.getUserId());
                LobbyActivity.actionStart(this);
                break;
            case FAIL:
                Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                break;
            case NO_USER:
                Toast.makeText(this,"用户名不存在",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
