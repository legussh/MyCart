package com.example.samsa.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.Shop;
import com.example.samsa.entity.ShopProduct;

import java.util.ArrayList;
import java.util.List;

public class FragmentShop extends Fragment {

    private ListView listShopProducts;
    private ImageView imageView;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    long shop_id;

    public FragmentShop(){
        super(R.layout.fragment_shop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        databaseHelper = new DatabaseHelper(getActivity());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        listShopProducts = view.findViewById(R.id.list_shop_products);
        imageView = view.findViewById(R.id.image_view);

        shop_id = this.getArguments().getLong("shop_id");

//        Toast toast = Toast.makeText(getActivity(), "" + shop_id , Toast.LENGTH_SHORT);
//        toast.show();

        Cursor cursorShop = sqLiteDatabase.rawQuery("select " + Shop.COLUMN_NAME + "," + Shop.COLUMN_RES + " from " + Shop.TABLE_NAME + " where " + Shop.COLUMN_ID + " =? ",
                new String[]{String.valueOf(shop_id)});
        cursorShop.moveToFirst();

        String shop_name = cursorShop.getString(0);
        int shop_res = cursorShop.getInt(1);

        cursorShop.close();

        imageView.setImageResource(shop_res);

        List<ShopProduct> shopProductList = new ArrayList<ShopProduct>();


        Cursor cursorShopProduct = sqLiteDatabase.rawQuery("select * from " + ShopProduct.TABLE_NAME + " where " + ShopProduct.COLUMN_NAME_SHOP + " =? ",
                new String[]{shop_name});

        if (cursorShopProduct.moveToFirst()) {
            while (!cursorShopProduct.isAfterLast()) {
//                Log.d("hehe", cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
//                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE));

                ShopProduct shopProduct = new ShopProduct(cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID),
                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_SHOP),
                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT),
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT),
                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE),
                        sqLiteDatabase);
                shopProductList.add(shopProduct);

                cursorShopProduct.moveToNext();
            }
        }


        ProductListAdapter productListAdapter = new ProductListAdapter(getActivity(), shopProductList, true);
        listShopProducts.setAdapter(productListAdapter);

        sqLiteDatabase.close();
    return view;
    }
}
