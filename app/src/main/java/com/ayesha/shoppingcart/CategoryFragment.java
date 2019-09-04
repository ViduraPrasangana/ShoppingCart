package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
    //Categories!//
    //--Frozen food
    //--Grocery
    //--Vegetables
    //--Fruits
    //--Meat
    //--Fish
    //--Liquor
    //--Chilled
    Category FrozenFood = new Category(0,)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories_fragment,container,false);
    }
}
