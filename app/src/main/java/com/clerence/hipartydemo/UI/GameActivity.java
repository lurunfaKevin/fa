package com.clerence.hipartydemo.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.UI.fragment.GamePopFragment;
import com.clerence.hipartydemo.Utils.PictureUtil;

/**
 * GameActivity     2017-04-21
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class GameActivity extends AppCompatActivity{

    public static void actionStart(Context context){
        Intent intent = new Intent(context,GameActivity.class);
        context.startActivity(intent);
    }

    private ImageView imageView;
    private Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        imageView = (ImageView) findViewById(R.id.game_pic);
        Bitmap bitmap = PictureUtil.readBitMap(this, R.drawable.game1);
        imageView.setImageBitmap(bitmap);
        mButton = (Button) findViewById(R.id.game_sure);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GamePopFragment gameFragment = new GamePopFragment();
                    gameFragment.show(getSupportFragmentManager(), "lobby");
            }
        });

    }
}
