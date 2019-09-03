package com.ayesha.shoppingcart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;


public class MainPageAdapter extends FragmentStatePagerAdapter {
    private List<MainScreen> screens = Arrays.asList(MainScreen.values());
    public MainPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return screens.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return screens.size();
    }


}
