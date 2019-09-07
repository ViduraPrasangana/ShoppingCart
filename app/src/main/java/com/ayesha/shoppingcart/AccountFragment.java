package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    public static User user ; //User object that will show
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

    public static AccountFragment accountFragment;


    @Override
    public void onStart() {

        super.onStart();
        //Constants.fetchTheCurrentUser();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_fragment,container,false);


        accountFragment = this;
        firstName = view.findViewById(R.id.firstName);
        //firstName.setText(user.getEmail()+"assasaasa");
        //Log.d("HIIIiiIIIIII",this.user.getEmail());
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

        setUser();

        user = new User(addr.getText().toString(),email.getText().toString(),firstName.getText().toString(),lastName.getText().toString(),number.getText().toString());
        //------------------------------//

        this.btnEditListner(); //btnEdit onClickListner
        this.btnSaveListner(); //btnSave onClickListner
        this.btnLogoutListener(); //btnLogout onClickListner

        return view;

    }

    void loadUser(){
        this.user = Constants.user;
    }

    static void setUser(){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/");
        userRef.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> tempUser = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if(snapshot.getValue(User.class)!=null) {
                        tempUser.add(snapshot.getValue(User.class));
                    }
                }
                //AccountFragment.this.setUser(userTemp);
                user = tempUser.get(0);
                AccountFragment.accountFragment.firstName.setText(user.getFirstName());
                AccountFragment.accountFragment.lastName.setText(user.getLastName());
                AccountFragment.accountFragment.email.setText(user.getEmail());
                AccountFragment.accountFragment.number.setText(user.getNumber());
                AccountFragment.accountFragment.addr.setText(user.getAddress());

                AccountFragment.accountFragment.firstNameEdit.setText(user.getFirstName());
                AccountFragment.accountFragment.lastNameEdit.setText(user.getLastName());
                AccountFragment.accountFragment.emailEdit.setText(user.getEmail());
                AccountFragment.accountFragment.numberEdit.setText(user.getNumber());
                AccountFragment.accountFragment.addrEdit.setText(user.getAddress());

                Log.d("HIIIIIIIII",user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Constants.showAlertBox(AccountFragment.accountFragment.getActivity(),"Database Error!","There is a database Error \n Plaese Check Your Internet Connection and Try Again!");
            }
        });
    }

//    private static void setUser(User user){
//        AccountFragment.user = user;
//    }




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
                Log.d("HIIIIIIIIII",Boolean.toString(temp));

                if(temp){
                    Log.d("HIIIIIIIIII22222",btnSave.toString());
                }

                else if(!(temp)){

                    Log.d("HIIIIIIIIII333333333",btnSave.toString());

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

                    updateUser(firstNameEdit.getText().toString(),lastNameEdit.getText().toString(),emailEdit.getText().toString(),numberEdit.getText().toString(),addrEdit.getText().toString());

                    //String firstName, String lastName, String email, String number, String addr

                    //This is a simple Toast Message
                    Constants.showToast(getActivity(),"SAVED");
                }


            }
        });
    }

    private void btnLogoutListener(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getActivity(),LoginActivity.class));
                Constants.showToast(getActivity(),"Logout Successfull!");

            }
        });
    }

    private void updateUser(String firstName, String lastName, String email, String number, String addr) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNumber(number);
        user.setAddress(addr);

        dbRef.setValue(user);

        setUser();
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

        else if(numberEdit.getText().length()!=10){
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
