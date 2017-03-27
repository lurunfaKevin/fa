package com.clerence.hipartydemo.UI.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.clerence.hipartydemo.R;
import com.clerence.hipartydemo.Service.JoinRoomInterface;

/**
 * RoomPopFragment     2017-03-27
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class RoomPopFragment extends DialogFragment {


    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.introduce_pop,null);
        mEditText = (EditText) view.findViewById(R.id.introduce_pop_et_introduce);
        AlertDialog dialog = builder
                .setTitle("请在30秒内输入自我介绍")
                .setView(view)
                .setNegativeButton("cancel", null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JoinRoomInterface joinRoomInterface = (JoinRoomInterface) getActivity();
                        joinRoomInterface.onFinish(mEditText.getText().toString());
                    }
                })
                .create();


        return dialog;
    }
}
