package com.clerence.hipartydemo.Bean;

//
import java.io.Serializable;

public class RoomUserDTO implements Serializable{

    private String userId;//用户id
    private String nickname;//昵称
    private int seat;//座位号

    public RoomUserDTO() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
