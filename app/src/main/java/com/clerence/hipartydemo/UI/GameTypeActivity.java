package com.clerence.hipartydemo.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Utils.PictureUtil;

/**
 * GameTypeActivity     2017-04-19
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class GameTypeActivity extends Activity {

    public static  void actionStart(Context context){
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
        Bitmap bg = PictureUtil.readBitMap(this, R.drawable.gametype_bg);
        Bitmap nc = PictureUtil.readBitMap(this, R.drawable.gametype_selfintro);
        Bitmap zy = PictureUtil.readBitMap(this, R.drawable.gametype_prologue);
        findViewById(R.id.gametype_relative_bg).setBackground(new BitmapDrawable(bg));
        //暖场游戏按钮
        findViewById(R.id.gametype_nc_btn).setBackground(new BitmapDrawable(nc));
        findViewById(R.id.gametype_nc_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarmGameListActivity.actionStart(GameTypeActivity.this);
                GameTypeActivity.this.finish();
            }
        });
        findViewById(R.id.gametype_zy_btn).setBackground(new BitmapDrawable(zy));
        //桌游按钮
        findViewById(R.id.gametype_zy_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.actionStart(GameTypeActivity.this);
                GameTypeActivity.this.finish();
            }
        });


    }
}
