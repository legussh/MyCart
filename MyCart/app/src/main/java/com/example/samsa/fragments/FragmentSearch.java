package com.example.samsa.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ProductListAdapter;
import com.example.samsa.entity.ShopProduct;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ListView listShopProducts;
    private EditText editTextSearch;

    public FragmentSearch(){
        super(R.layout.fragment_search);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        listShopProducts = (ListView) view.findViewById(R.id.list_shop_products);
        editTextSearch = (EditText) view.findViewById(R.id.edit_text_search);

        databaseHelper = new DatabaseHelper(getActivity());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<ShopProduct> shopProductList = new ArrayList<ShopProduct>();

        if (editTextSearch.getText().toString().equals("")) {
            Cursor cursorShopProduct = sqLiteDatabase.query(ShopProduct.TABLE_NAME, null, null, null, null, null, null);

            if (cursorShopProduct.moveToFirst()) {
                while (!cursorShopProduct.isAfterLast()) {
//                Log.d("hehe", cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
//                        cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
//                        cursorShopProduct.getInt(ShopProduct.NUM_COLUMN_PRICE));

//                shopNames.add(cursorShopProduct.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT));

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
            ProductListAdapter productListAdapter = new ProductListAdapter(getActivity(), shopProductList, false);
            listShopProducts.setAdapter(productListAdapter);
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Cursor cursorSearch = sqLiteDatabase.query(ShopProduct.TABLE_NAME, null, ShopProduct.COLUMN_NAME_PRODUCT + " like ?",
                        new String[]{"%" + editTextSearch.getText().toString() + "%"}, null, null, null);

                List<ShopProduct> searchList = new ArrayList<ShopProduct>();

                if (cursorSearch.moveToFirst()) {
                    while (!cursorSearch.isAfterLast()) {
//                Log.d("hehe", cursorSearch.getInt(ShopProduct.NUM_COLUMN_ID) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_NAME_SHOP) + " " +
//                        cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_COUNT) + " " +
//                        cursorSearch.getInt(ShopProduct.NUM_COLUMN_PRICE));

                        ShopProduct shopProduct = new ShopProduct(cursorSearch.getInt(ShopProduct.NUM_COLUMN_ID),
                                cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_SHOP),
                                cursorSearch.getString(ShopProduct.NUM_COLUMN_NAME_PRODUCT),
                                cursorSearch.getInt(ShopProduct.NUM_COLUMN_COUNT),
                                (cursorSearch.getInt(ShopProduct.NUM_COLUMN_PRICE)),
                                sqLiteDatabase);
                        searchList.add(shopProduct);

                        cursorSearch.moveToNext();
                    }
                }

                ProductListAdapter searchAdapter = new ProductListAdapter(getActivity(), searchList, false);
                listShopProducts.setAdapter(searchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }
}
