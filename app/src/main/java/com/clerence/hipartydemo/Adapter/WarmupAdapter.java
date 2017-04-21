package com.clerence.hipartydemo.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clerence.hipartydemo.Bean.WarmGame;
import com.clerence.hipartydemo.R;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * WarmupAdapter     2017-04-07
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class WarmupAdapter extends BaseAdapter {
    private List<WarmGame> mWarmGames = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    public WarmupAdapter(List<WarmGame> warmGames, Context context) {
        if (warmGames == null){
            mWarmGames = new ArrayList<>();
        }
        if (context!=null){
            mWarmGames = warmGames;
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

    }

    public void addWarmGame(WarmGame warmGame) {
        if (warmGame != null) {
            Logger.d(warmGame.toString());
            mWarmGames.add(warmGame);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        if (mWarmGames!=null){
            mWarmGames.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mWarmGames.size();
    }

    @Override
    public WarmGame getItem(int position) {
        return mWarmGames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mWarmGames.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView == null){
           convertView = mInflater.inflate(R.layout.lv_warmup,parent,false);
           convertView.setTag(new ViewHolder(convertView));
       }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.init(position);
        return convertView;
    }
    class ViewHolder{
        public TextView mLevelTextView,mNameTextView;
        public ImageView mImageView;

        public ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.lv_warmup_iv_counting);
            mLevelTextView = (TextView) view.findViewById(R.id.lv_warmup_levle_tv);
            mNameTextView = (TextView) view.findViewById(R.id.lv_warmup_name_tv);
        }

        public void init(int position){
            if (TextUtils.isEmpty(mWarmGames.get(position).getWarmGameUrl())){
                mImageView.setImageResource(R.drawable.warmup_inform_counting);
            }else{
                Picasso.with(mContext).load(mWarmGames.get(position).getWarmGameUrl()).into(mImageView);
            }
            mLevelTextView.setText(mWarmGames.get(position).getWarmGameLevel()+"星");
            mNameTextView.setText(mWarmGames.get(position).getWarmGameName());
        }



    }
}
