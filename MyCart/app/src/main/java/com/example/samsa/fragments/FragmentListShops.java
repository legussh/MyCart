package com.example.samsa.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.adapters.ShopListAdapter;
import com.example.samsa.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class FragmentListShops extends Fragment {

    FragmentShop fragmentShop;

    private ListView listShops;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public FragmentListShops() {
        super(R.layout.fragment_shop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_shops, container, false);

        fragmentShop = new FragmentShop();

        databaseHelper = new DatabaseHelper(getActivity());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        listShops = (ListView) view.findViewById(R.id.list_shops);

        List<Shop> shopList = new ArrayList<Shop>();

        Cursor cursorShop = sqLiteDatabase.query(Shop.TABLE_NAME, null, null, null, null, null, null);

        if (cursorShop.moveToFirst()) {
            while (!cursorShop.isAfterLast()) {
//                Log.d("hehe", cursorShop.getInt(Shop.NUM_COLUMN_ID) + " " +
//                        cursorShop.getString(Shop.NUM_COLUMN_NAME) + " " +
//                        cursorShop.getInt(Shop.NUM_COLUMN_RES));

                Shop shop = new Shop(cursorShop.getInt(Shop.NUM_COLUMN_ID),
                        cursorShop.getString(Shop.NUM_COLUMN_NAME),
                        cursorShop.getInt(Shop.NUM_COLUMN_RES), sqLiteDatabase);
                shopList.add(shop);

                cursorShop.moveToNext();
            }
        }

        ShopListAdapter shopListAdapter = new ShopListAdapter(getActivity(), shopList);
        listShops.setAdapter(shopListAdapter);

        listShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                id = id + 1;

//                Toast toast = Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT);
//                toast.show();

                Bundle bundle = new Bundle();
                bundle.putLong("shop_id", id);
                fragmentShop.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentShop).commit();



            }
        });

        return view;
    }
}
