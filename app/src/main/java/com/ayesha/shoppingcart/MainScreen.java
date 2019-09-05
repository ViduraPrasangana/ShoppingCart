package com.ayesha.shoppingcart;

import androidx.fragment.app.Fragment;

enum MainScreen {
    HOME(new HomeFragment()),
    CATEGORY(new CategoryFragment()),
    CART(new CartFragment()),
    ACCOUNT(new AccountFragment());

    private final Fragment fragment;

    MainScreen(Fragment fr) {
        this.fragment = fr;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
