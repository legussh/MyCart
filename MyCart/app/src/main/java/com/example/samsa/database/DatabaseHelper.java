package com.example.samsa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.samsa.R;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.Cart;
import com.example.samsa.entity.Product;
import com.example.samsa.entity.Shop;
import com.example.samsa.entity.ShopProduct;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shops";
    public static final int DB_VERSION = 32;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query_shop = "CREATE TABLE " + Shop.TABLE_NAME + " (" +
                Shop.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Shop.COLUMN_NAME + " TEXT NOT NULL, " +
                Shop.COLUMN_RES + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(query_shop);

        Shop шестёрочка = new Shop(1, "шестёрочка", R.drawable.ic_shop_shesterochka, sqLiteDatabase);
        Shop пикси = new Shop(2, "пикси", R.drawable.ic_shop_piksi, sqLiteDatabase);
        Shop ашот = new Shop(3, "ашот", R.drawable.ic_shop_ashot, sqLiteDatabase);

        String query_product = "CREATE TABLE " + Product.TABLE_NAME + " (" +
                Product.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Product.COLUMN_NAME + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(query_product);

        Product хлеб = new Product(1, "хлеб", sqLiteDatabase);
        Product cabbage = new Product(2, "капуста", sqLiteDatabase);
        Product картошка = new Product(3, "картошка", sqLiteDatabase);
        Product banana = new Product(4, "банан", sqLiteDatabase);
        Product пельмени = new Product(5, "пельмени Сибирская язва", sqLiteDatabase);
        Product pizza = new Product(6, "пицца Как в школьной столовой", sqLiteDatabase);
        Product булочка = new Product(7, "булочка", sqLiteDatabase);

        String query_shop_product = "CREATE TABLE " + ShopProduct.TABLE_NAME + " (" +
                ShopProduct.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                ShopProduct.COLUMN_NAME_SHOP + " INTEGER NOT NULL, " +
                ShopProduct.COLUMN_NAME_PRODUCT + " TEXT NOT NULL, " +
                ShopProduct.COLUMN_COUNT + " INTEGER NOT NULL, " +
                ShopProduct.COLUMN_PRICE + " INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(query_shop_product);

        ShopProduct шестёрочка_хлеб = new ShopProduct(1,"шестёрочка", "хлеб", 30, 45, sqLiteDatabase);
        ShopProduct шестёрочка_булочка = new ShopProduct(2,"шестёрочка", "булочка", 100, 23, sqLiteDatabase);
        ShopProduct пикси_хлеб = new ShopProduct(3,"пикси", "хлеб", 36, 52, sqLiteDatabase);
        ShopProduct пикси_картошка = new ShopProduct(4,"пикси", "картошка", 257, 40, sqLiteDatabase);
        ShopProduct ашот_хлеб = new ShopProduct(5,"ашот", "хлеб", 62, 47, sqLiteDatabase);
        ShopProduct ашот_пельмени = new ShopProduct(6,"ашот", "пельмени", 78, 104, sqLiteDatabase);

        String query_cart = "CREATE TABLE " + Cart.TABLE_NAME + " (" +
                Cart.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Cart.COLUMN_NAME_SHOP + " TEXT NOT NULL, " +
                Cart.COLUMN_NAME_PRODUCT + " TEXT NOT NULL, " +
                Cart.COLUMN_PRICE + " INTEGER NOT NULL, " +
                Cart.COLUMN_COUNT + " INTEGER NOT NULL DEFAULT 1);";

        sqLiteDatabase.execSQL(query_cart);
    }

    public static void createShopValue(Shop shop, SQLiteDatabase sqLiteDatabase) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Shop.COLUMN_ID, shop.getId());
        contentValues.put(Shop.COLUMN_NAME, shop.getName());
        contentValues.put(Shop.COLUMN_RES, shop.getRes());

        sqLiteDatabase.insert(Shop.TABLE_NAME, null, contentValues);
//        sqLiteDatabase.close();
    }

    public static void createProductValue(Product product, SQLiteDatabase sqLiteDatabase) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Product.COLUMN_ID, product.getId());
        contentValues.put(Product.COLUMN_NAME, product.getName());

        sqLiteDatabase.insert(Product.TABLE_NAME, null, contentValues);
//        sqLiteDatabase.close();
    }

    public static void createShopProductValue(ShopProduct shopProduct, SQLiteDatabase sqLiteDatabase) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopProduct.COLUMN_ID, shopProduct.getId());
        contentValues.put(ShopProduct.COLUMN_NAME_SHOP, shopProduct.getName_shop());
        contentValues.put(ShopProduct.COLUMN_NAME_PRODUCT, shopProduct.getName_product());
        contentValues.put(ShopProduct.COLUMN_COUNT, shopProduct.getCount());
        contentValues.put(ShopProduct.COLUMN_PRICE, shopProduct.getPrice());

        sqLiteDatabase.insert(ShopProduct.TABLE_NAME, null, contentValues);
//        sqLiteDatabase.close();
    }

    public static void createCartValue(Cart cart, ShopProduct shopProduct, SQLiteDatabase sqLiteDatabase) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(Cart.COLUMN_NAME_SHOP, shopProduct.getName_shop());
        contentValues.put(Cart.COLUMN_NAME_PRODUCT, shopProduct.getName_product());

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Cart.TABLE_NAME + " where " + Cart.COLUMN_NAME_SHOP + " =? AND " + Cart.COLUMN_NAME_PRODUCT + " =?",
                new String[]{shopProduct.getName_shop(), shopProduct.getName_product()});
        if(cursor.moveToFirst()){
            contentValues.put(Cart.COLUMN_ID, cursor.getInt(Cart.NUM_COLUMN_ID));
            contentValues.put(Cart.COLUMN_PRICE, cursor.getInt(Cart.NUM_COLUMN_PRICE) + shopProduct.getPrice());
            contentValues.put(Cart.COLUMN_COUNT, cursor.getInt(Cart.NUM_COLUMN_COUNT) + 1);
            sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cursor.getInt(Cart.NUM_COLUMN_ID))});
        }
        else {
            contentValues.put(Cart.COLUMN_ID, DatabaseUtils.queryNumEntries(sqLiteDatabase, Cart.TABLE_NAME) + 1);
            contentValues.put(Cart.COLUMN_PRICE, shopProduct.getPrice());
            contentValues.put(Cart.COLUMN_COUNT, cart.getCount() + 1);
        }
        sqLiteDatabase.insert(Cart.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Shop.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ShopProduct.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);
        onCreate(db);
    }
}



