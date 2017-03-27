package com.clerence.hipartydemo.Utils;

/**
 * OrderType     2017-03-20
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public enum OrderType {
    CREATE("create"),IN("in");

    private String order;
    OrderType(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
