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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.clerence.hipartydemo.Adapter.PunishmentAdapter;
import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.Bean.Punishment;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.MyProgressDialog;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.clerence.hipartydemo.Bean.Constant.*;

/**
 * PunishActivity     2017-03-24
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class PunishActivity extends AppCompatActivity {

    public static void actionStart(Context context){
        Intent intent = new Intent(context,PunishActivity.class);
        context.startActivity(intent);
    }

    private ProgressDialog mProgressDialog;
    private MinaService.MinaBinder mBinder;
    private RatingBar mRatingBar;
    private ListView mListView;
    private ImageView mImageView;
    private PunishmentAdapter mPunishmentAdapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Order.punishment.getIndex()){
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                Map<String,Object> map = (Map<String, Object>) chater.getObject();
                String size = (String) map.get("size");
                if (!size.equals("0")){
                    String listString = (String) map.get("list");
                    try {
                        JSONArray jsonArray = new JSONArray(listString);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String punishmentId = (String) jsonObject.get("punishmentId");
                            String punishment = (String) jsonObject.get("punishment");
                            String punishmentLevel = (String) jsonObject.get("punishmentLevel");
                            String punishmentName = (String) jsonObject.get("punishmentId");
                            Punishment punishment1 = new Punishment();
                            punishment1.setPunishment(punishment);
                            punishment1.setPunishmentId(punishmentId);
                            punishment1.setPunishmentLevel(punishmentLevel);
                            punishment1.setPunishmentName(punishmentName);
                            mPunishmentAdapter.setData(punishment1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Logger.e("error",e);
                    }
                }
            }
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MinaService.MinaBinder) service;
            if (mHandler!=null){
                mBinder.setHandler(mHandler);
                Chater chater = new Chater();
                chater.setOrder(Order.punishment.name());
                chater.setUserId(BeanLab.getBeanLab().getUserId());
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("punishmentLevel","1");
                chater.setObject(map);
                chater.setRoomId(BeanLab.getBeanLab().getFromMap("roomId").toString());
                mBinder.sendMsg(Json2Chater.chater2Json(chater));
            }
            mProgressDialog.dismiss();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punish);
        Intent intent = new Intent(this,MinaService.class);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        mProgressDialog = MyProgressDialog.createProgressDialog(this);
        init();

    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.punish_demonbutton);
        mListView = (ListView) findViewById(R.id.punish_lv);
        mPunishmentAdapter = new PunishmentAdapter(this,new ArrayList<Punishment>());
        mListView.setAdapter(mPunishmentAdapter);
        mRatingBar = (RatingBar) findViewById(R.id.punish_level);
        mRatingBar.setRating(1);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Logger.d("改变的等级"+rating);
                Chater chater = new Chater();
                chater.setOrder(Order.punishment.name());
                String roomId = (String) BeanLab.getBeanLab().getFromMap("roomId");
                if (TextUtils.isEmpty(roomId)){
                    Toast.makeText(PunishActivity.this,"请加入房间",Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("punishmentLevel",rating);
                    chater.setUserId(BeanLab.getBeanLab().getUserId());
                    chater.setRoomId(roomId);
                    chater.setObject(map);
                    mBinder.sendMsg(Json2Chater.chater2Json(chater));
                }
            }
        });
        mImageView.setImageResource(R.mipmap.ic_launcher);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = mPunishmentAdapter.getSelect();
                if (select==-1){
                    Toast.makeText(PunishActivity.this,"请选择一项",Toast.LENGTH_SHORT).show();
                }else{
                    Punishment punishment = mPunishmentAdapter.getItem(select);
                    Chater chater = new Chater();
                    chater.setOrder(Order.ensure_punishment.name());
                    String roomId = (String) BeanLab.getBeanLab().getFromMap("roomId");
                    if (TextUtils.isEmpty(roomId)){
                        Toast.makeText(PunishActivity.this,"请加入房间",Toast.LENGTH_SHORT).show();
                    }else{
                        chater.setRoomId(roomId);
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put("punishmentId",punishment.getPunishmentId());
                        chater.setObject(map);
                        chater.setUserId(BeanLab.getBeanLab().getUserId());
                        mBinder.sendMsg(Json2Chater.chater2Json(chater));
                        mBinder.setHandler(BeanLab.getBeanLab().getHandler());
                    }
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }
}
