package com.clerence.hipartydemo.UI;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.Bean.WarmGame;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.MyProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * WarmGameActivity     2017-04-16
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class WarmGameActivity extends AppCompatActivity {

    private WarmGame mWarmGame;
    private ImageView mImageView;
    private TextView mNameTextView,mLevelTextView,mGameTextView;
    private MinaService.MinaBinder mBinder;
    private Button mOkButton;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MinaService.MinaBinder) service;
            if (mBinder!=null){
                mBinder.setHandler(handler);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static void actionStart(WarmGame warmGame, Context context){
        if (warmGame!=null&&context!=null){
            Intent intent = new Intent(context,WarmGameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("warmGame",warmGame);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warmup_inform);
        //绑定mina
        Intent intent1 = new Intent(this,MinaService.class);
        bindService(intent1,mServiceConnection,BIND_AUTO_CREATE);
        Intent intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            mWarmGame= (WarmGame) bundle.getSerializable("warmGame");
        }
        if (mWarmGame!=null){
            initView();
        }

    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.warmup_inform_iv_game);
        mGameTextView = (TextView) findViewById(R.id.warmup_inform_tv_introduction);
        mNameTextView = (TextView) findViewById(R.id.warmup_inform_tv_game_title);
        mLevelTextView = (TextView) findViewById(R.id.warmup_inform_level);
        if (mWarmGame.getWarmGameUrl()!=null){
            ProgressDialog downloadDialog = MyProgressDialog.createDownloadDialog(this);
            downloadDialog.show();
            Picasso.with(this).load(mWarmGame.getWarmGameUrl()).into(mImageView);
            downloadDialog.dismiss();
        }
        if (mWarmGame.getWarmGame() !=null){
            mGameTextView.setText(mWarmGame.getWarmGame());
        }else{
        //    mGameTextView.setText;
        }
        if (mWarmGame.getWarmGameLevel() !=null){
            mLevelTextView.setText("难度："+mWarmGame.getWarmGameLevel()+"星");
        }else{
         //   mLevelTextView.setText(mWarmGame.getWarmGameLevel());
        }
        if (mWarmGame.getWarmGameName() !=null){
            mNameTextView.setText(mWarmGame.getWarmGameName());
        }else{
          //  mNameTextView.setText(mWarmGame.getWarmGameName());
        }
        mOkButton = (Button) findViewById(R.id.warmup_inform_send);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinder!=null){
                    mBinder.setHandler(BeanLab.getBeanLab().getHandler());
                    Chater chater = new Chater();
                    chater.setUserId(BeanLab.getBeanLab().getUserId());
                    if (BeanLab.getBeanLab().getFromMap("roomId") == null){
                        Toast.makeText(WarmGameActivity.this,"请加入房间",Toast.LENGTH_SHORT).show();
                    }else{
                        chater.setRoomId((String) BeanLab.getBeanLab().getFromMap("roomId"));
                        chater.setOrder(Constant.Order.ensure_warmgame.name());
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put("warmgameId",mWarmGame.getWarmGameId());
                        chater.setObject(map);
                    }
                    mBinder.sendMsg(Json2Chater.chater2Json(chater));
                    WarmGameActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unbindService(mServiceConnection);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
