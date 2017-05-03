package com.clerence.hipartydemo.UI.testUI;

import android.app.Activity;
import android.os.Bundle;

import com.clerence.hipartydemo.R;
import com.panxw.android.imageindicator.ImageIndicatorView;

/**
 * TestActivity     2017-04-21
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class TestActivity extends Activity {

    private ImageIndicatorView imageIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_warmup);
//        final TextView tv = (TextView) findViewById(R.id.test_tv);
//        imageIndicatorView = (ImageIndicatorView) findViewById(R.id.imageIndi);
//        final Integer[] resArray = new Integer[] { R.mipmap.ic_launcher, R.mipmap.ic_launcher};
//        imageIndicatorView.setupLayoutByDrawable(resArray);
//        imageIndicatorView.setIndicateStyle(ImageIndicatorView.INDICATE_ARROW_ROUND_STYLE);
//        imageIndicatorView.show();
//        imageIndicatorView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {
//            @Override
//            public void onPosition(int position, int totalCount) {
//                Logger.d(position);
//                tv.setText(position+"nihao");
//            }
//        });
    }
}
