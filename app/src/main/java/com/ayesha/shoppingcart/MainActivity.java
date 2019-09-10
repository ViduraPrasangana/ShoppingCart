package com.ayesha.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bnv;
    private LinearLayout root;
    private MainPageAdapter mainPageAdapter;
    private ViewPager viewPager;
    private Context context;

    public static MainActivity mainActivity;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private static LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        context = this;
        mainActivity = this;
        loadingDialog = new LoadingDialog(this);
        auth = FirebaseAuth.getInstance();
        setupImageLoader();
        checkUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                checkUser();
            }
        };

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
                switch (position) {
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
        viewPager.setOffscreenPageLimit(4);
        loadAllProducts();
    }

    public void setPage(int page){
        viewPager.setCurrentItem(page);
    }

    private void checkUser() {
        if (auth.getCurrentUser() == null) {
            openLogin();
            finish();
        }
    }

    public static void loadAllProducts(){
        loadingDialog.showDialog();
        Constants.fetchProductsFromDB(mainActivity);
        Constants.fetchAllCategoriesFromDB(mainActivity);
    }

    public static void dialogDismiss(){
        loadingDialog.closeDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(authStateListener!=null)
            auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(authStateListener!=null)
            auth.removeAuthStateListener(authStateListener);
    }

    private void openLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

    private void setupImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
    }

}
