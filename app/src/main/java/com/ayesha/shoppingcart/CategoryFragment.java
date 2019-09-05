package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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


    private ArrayList<Category>categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.categories_fragment,container,false);
        initcategories(view);
        return view;
    }

    private void initcategories(View view){
        this.categories = Constants.getCategoryArrayList();
        this.initRecycleView(view);
    }

    private void initRecycleView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerLayout);
        RecyclerViewAdapterCategory adapter = new RecyclerViewAdapterCategory(getActivity(),categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
