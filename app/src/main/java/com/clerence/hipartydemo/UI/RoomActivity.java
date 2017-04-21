package com.clerence.hipartydemo.UI;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clerence.hipartydemo.Adapter.RoomAdapter;
import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Service.JoinRoomInterface;
import com.clerence.hipartydemo.Service.ShiftListener;
import com.clerence.hipartydemo.UI.fragment.RoomPopFragment;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.ShifListenerImpl;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * RoomActivity     2017-03-24
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class RoomActivity extends AppCompatActivity implements JoinRoomInterface {


    private EditText mEditInput;
    private Button mBtnSend;
    private TextView mTVRoomName;
    private ListView mListView;
    private MinaService.MinaBinder mBinder;
    private RoomAdapter mRoomAdapter;
    private RoomPopFragment mPopFragment;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MinaService.MinaBinder) service;
            mBinder.setHandler(mHandler);
            BeanLab.getBeanLab().setHandler(mHandler);
            Logger.d("test");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RoomActivity.class);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == Constant.Order.talk.getIndex()) {
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                mRoomAdapter.addChater(chater);
            } else if (msg.what == Constant.Order.rank.getIndex()) {

                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                mRoomAdapter.addChater(chater);
                Logger.d(chater.getOrder());
            } else if (msg.what == Constant.Order.ensure_punishment.getIndex()) {
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                Logger.d(chater.getOrder());
                mRoomAdapter.addChater(chater);
            } else if (msg.what == Constant.Order.introduce.getIndex()) {
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                Logger.d(chater.getOrder());
                if (mPopFragment == null) {
                    showPop();
                }
            }else if(msg.what == Constant.Order.ensure_introduce.getIndex()){
                if (mPopFragment !=null){
                    mPopFragment.dismiss();
                    mPopFragment = null;
                }
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                Logger.d(chater.getOrder());
                mRoomAdapter.addChater(chater);
            }else if (msg.what == Constant.Order.ensure_warmgame.getIndex()){
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                mRoomAdapter.addChater(chater);
            }
        }
    };

    private void showPop() {
        mPopFragment = new RoomPopFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(mPopFragment,"room");
        ft.commitAllowingStateLoss();



    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);
        initShiftButton();
        init();
        Intent intent = new Intent(this, MinaService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        if (mBinder == null) {
            findViewById(R.id.room_framTip).setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.room_tv_tip);
            tv.setText("binder为空");
        }
        if (BeanLab.getBeanLab().getFromMap("roomId") == null) {
            findViewById(R.id.room_framTip).setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.room_tv_tip);
            tv.setText("请加入房间");
        } else {
//            if (!mBinder.isConnect()) {
//                findViewById(R.id.room_framTip).setVisibility(View.VISIBLE);
//                TextView tv = (TextView) findViewById(R.id.room_tv_tip);
//                tv.setText("网络异常，请重新加入房间");
//            } else {
            findViewById(R.id.room_framTip).setVisibility(View.GONE);
            findViewById(R.id.room_lv_dialog).setVisibility(View.VISIBLE);
            findViewById(R.id.room_frameBottom).setVisibility(View.VISIBLE);
            String roomName = (String) BeanLab.getBeanLab().getFromMap("roomName");
            String roomId = (String) BeanLab.getBeanLab().getFromMap("roomId");
            mTVRoomName.setText(roomName + "(" + roomId + ")");
            initSend();
            //}
        }

    }

    private void initSend() {
        mEditInput = (EditText) findViewById(R.id.room_et_input);
        mBtnSend = (Button) findViewById(R.id.room_bt_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditInput.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(RoomActivity.this, "消息不能为空", Toast.LENGTH_SHORT);
                } else {
                    Chater chater = new Chater();
                    chater.setOrder("talk");
                    chater.setUserId(BeanLab.getBeanLab().getUserId());
                    chater.setRoomId(BeanLab.getBeanLab().getFromMap("roomId").toString());
                    chater.setMessage(input);
                    mBinder.sendMsg(Json2Chater.chater2Json(chater));
                    mEditInput.setText("");
                }
            }
        });
    }

    private void init() {
        mTVRoomName = (TextView) findViewById(R.id.room_tv_room_name);
        mTVRoomName.setText("");
        mListView = (ListView) findViewById(R.id.room_lv_dialog);
        mRoomAdapter = new RoomAdapter(this);
        mListView.setAdapter(mRoomAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent = new Intent(this, MinaService.class);
//        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //unbindService(mServiceConnection);
        Logger.d("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
       // BeanLab.getBeanLab().setChaters(new ArrayList<Chater>());
    }

    private void initShiftButton() {
        ShiftListener listener = new ShifListenerImpl();
        findViewById(R.id.shift_im_mine).setOnClickListener(listener.getMyListener(this));
        findViewById(R.id.shift_im_module).setOnClickListener(listener.getModuelListener(this));
        findViewById(R.id.shift_im_lobby).setOnClickListener(listener.getHallListener(this));
        //设置颜色高亮
        ImageView imageView = (ImageView) findViewById(R.id.shift_im_room);
        imageView.setImageResource(R.drawable.shift_room_check);
    }

    @Override
    public void onFinish(String roomNum) {
        if (TextUtils.isEmpty(roomNum)){
            Toast.makeText(RoomActivity.this,"自我介绍内容为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPopFragment!=null){
            mPopFragment = null;
        }
        Chater chater = new Chater();
        chater.setUserId(BeanLab.getBeanLab().getUserId());
        chater.setRoomId(BeanLab.getBeanLab().getFromMap("roomId").toString());
        chater.setOrder(Constant.Order.ensure_introduce.name());
        Map<String,Object> map = new HashMap<>();
        map.put("introduction",roomNum);
        chater.setObject(map);
        mBinder.sendMsg(Json2Chater.chater2Json(chater));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("activity restart");
    }


}
