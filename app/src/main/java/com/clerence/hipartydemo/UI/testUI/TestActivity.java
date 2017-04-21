package com.clerence.hipartydemo.UI.testUI;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.clerence.hipartydemo.R;

/**
 * TestActivity     2017-04-21
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test222);
        TextView tv = (TextView) findViewById(R.id.test222_tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
