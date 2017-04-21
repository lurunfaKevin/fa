package com.clerence.hipartydemo.UI.testUI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.clerence.hipartydemo.R;

/**
 * AndroidPickerTestActivity     2017-04-03
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class AndroidPickerTestActivity extends Activity{
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test222);
//        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.wheelview_container);
//        final OptionPicker picker = new OptionPicker(this, new String[]{
//                "第一项", "第二项", "这是一个很长很长很长很长很长很长很长很长很长的很长很长的很长很长的项"
//        });
//        picker.setCycleDisable(false);
//        picker.setLineVisible(false);
//        picker.setShadowVisible(true);
//        picker.setTextSize(11);
//        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//            @Override
//            public void onOptionPicked(int index, String item) {
//                Logger.d("index=" + index + ", item=" + item);
//            }
//        });
//        viewGroup.addView(picker.getContentView());
//        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.d(picker.getSelectedIndex());
//            }
//        });
//        mImageView = (ImageView) findViewById(R.id.image_download);
//        findViewById(R.id.download_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Picasso.with(AndroidPickerTestActivity.this).load("http://10.110.210.129:8080/user/download?url=/Users/lurunfa/Desktop/plane.png")
//                        .into(mImageView);
//
//            }
//        });
    }
}
