package com.example.samsa.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.entity.Cart;
import com.example.samsa.entity.ShopProduct;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<ShopProduct>  {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private boolean showShopName;

    public ProductListAdapter(Context context, List<ShopProduct> shopProductList, boolean showShopName) {
        super(context, R.layout.listview_shop_product, shopProductList);
        this.showShopName = showShopName;

    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final ShopProduct shopProduct = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_shop_product, null);
        }


        ((TextView)view.findViewById(R.id.text_id)).setText(String.valueOf(shopProduct.getId()));
        ((TextView)view.findViewById(R.id.text_product)).setText(String.valueOf(shopProduct.getName_product()));
        ((TextView)view.findViewById(R.id.text_price)).setText(String.valueOf(shopProduct.getPrice()) + " ₽");
        ((TextView)view.findViewById(R.id.text_count)).setText(String.valueOf(shopProduct.getCount()));
        ((TextView) view.findViewById(R.id.text_shop)).setText(String.valueOf(shopProduct.getName_shop()));
        if(showShopName) {
            ((TextView) view.findViewById(R.id.text_shop)).setVisibility(View.GONE);
        }



        ((ImageButton)view.findViewById(R.id.button_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(getContext());
                sqLiteDatabase = databaseHelper.getReadableDatabase();

                Cart cart = new Cart();
                DatabaseHelper.createCartValue(cart, shopProduct, sqLiteDatabase);

                Toast toast = Toast.makeText(getContext(), "успешно добавлено" , Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return view;
    }



}
