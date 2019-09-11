package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView logo;

    private Context context;

    static CategoryFragment categoryFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.categories_fragment,container,false);
        categoryFragment = this;
        context = requireContext();
        recyclerView = view.findViewById(R.id.categoryRecyclerLayout);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        toolbar = view.findViewById(R.id.toolbar);
        logo = view.findViewById(R.id.logo);

        return view;
    }

    void loadCategoriesToView(ArrayList<Category> arrayList){
        AdapterCategory adapter = new AdapterCategory(context,arrayList);
        recyclerView.setAdapter(adapter);
    }


}
