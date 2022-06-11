package com.example.samsa.entity;

public class Cart {
    int id;
    String name_shop;
    String name_product;
    int price;
    int count;

    public static final String TABLE_NAME = "cart";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_SHOP = "name_shop";
    public static final String COLUMN_NAME_PRODUCT = "name_product";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_COUNT = "count";


    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME_SHOP = 1;
    public static final int NUM_COLUMN_NAME_PRODUCT = 2;
    public static final int NUM_COLUMN_PRICE = 3;
    public static final int NUM_COLUMN_COUNT = 4;

    public Cart(){

    }

    public Cart (int id, String name_shop, String name_product, int price, int count) {
        this.id = id;
        this.name_shop = name_shop;
        this.name_product = name_product;
        this.price = price;
        this.count = count;
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

    public void setName_shop(String name_shop) {
        this.name_shop = name_shop;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
