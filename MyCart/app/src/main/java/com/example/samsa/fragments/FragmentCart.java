package com.example.samsa.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.CartListAdapter;
import com.example.samsa.entity.Cart;

import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends Fragment {

    private ListView listView;
    private TextView finalSum;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public FragmentCart() {
        super(R.layout.fragment_cart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        listView = (ListView) view.findViewById(R.id.list_cart);
        finalSum = (TextView) view.findViewById(R.id.text_view_sum);

        databaseHelper = new DatabaseHelper(getActivity());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Cart> cartList = new ArrayList<Cart>();

        Cursor cursorCart = sqLiteDatabase.query(Cart.TABLE_NAME, null, null, null, null, null, Cart.COLUMN_NAME_SHOP);

        if (cursorCart.moveToFirst()) {
            while (!cursorCart.isAfterLast()) {
                Log.d("hehe", cursorCart.getInt(Cart.NUM_COLUMN_ID) + " " +
                        cursorCart.getString(Cart.NUM_COLUMN_NAME_SHOP) + " " +
                        cursorCart.getString(Cart.NUM_COLUMN_NAME_PRODUCT) + " " +
                        cursorCart.getInt(Cart.NUM_COLUMN_PRICE) + " " +
                        cursorCart.getInt(Cart.NUM_COLUMN_COUNT));

                Cart cart = new Cart(cursorCart.getInt(Cart.NUM_COLUMN_ID),
                        cursorCart.getString(Cart.NUM_COLUMN_NAME_SHOP),
                        cursorCart.getString(Cart.NUM_COLUMN_NAME_PRODUCT),
                        cursorCart.getInt(Cart.NUM_COLUMN_PRICE),
                        cursorCart.getInt(Cart.NUM_COLUMN_COUNT));

                cartList.add(cart);

                cursorCart.moveToNext();
            }
        }

        CartListAdapter cartListAdapter = new CartListAdapter(getActivity(), cartList);
        listView.setAdapter(cartListAdapter);

        Cursor cursorSum = sqLiteDatabase.rawQuery("select SUM(" + Cart.COLUMN_PRICE + ") from " + Cart.TABLE_NAME, null);
        cursorSum.moveToFirst();
        finalSum.setText("Итоговая стоимость: " + cursorSum.getString(0));

        if(cursorSum.getString(0) == null){
            finalSum.setText("Итоговая стоимость: 0");
        }

        sqLiteDatabase.close();

        return view;
    }
}
