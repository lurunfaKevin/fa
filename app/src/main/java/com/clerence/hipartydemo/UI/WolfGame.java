package com.clerence.hipartydemo.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.clerence.hipartydemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * WolfGame     2017-04-26
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class WolfGame extends AppCompatActivity {

    private Spinner mGodSpinner,mWolfSpinner;
    private CheckBox mWitchCK;
    private CheckBox mSeerCK;
    private CheckBox mCupCK;
    private CheckBox mTheifCK;
    private CheckBox mOccupCK;
    private CheckBox mHunterCK;
    private CheckBox mIdoitCK;
    private CheckBox mGrilCK;
    private CheckBox mOlderCK;
    private CheckBox mPolicCK;
    private Map<String,Boolean> map = new HashMap<>();
    private String God;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.werwolfchoose);
        initView();
    }

    private void initView() {

        mGodSpinner = (Spinner) findViewById(R.id.werwolfchoose_sp_god);
        mWolfSpinner = (Spinner) findViewById(R.id.werwolfchoose_sp_wolf);
        Integer[] ints = new Integer[]{1,2,3,4,5,6,7,8};
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, ints);
        mWolfSpinner.setAdapter(integerArrayAdapter);
        mWitchCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_witch);
        mWitchCK.setOnCheckedChangeListener(new myCKListener("witch"));
        mSeerCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_seer);
        mSeerCK.setOnCheckedChangeListener(new myCKListener("seer"));
        mCupCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_cupid);
        mCupCK.setOnCheckedChangeListener(new myCKListener("cupid"));
        mTheifCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_theif);
        mTheifCK.setOnCheckedChangeListener(new myCKListener("theif"));
        mOccupCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_occup);
        mOccupCK.setOnCheckedChangeListener(new myCKListener("occup"));
        mHunterCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_hunter);
        mHunterCK.setOnCheckedChangeListener(new myCKListener("hunter"));
        mIdoitCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_idoit);
        mIdoitCK.setOnCheckedChangeListener(new myCKListener("idoit"));
        mGrilCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_girl);
        mGrilCK.setOnCheckedChangeListener(new myCKListener("girl"));
        mOlderCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_older);
        mOlderCK.setOnCheckedChangeListener(new myCKListener("older"));
        mPolicCK = (CheckBox) findViewById(R.id.werwolfchoose_cb_polic);
        mPolicCK.setOnCheckedChangeListener(new myCKListener("polic"));
        mButton = (Button) findViewById(R.id.startgame_bt);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class myCKListener implements CompoundButton.OnCheckedChangeListener{

        String name;
        public myCKListener(String name) {
            this.name = name;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(name,isChecked);
        }
    }
}
