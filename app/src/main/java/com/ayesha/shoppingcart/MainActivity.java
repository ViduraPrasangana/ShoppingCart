package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bnv;
    private LinearLayout root;
    private MainPageAdapter mainPageAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        root = findViewById(R.id.root);
        bnv = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

        bnv.setOnNavigationItemSelectedListener(this);
        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        viewPager.setAdapter(mainPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bnv.setSelectedItemId(R.id.homeNav);
                        break;
                    case 1:
                        bnv.setSelectedItemId(R.id.categoriesNav);
                        break;
                    case 2:
                        bnv.setSelectedItemId(R.id.cartNav);
                        break;
                    case 3:
                        bnv.setSelectedItemId(R.id.accountNav);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeNav:
                viewPager.setCurrentItem(0);
                break;
            case R.id.categoriesNav:
                viewPager.setCurrentItem(1);
                break;
            case R.id.cartNav:
                viewPager.setCurrentItem(2);
                break;
            case R.id.accountNav:
                viewPager.setCurrentItem(3);
                break;
        }
        return true;
    }

}
