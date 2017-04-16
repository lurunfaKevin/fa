package com.clerence.hipartydemo.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.clerence.hipartydemo.Adapter.WarmupAdapter;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.Bean.WarmGame;
import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Utils.Json2Chater;
import com.clerence.hipartydemo.Utils.MyProgressDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * WarmGameActivity     2017-04-05
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class WarmGameListActivity extends Activity implements View.OnClickListener{

    private static final String URL = "http://"+ Constant.ADDRESS+":8099/user/warmgame";
    private static final int GET_WARM_DATA = 1;
    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5;
    private ListView mListView;
    private WarmupAdapter warmupAdapter;
    private static final int Fail_REQUEST = 2;
    private ProgressDialog downloadDialog;


    public static void actionStart(Context context){
        Intent intent = new Intent(context,WarmGameListActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warmup);

        initView();
        downloadDialog = MyProgressDialog.createDownloadDialog(this);
        downloadDialog.show();
        try {
            initData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void initData() throws UnsupportedEncodingException {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        String level = URLEncoder.encode("一", "utf-8");
        builder.add("level",level);
        Request request = new Request.Builder()
                .url(URL)
                .post(builder.build())
                .build();
        com.orhanobut.logger.Logger.d(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = new Message();
                message.what = GET_WARM_DATA;
                message.obj = response.body().string();
                handler.sendMessage(message);
                downloadDialog.dismiss();
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_WARM_DATA:
                    String string = (String) msg.obj;
                    Chater chater = Json2Chater.json2Chater(string);
                    Map<String,Object> map = (Map<String, Object>) chater.getObject();
                    Double size = (Double) map.get("size");

                    if (size>0){
                        List<WarmGame> warmGames = new ArrayList<>();
                        String json = map.get("list").toString();
                        try{
                            JSONArray jsonArray = new JSONArray(json);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                WarmGame warmGame = new WarmGame();
                                warmGame.setId((Integer) jsonObject.get("id"));
                                warmGame.setWarmGame((String) jsonObject.get("warmGame"));
                                warmGame.setWarmGameId((String) jsonObject.get("warmGameId"));
                                warmGame.setWarmGameLevel((String) jsonObject.get("warmGameLevel"));
                                warmGame.setWarmGameUrl((String) jsonObject.get("warmGameUrl"));
                                warmGame.setWarmGameName((String) jsonObject.get("warmGameName"));
                                warmGames.add(warmGame);
                            }
                        }catch (Exception e){}
                        warmupAdapter = new WarmupAdapter(warmGames,WarmGameListActivity.this);
                        mListView.setAdapter(warmupAdapter);
//                        for (WarmGame warmGame:warmGames){
//                            if (warmupAdapter!=null){
//                                warmupAdapter.addWarmGame(warmGame);
//                            }
//                        }
                    }
                    break;
                default:

                    break;
            }
        }
    };

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.warmup_btn1);

        mBtn2 = (Button) findViewById(R.id.warmup_btn2);
        mBtn3 = (Button) findViewById(R.id.warmup_btn3);
        mBtn4 = (Button) findViewById(R.id.warmup_btn4);
        mBtn5 = (Button) findViewById(R.id.warmup_btn5);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.warmup_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WarmGame warmGame = warmupAdapter.getItem(position);
                WarmGameActivity.actionStart(warmGame,WarmGameListActivity.this);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.warmup_btn1:
                sendLevel("一");
                break;
            case R.id.warmup_btn2:
                sendLevel("二");
                break;
            case R.id.warmup_btn3:
                sendLevel("三");
                break;
            case R.id.warmup_btn4:
                sendLevel("四");
                break;
            case R.id.warmup_btn5:
                sendLevel("五");
                break;
            default:
                break;

        }
    }

    public void sendLevel(String level1){
        String level = null;
        try {
            level = URLEncoder.encode(level1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("level",level);
        Request request = new Request.Builder()
                .url(URL)
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message message = new Message();

                message.what = Fail_REQUEST;
                message.obj = e.getMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = new Message();
                message.what = GET_WARM_DATA;
                message.obj = response.body().string();
            }
        });
    }
}
