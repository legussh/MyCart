package com.example.samsa.entity;

import android.database.sqlite.SQLiteDatabase;

import com.example.samsa.database.DatabaseHelper;

public class ShopProduct {
    int id;
    String name_shop;
    String name_product;
    int count;
    int price;

    public static final String TABLE_NAME = "shops_products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_SHOP = "name_shop";
    public static final String COLUMN_NAME_PRODUCT = "name_product";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_PRICE = "price";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME_SHOP = 1;
    public static final int NUM_COLUMN_NAME_PRODUCT = 2;
    public static final int NUM_COLUMN_COUNT = 3;
    public static final int NUM_COLUMN_PRICE = 4;

    public ShopProduct(int id, String name_shop, String name_product, int count, int price, SQLiteDatabase sqLiteDatabase) {
        this.id = id;
        this.name_shop = name_shop;
        this.name_product = name_product;
        this.count = count;
        this.price = price;

        DatabaseHelper.createShopProductValue(this, sqLiteDatabase);
    }

    public ShopProduct(int id, int count, int price) {
        this.id = id;
        this.count = count;
        this.price = price;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_shop() {
        return name_shop;
    }

    public void setName_shop(int id_shop) {
        this.name_shop = name_shop;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
