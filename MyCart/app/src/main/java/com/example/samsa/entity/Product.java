package com.example.samsa.entity;

import android.database.sqlite.SQLiteDatabase;

import com.example.samsa.database.DatabaseHelper;

public class Product {
    private int id;
    private String name;

    public static final String TABLE_NAME = "products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME = 1;


    public Product(int id, String name, SQLiteDatabase sqLiteDatabase) {
        this.id = id;
        this.name = name;
        DatabaseHelper.createProductValue(this, sqLiteDatabase);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}


