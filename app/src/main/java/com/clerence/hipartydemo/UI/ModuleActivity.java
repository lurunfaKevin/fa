package com.clerence.hipartydemo.UI;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Service.ShiftListener;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.ShifListenerImpl;

import static com.clerence.hipartydemo.Bean.Constant.Order;


/**
 * ModuleActivity     2017-03-24
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class ModuleActivity extends AppCompatActivity {

    private Button mBtnGame,mBtnChat;
    private MinaService.MinaBinder mBinder;
    private ProgressDialog progressDialog;
    private boolean isBind;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ModuleActivity.class);
        context.startActivity(intent);
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MinaService.MinaBinder) service;
            progressDialog.dismiss();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module);
        findViewById(R.id.module_bg).setBackground(getResources().getDrawable(R.drawable.module_bg));
        Intent intent = new Intent(this,MinaService.class);
        startService(intent);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在连接服务器");
        progressDialog.show();
        Button btnPunish = (Button) findViewById(R.id.module_bt_punish);
        btnPunish.setBackground(getResources().getDrawable(R.drawable.button));
        btnPunish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PunishActivity.actionStart(ModuleActivity.this);
                ModuleActivity.this.finish();
            }
        });
        Button btnRank = (Button) findViewById(R.id.module_bt_rank);
        btnRank.setBackground(getResources().getDrawable(R.drawable.button));
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chater chater = new Chater();
                chater.setOrder(Order.rank.name());
                String roomId = (String) BeanLab.getBeanLab().getFromMap("roomId");
                if (TextUtils.isEmpty(roomId)){
                    Toast.makeText(ModuleActivity.this,"请加入房间",Toast.LENGTH_SHORT).show();
                }else{
                    chater.setRoomId(roomId);
                    chater.setUserId(BeanLab.getBeanLab().getUserId());
                    mBinder.sendMsg(Json2Chater.chater2Json(chater));
                    ModuleActivity.this.finish();
                }
            }
        });

        mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chater chater = new Chater();
                chater.setUserId(BeanLab.getBeanLab().getUserId());
                chater.setOrder(Order.introduce.name());
                chater.setRoomId(BeanLab.getBeanLab().getFromMap("roomId").toString());
                mBinder.sendMsg(Json2Chater.chater2Json(chater));
                ModuleActivity.this.finish();
            }
        });
        mBtnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameTypeActivity.actionStart(ModuleActivity.this);
                ModuleActivity.this.finish();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ShiftListener listener = new ShifListenerImpl();
        findViewById(R.id.shift_im_mine).setOnClickListener(listener.getMyListener(this));
        findViewById(R.id.shift_im_room).setOnClickListener(listener.getRoomListener(this));
        findViewById(R.id.shift_im_lobby).setOnClickListener(listener.getHallListener(this));
        ImageView imageView = (ImageView) findViewById(R.id.shift_im_module);
        imageView.setImageResource(R.drawable.shift_module_check);
        mBtnChat = (Button) findViewById(R.id.module_bt_chat);
        mBtnGame = (Button) findViewById(R.id.module_bt_game);
        mBtnChat.setBackground(getResources().getDrawable(R.drawable.button));
        mBtnGame.setBackground(getResources().getDrawable(R.drawable.button));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
