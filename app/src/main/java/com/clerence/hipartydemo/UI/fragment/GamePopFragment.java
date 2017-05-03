package com.clerence.hipartydemo.UI.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePopFragment     2017-05-01
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class GamePopFragment extends DialogFragment {

    private static final int GET_SPINNER = 876;
    private CheckBox mCheckBox;
    private Spinner mSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.werwolfchoose_pop,null);
        initView(view);

        AlertDialog dialog = builder
                .setView(view)
                .setNegativeButton("cancel", null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();


        return dialog;
    }

    private void initView(View view) {
        mCheckBox = (CheckBox) view.findViewById(R.id.werchoose_pop_ck_into);
        mSpinner = (Spinner) view.findViewById(R.id.werchoose_pop_ck_mem);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder().add("roomId", BeanLab.getBeanLab().getFromMap("roomId").toString())
                .add("userId",BeanLab.getBeanLab().getUserId()).add("order","getlist");
        Request request = new Request.Builder().url(Constant.HTTPCONTEXT+"game/getlist").post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String msg = response.body().string();
                Message message = new Message();
                message.what = GET_SPINNER;
                message.obj = msg;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_SPINNER){
                String chaterStr = (String) msg.obj;
                List<String> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(chaterStr);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("object");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        list.add((String) jsonObject1.get("nickname"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
               mSpinner.setAdapter(arrayAdapter);
            }
        }
    };
}
