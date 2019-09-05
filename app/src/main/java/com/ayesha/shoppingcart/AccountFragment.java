package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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

    private MaterialButton btnEdit ;
    private MaterialButton btnSave;
    private MaterialButton btnLogout;

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
        lastNameEdit = view.findViewById(R.id.lastNameEdit);
        emailEdit = view.findViewById(R.id.emailEdit);
        numberEdit = view.findViewById(R.id.numberEdit);
        addrEdit = view.findViewById(R.id.addrEdit);

        btnEdit = view.findViewById(R.id.btnEdit);
        btnSave = view.findViewById(R.id.btnSave);
        btnLogout = view.findViewById(R.id.btnLogout);
        //------------------------------//

        this.btnEditListner(); //btnEdit onClickListner
        this.btnSaveListner(); //btnSave onClickListner

        return view;

    }

    private void btnEditListner(){
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change the visibility of the TextView to INVISIBLE
                firstName.setVisibility(View.INVISIBLE);
                lastName.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                number.setVisibility(View.INVISIBLE);
                addr.setVisibility(View.INVISIBLE);

                //Change the visibility of the EditText to VISIBLE
                firstNameEdit.setVisibility(View.VISIBLE);
                lastNameEdit.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                numberEdit.setVisibility(View.VISIBLE);
                addrEdit.setVisibility(View.VISIBLE);

                //Change the btnSave to enable
                btnSave.setEnabled(true);

                //Change the btnEdit to enable
                btnEdit.setEnabled(false);
                btnLogout.setEnabled(false);
            }
        });
    }

    private void btnSaveListner(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean temp = AccountFragment.this.formCheck();

                if(temp){

                }

                else if((temp)){
                    //Change the btnEdit to enable
                    btnEdit.setEnabled(true);
                    btnEdit.setEnabled(true);

                    //Change the btnSave to disable
                    btnSave.setEnabled(false);


                    //Change the visibility of the TextView to VISIBLE
                    firstName.setVisibility(View.VISIBLE);
                    lastName.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    number.setVisibility(View.VISIBLE);
                    addr.setVisibility(View.VISIBLE);

                    //Change the visibility of the EditText to INVISIBLE
                    firstNameEdit.setVisibility(View.INVISIBLE);
                    lastNameEdit.setVisibility(View.INVISIBLE);
                    emailEdit.setVisibility(View.INVISIBLE);
                    numberEdit.setVisibility(View.INVISIBLE);
                    addrEdit.setVisibility(View.INVISIBLE);

                    //This is a simple Toast Message
                    Constants.showToast(getActivity(),"SAVED");
                }


            }
        });
    }

    private void btnLoginListener(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean formCheck(){
        if (firstNameEdit.getText().length()==0 || lastNameEdit.getText().length()==0 || emailEdit.getText().length()==0 || numberEdit.getText().length()==0 || addrEdit.getText().length()==0){
            Constants.showAlertBox(getActivity(),"EMPTY FIELDS!","FIELDS CANNOT BE EMPTY!");
            return false;

        }

        else if(firstNameEdit.getText().length()<4 || lastNameEdit.getText().length()<4){
            Constants.showAlertBox(getActivity(),"INVALD NAMES!","NAME FIELDS MUST HAVE AT LEAST 4 CHARACTERS!");
            return true;
        }

        else if(!(Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText()).matches())){
            Constants.showAlertBox(getActivity(),"INVALD EMAIL!","ENTER A VALID EMAIL!");
            return true;
        }

        else if((numberEdit.getText().length()!=10) || (Patterns.PHONE.matcher(numberEdit.getText()).matches()  )){
            Constants.showAlertBox(getActivity(),"INVALD PHONE NUMBER!","ENTER A VALID PHONE NUMBER!");
            return true;
        }

        else if(addr.getText().length()<10){
            Constants.showAlertBox(getActivity(),"INVALD ADDRESS!","ADDRESS LENGTH MUST BE GREATER THAN 10 CHARACTERS!");
            return true;
        }

        else{
            return false;
        }
    }
}
