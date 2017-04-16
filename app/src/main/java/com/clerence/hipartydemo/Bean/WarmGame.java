package com.clerence.hipartydemo.Bean;

import java.io.Serializable;

/**
 * WarmGame     2017-04-07
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class WarmGame implements Serializable{

    private String warmGameName;
    private String warmGameLevel;
    private String warmGameId;
    private String warmGame;
    private String warmGameUrl;
    private int id;

    public String getWarmGameName() {
        return warmGameName;
    }

    public void setWarmGameName(String warmGameName) {
        this.warmGameName = warmGameName;
    }

    public String getWarmGameLevel() {
        return warmGameLevel;
    }

    public void setWarmGameLevel(String warmGameLevel) {
        this.warmGameLevel = warmGameLevel;
    }

    public String getWarmGameId() {
        return warmGameId;
    }

    public void setWarmGameId(String warmGameId) {
        this.warmGameId = warmGameId;
    }

    public String getWarmGame() {
        return warmGame;
    }

    public void setWarmGame(String warmGame) {
        this.warmGame = warmGame;
    }

    public String getWarmGameUrl() {
        return warmGameUrl;
    }

    public void setWarmGameUrl(String warmGameUrl) {
        this.warmGameUrl = warmGameUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
