package com.example.samsa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;

import com.example.samsa.database.DatabaseHelper;
import com.example.samsa.R;
import com.example.samsa.fragments.FragmentCart;
import com.example.samsa.fragments.FragmentListShops;
import com.example.samsa.fragments.FragmentSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentListShops fragmentListShops;
    FragmentSearch fragmentSearch;
    FragmentCart fragmentCart;
    FragmentTransaction fragmentTransaction;

    private ListView listShops;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private boolean startedFromCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(0);

        fragmentListShops = new FragmentListShops();
        fragmentCart = new FragmentCart();
        fragmentSearch = new FragmentSearch();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            startedFromCart = bundle.getBoolean("cart");
        }

        if(startedFromCart){
            fragmentManager.beginTransaction().add(R.id.frame_layout, fragmentCart).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.frame_layout, fragmentListShops).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        fragmentTransaction = getFragmentManager().beginTransaction();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        switch (item.getItemId()) {
                            case R.id.page_cart:

                                fragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentCart).commit();
                                break;
                            case R.id.page_search:

                                fragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentSearch).commit();
                                break;
                            case R.id.page_shops:

                                fragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentListShops).commit();
                                break;
                        }
                        return false;
                    }
                });
    }
}
