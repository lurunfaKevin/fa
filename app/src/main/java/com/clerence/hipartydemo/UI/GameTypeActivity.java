package com.clerence.hipartydemo.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.clerence.hipartydemo.R;

/**
 * GameTypeActivity     2017-04-19
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class GameTypeActivity extends Activity {

    public void actionStart(Context context){
        Intent intent = new Intent(context,GameTypeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gametype);
        initView();
    }

    private void initView() {
        //暖场游戏按钮
        findViewById(R.id.gametype_nc_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //桌游按钮
        findViewById(R.id.gametype_zy_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
