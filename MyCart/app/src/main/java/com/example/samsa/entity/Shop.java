package com.example.samsa.entity;

import android.database.sqlite.SQLiteDatabase;

import com.example.samsa.database.DatabaseHelper;

public class Shop {
    private int id;
    private String name;
    private int res;

    public static final String TABLE_NAME = "shops";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RES = "res";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME = 1;
    public static final int NUM_COLUMN_RES = 2;

    public Shop(int id, String name, int res, SQLiteDatabase sqLiteDatabase) {
        this.id = id;
        this.name = name;
        this.res = res;
        DatabaseHelper.createShopValue(this, sqLiteDatabase);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRes() {
        return res;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRes(int res) {
        this.res = res;
    }
}

