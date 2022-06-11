package com.example.samsa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsa.R;
import com.example.samsa.entity.Shop;

import java.util.List;

public class ShopListAdapter extends ArrayAdapter<Shop> {

    public ShopListAdapter(Context context, List<Shop> shops) {
        super(context, R.layout.listview_shops, shops);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final Shop shop = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_shops, null);
        }

        ((ImageView) view.findViewById(R.id.image_shop)).setImageResource(shop.getRes());

        return view;
    }
}
