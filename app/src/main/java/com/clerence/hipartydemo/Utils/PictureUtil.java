package com.clerence.hipartydemo.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * PictureUtil     2017-04-26
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class PictureUtil {
    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        // 获取资源图片

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, opt);

    }
}
