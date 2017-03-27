package com.clerence.hipartydemo.Utils;

import android.content.Context;
import android.view.View;

import com.clerence.hipartydemo.Service.ShiftListener;

import com.clerence.hipartydemo.UI.LobbyActivity;
import com.clerence.hipartydemo.UI.ModuleActivity;
import com.clerence.hipartydemo.UI.RoomActivity;
import com.orhanobut.logger.Logger;

/**
 * ShifListenerImpl     2017-03-25
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class ShifListenerImpl implements ShiftListener {
    @Override
    public View.OnClickListener getHallListener(final Context context) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LobbyActivity.actionStart(context);
            }
        };
        return listener;
    }

    @Override
    public View.OnClickListener getRoomListener(final Context context) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomActivity.actionStart(context);
            }
        };
        return listener;
    }

    @Override
    public View.OnClickListener getModuelListener(final Context context) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("模块");
                ModuleActivity.actionStart(context);
            }
        };
        return listener;
    }

    @Override
    public View.OnClickListener getMyListener(final Context context) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("我的");
               // LobbyActivity.actionStart(context);
            }
        };
        return listener;
    }
}
