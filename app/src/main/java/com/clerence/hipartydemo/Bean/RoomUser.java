package com.clerence.hipartydemo.Bean;

/**
 * RoomUser     2017-04-16
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class RoomUser {
    private String userId;
    private String nickname;
    private int seat;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
