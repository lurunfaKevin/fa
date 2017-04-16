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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Service.JoinRoomInterface;
import com.clerence.hipartydemo.Service.ShiftListener;
import com.clerence.hipartydemo.UI.fragment.LobbyFragment;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.MyProgressDialog;
import com.clerence.hipartydemo.Utils.ShifListenerImpl;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.clerence.hipartydemo.Bean.Constant.ConnectTypeEnum;
import static com.clerence.hipartydemo.Bean.Constant.Order;
import static com.clerence.hipartydemo.Bean.Constant.SUCCEED;


public class LobbyActivity extends AppCompatActivity implements JoinRoomInterface {

    private ProgressDialog progressDialog;
    private int flag = 0;

    //需要检测是否已经登录
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LobbyActivity.class);
        context.startActivity(intent);
    }

    private MinaService.MinaBinder mBinder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);
        initShiftButton();
        //启动和绑定后台服务
        Intent intent = new Intent(this, MinaService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
         progressDialog = MyProgressDialog.createProgressDialog(this);
        progressDialog.show();
        Button btnIn = (Button) findViewById(R.id.lobby_bt_join);
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                showPop();
            }
        });
        Button btnCreate = (Button) findViewById(R.id.lobby_bt_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                showPop();
            }


        });
    }

    private void initShiftButton() {
        ShiftListener listener = new ShifListenerImpl();
        findViewById(R.id.shift_im_mine).setOnClickListener(listener.getMyListener(this));
        findViewById(R.id.shift_im_module).setOnClickListener(listener.getModuelListener(this));
        findViewById(R.id.shift_im_room).setOnClickListener(listener.getRoomListener(this));
        //设置颜色高亮
        ImageView imageView = (ImageView) findViewById(R.id.shift_im_lobby);
        imageView.setImageResource(R.drawable.shift_lobby_check);
    }


    /**
     * 创建房间时输入房间号
     */
    private void showPop() {
        LobbyFragment lobbyFragment = new LobbyFragment();
        lobbyFragment.show(getSupportFragmentManager(), "lobby");

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Order.create.getIndex()) {
                Bundle bundle = (Bundle) msg.obj;
                Chater chater = (Chater) bundle.getSerializable("chater");
                if (chater.getMessage().equals(SUCCEED)) {
                    Map<String, Object> map = (Map<String, Object>) chater.getObject();
                    String roomName = (String) map.get("roomName");
                    String roomId = chater.getRoomId();
                    BeanLab.getBeanLab().setAttribute("roomName", roomName);
                    BeanLab.getBeanLab().setAttribute("roomId", roomId);
                    Logger.d(roomName);
                    RoomActivity.actionStart(LobbyActivity.this);
                }
            } else if (msg.what == Order.in.getIndex()) {
                    Bundle bundle = (Bundle) msg.obj;
                    Chater chater = (Chater) bundle.getSerializable("chater");
                    if (chater.getMessage().equals(SUCCEED)) {
                        Map<String, Object> map = (Map<String, Object>) chater.getObject();
                        String roomName = (String) map.get("roomName");
                        String roomId = chater.getRoomId();
                        BeanLab.getBeanLab().setAttribute("roomName", roomName);
                        BeanLab.getBeanLab().setAttribute("roomId", roomId);
                        Logger.d(roomName);
                        RoomActivity.actionStart(LobbyActivity.this);
                    }

            }
        }
    };

    @Override
    public void onFinish(String roomName) {
        if (BeanLab.getBeanLab().getConnectTypeEnum() == ConnectTypeEnum.DISCONNECT ||
                BeanLab.getBeanLab().getConnectTypeEnum() == null) {
            Toast.makeText(this, "没有连接服务器", Toast.LENGTH_SHORT).show();
            return;
        }
        mBinder.setHandler(mHandler);
        if (flag == 0) {
            Chater chater = new Chater();
            chater.setOrder(Order.create.name());
            chater.setUserId(BeanLab.getBeanLab().getUserId());
            Map<String, Object> map = new HashMap<>();
            map.put("roomName", roomName);
            chater.setObject(map);
            if (!mBinder.isConnect()) {
                Toast.makeText(this, "无法连接服务器", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mBinder.sendMsg(Json2Chater.chater2Json(chater));
            }
        } else if (flag == 1) {
            Chater chater = new Chater();
            chater.setOrder(Order.in.name());
            chater.setUserId(BeanLab.getBeanLab().getUserId());
            Map<String, Object> map = new HashMap<>();
            map.put("roomId", roomName);
            chater.setObject(map);
            if (!mBinder.isConnect()) {
                Toast.makeText(this, "无法连接服务器", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mBinder.sendMsg(Json2Chater.chater2Json(chater));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
