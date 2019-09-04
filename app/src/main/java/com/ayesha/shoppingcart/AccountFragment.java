package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class AccountFragment extends Fragment {

    private User user; //User object that will show

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView number;
    private TextView addr;

    private TextInputEditText firstNameEdit;
    private TextInputEditText lastNameEdit;
    private TextInputEditText emailEdit;
    private TextInputEditText numberEdit;
    private TextInputEditText addrEdit;

    private MaterialButton btnLogin ;
    private MaterialButton btnRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_fragment,container,false);

        //Finding components from the view
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        addr = view.findViewById(R.id.addr);

        firstNameEdit = view.findViewById(R.id.firstNameEdit);
        lastNameEdit = view.findViewById(R.id.firstNameEdit);
        emailEdit = view.findViewById(R.id.firstNameEdit);
        numberEdit = view.findViewById(R.id.firstNameEdit);
        addrEdit = view.findViewById(R.id.firstNameEdit);




        return view;

    }
    
}
