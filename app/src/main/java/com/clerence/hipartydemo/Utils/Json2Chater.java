package com.clerence.hipartydemo.Utils;

import com.clerence.hipartydemo.Bean.Chater;
import com.google.gson.Gson;


/**
 * Json2Chater     2017-03-20
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class Json2Chater {
    public static Chater json2Chater(String json){
        return new Gson().fromJson(json,Chater.class);
    }

    public static String chater2Json(Chater chater){
        return new Gson().toJson(chater);
    }
}
