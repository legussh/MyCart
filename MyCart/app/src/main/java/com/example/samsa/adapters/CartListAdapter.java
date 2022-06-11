package com.example.samsa.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.activity.MainActivity;
import com.example.samsa.entity.Cart;
import com.example.samsa.entity.Shop;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<Cart> {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private TextView textViewProduct;
    private TextView textViewCount;
    private TextView textViewPrice;
    private ImageButton buttonDelete;
    private ImageButton buttonAdd;

    private String previousShop = " ";

    public CartListAdapter(Context context, List<Cart> cart) {
        super(context, R.layout.listview_cart, cart);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final Cart cart = getItem(position);


        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_cart, null);
        }

        Log.d("hehehe", position + "");

        databaseHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        textViewProduct = view.findViewById(R.id.text_product);
        textViewCount = view.findViewById(R.id.text_count);
        textViewPrice = view.findViewById(R.id.text_price);
        buttonDelete = view.findViewById(R.id.button_delete);
        buttonAdd = view.findViewById(R.id.button_add);

        String currentShop = cart.getName_shop();

        Log.d("hehe!!!!", currentShop + " " + previousShop);

        ((ImageView)view.findViewById(R.id.image_shop)).setVisibility(View.GONE);

        if(!currentShop.equals( previousShop) ||
                DatabaseUtils.queryNumEntries(sqLiteDatabase, Cart.TABLE_NAME) == 1) {

            Cursor res =  sqLiteDatabase.rawQuery("select " + Shop.COLUMN_RES + " from " + Shop.TABLE_NAME +
                    " where " + Shop.COLUMN_NAME + " =? ", new String[]{currentShop});
            res.moveToFirst();

            ((ImageView)view.findViewById(R.id.image_shop)).setImageResource(res.getInt(0));
            ((ImageView)view.findViewById(R.id.image_shop)).setVisibility(View.VISIBLE);
        } else if(sqLiteDatabase.rawQuery("select distinct " + Cart.COLUMN_NAME_SHOP + " from " + Cart.TABLE_NAME, null).getCount() == 1 &
                DatabaseUtils.queryNumEntries(sqLiteDatabase, Cart.TABLE_NAME) != 1){

            Cursor res =  sqLiteDatabase.rawQuery("select " + Shop.COLUMN_RES + " from " + Shop.TABLE_NAME +
                    " where " + Shop.COLUMN_NAME + " =? ", new String[]{currentShop});
            res.moveToFirst();
            currentShop = " ";

        }
        previousShop = currentShop;

        textViewProduct.setText(String.valueOf(cart.getName_product()));
        textViewPrice.setText(String.valueOf(cart.getPrice()  + " ₽"));
        textViewCount.setText(String.valueOf(cart.getCount() + " шт"));

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper = new DatabaseHelper(getContext());
                sqLiteDatabase = databaseHelper.getReadableDatabase();

//                cart.setPrice(cart.getPrice() - cart.getPrice()/cart.getCount());
//                cart.setCount(cart.getCount() - 1);

                ContentValues contentValues = new ContentValues();
                contentValues.put(Cart.COLUMN_ID, cart.getId());
                contentValues.put(Cart.COLUMN_NAME_PRODUCT, cart.getName_product());
                contentValues.put(Cart.COLUMN_NAME_SHOP, cart.getName_shop());
                contentValues.put(Cart.COLUMN_COUNT, cart.getCount() - 1);
                contentValues.put(Cart.COLUMN_PRICE, cart.getPrice() - cart.getPrice()/cart.getCount());



                if(cart.getCount() - 1 > 0) {
                    sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cart.getId())});

                    sqLiteDatabase.insert(Cart.TABLE_NAME, null, contentValues);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("cart", true);
                    getContext().startActivity(intent);
                } else{
                    sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cart.getId())});
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("cart", true);
                    getContext().startActivity(intent);
                }
                Log.d("CART_LIST_ADAPTER", String.valueOf(cart.getCount()));

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper = new DatabaseHelper(getContext());
                sqLiteDatabase = databaseHelper.getReadableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Cart.COLUMN_ID, cart.getId());
                contentValues.put(Cart.COLUMN_NAME_PRODUCT, cart.getName_product());
                contentValues.put(Cart.COLUMN_NAME_SHOP, cart.getName_shop());
                contentValues.put(Cart.COLUMN_COUNT, cart.getCount() + 1);
                contentValues.put(Cart.COLUMN_PRICE, cart.getPrice() + cart.getPrice()/cart.getCount());

                sqLiteDatabase.delete(Cart.TABLE_NAME,"id =?", new String[]{String.valueOf(cart.getId())});
                sqLiteDatabase.insert(Cart.TABLE_NAME, null, contentValues);

                Cursor cursor = sqLiteDatabase.query(Cart.TABLE_NAME, null, Cart.COLUMN_ID + "=?", new String[]{String.valueOf(cart.getId())},null,null, null);
                cursor.moveToFirst();

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("cart", true);
                getContext().startActivity(intent);

                Log.d("CART_LIST_ADAPTER", String.valueOf(cart.getCount()));

            }
        });

        sqLiteDatabase.close();
        return view;
    }
}
