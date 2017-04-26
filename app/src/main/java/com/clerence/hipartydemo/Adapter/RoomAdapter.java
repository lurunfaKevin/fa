package com.clerence.hipartydemo.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clerence.hipartydemo.Bean.BeanLab;
import com.clerence.hipartydemo.Bean.Chater;
import com.clerence.hipartydemo.Bean.Constant;
import com.clerence.hipartydemo.R;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * RoomAdapter     2017-03-25
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class RoomAdapter extends BaseAdapter {

    private List<Chater> mDatas;
    private String mUserId;
    private LayoutInflater mLayoutInflater;
    private Handler mHandler;

    public RoomAdapter(Context context,Handler handler) {
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = BeanLab.getBeanLab().getChaters();
        mUserId = BeanLab.getBeanLab().getUserId();
        mHandler = handler;
    }

    public void addChater(Chater chater) {
        if (chater != null) {
            Logger.d("add");
            Logger.d(chater.toString());
            BeanLab.getBeanLab().getChaters().add(chater);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.lv_room1, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.init(position);
        return convertView;
    }

    class ViewHolder {
        private TextView tvRight, tvLeft, tvTime, tvUser,tvRightName,tvLeftName;
        private LinearLayout leftLayout, rightLayout, midLayout;
        private ImageView mImageViewLeft,mImageViewRight;

        private ViewHolder(View view) {
            tvRight = (TextView) view.findViewById(R.id.lvroom_tv_right_dialog);
            tvLeft = (TextView) view.findViewById(R.id.lvroom_tv_left_dialog);
            tvTime = (TextView) view.findViewById(R.id.lvroom_tv_time);
            tvUser = (TextView) view.findViewById(R.id.lvroom_tv_prompt);
            tvLeftName = (TextView) view.findViewById(R.id.lvroom_tv_left_name);
            tvRightName = (TextView) view.findViewById(R.id.lvroom_tv_right_name);
            tvRight.setBackground(view.getResources().getDrawable(R.drawable.dialogright));
            tvLeft.setBackground(view.getResources().getDrawable(R.drawable.dialogleft));

            leftLayout = (LinearLayout) view.findViewById(R.id.frame_second);
            midLayout = (LinearLayout) view.findViewById(R.id.frame_first);
            rightLayout = (LinearLayout) view.findViewById(R.id.frame_third);

            mImageViewLeft = (ImageView) view.findViewById(R.id.lvroom_iv_left_head);
            mImageViewRight = (ImageView) view.findViewById(R.id.lvroom_iv_right_head);
        }

        private void init(int position) {
            mImageViewLeft.setImageResource(R.drawable.lvroom_lefthead);
            mImageViewRight.setImageResource(R.drawable.lvroom_righthead);
            Logger.d("init");
            Chater chater = mDatas.get(position);
            if (position == 0) {
                midLayout.setVisibility(View.VISIBLE);
                tvTime.setText(chater.getDate());
            } else {
                midLayout.setVisibility(View.GONE);
            }
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.GONE);

            if (chater.getOrder().equals("out")) {
                midLayout.setVisibility(View.VISIBLE);
                tvUser.setVisibility(View.VISIBLE);
                tvUser.setText(chater.getUserId().equals(mUserId) ? "你已经退出房间" : chater.getMessage
                        ());
            } else if (chater.getOrder().equals("talk_in")) {

                mHandler.sendEmptyMessage(99);
                midLayout.setVisibility(View.VISIBLE);
                tvUser.setVisibility(View.VISIBLE);
                tvUser.setText(chater.getUserId().equals(mUserId) ? "你已经加入了房间" : chater
                        .getMessage());
            } else if (chater.getOrder().equals("rank")) {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);


                leftLayout.setVisibility(View.VISIBLE);
                tvLeftName.setText("系统");
                tvLeft.setVisibility(View.VISIBLE);
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String rankMessage = (String) map.get("rank");
                tvLeft.setText(rankMessage);
            }  else if (chater.getOrder().equals("ensure_punishment")) {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                leftLayout.setVisibility(View.VISIBLE);
                tvLeftName.setText("系统");
                tvLeft.setVisibility(View.VISIBLE);
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String rankMessage = (String) map.get("punishment");
                tvLeft.setText(rankMessage);
            } else if (chater.getOrder().equals("ensure_introduce")) {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                leftLayout.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                tvLeftName.setText("系统");
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String rankMessage = (String) map.get("introduction");//需要确认
                tvLeft.setText(rankMessage);
            }else if(chater.getOrder().equals(Constant.Order.ensure_warmgame.name())){
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                leftLayout.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                tvLeftName.setText("系统");
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String warmgame = (String) map.get("warmgame");
                tvLeft.setText(warmgame);
            }else {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                if (chater.getUserId().equals(mUserId)) {
                    rightLayout.setVisibility(View.VISIBLE);
                    tvRightName.setText(BeanLab.getBeanLab().getUserName()==null?"你":BeanLab.getBeanLab().getUserName());
                    tvRight.setVisibility(View.VISIBLE);
                    tvRight.setText(chater.getMessage());
                } else {
                    leftLayout.setVisibility(View.VISIBLE);
                    tvLeft.setVisibility(View.VISIBLE);
                    Map<String, Object> map = (Map<String, Object>) chater.getObject();
                    String nickname = (String) map.get("nickname");
                    tvLeftName.setText(nickname);
                    tvLeft.setText(chater.getMessage());
                }
            }
        }
    }
}
