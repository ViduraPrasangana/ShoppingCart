package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jgabrielfreitas.core.BlurImageView;

public class SignupActivity extends AppCompatActivity {
    private BlurImageView bg;
    private MaterialButton btnSignup, btnLogin,reset;
    private TextInputEditText email, firstName, lastName, number, address, password, passwordConfirm;
    private FirebaseAuth auth;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        bg = findViewById(R.id.bg);
        bg.setBlur(1.5f);

        context = this;

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);

        if(auth.getCurrentUser()!=null) Constants.openMain(context);

        btnSignup = findViewById(R.id.btnRegister);
        reset = findViewById(R.id.reset);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllFields();
            }
        });
    }

    private void signup() {
        if(validate()){
            auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Constants.showSnack(reset,"SignUp Complete");
                        auth = FirebaseAuth.getInstance();
                        if(auth.getCurrentUser()!=null){
                            Constants.openMain(context);
                        }
                    }else {
                        Constants.showSnack(reset,"SignUp Failed");
                    }
                }
            });
        }
    }

    private void clearAllFields(){
        email.setText("");
        firstName.setText("");
        lastName.setText("");
        number.setText("");
        address.setText("");
        password.setText("");
        passwordConfirm.setText("");
    }

    private boolean validate() {
        boolean valid = true;
        if (isEmpty(email)) {
            email.setError("Email can't be empty");
            valid = false;
        }
        if (isEmpty(firstName)) {
            firstName.setError("First Name can't be empty");
            valid = false;
        }
        if (isEmpty(lastName)) {
            lastName.setError("Last Name can't be empty");
            valid = false;
        }
        if (isEmpty(number)) {
            number.setError("Mobile number can't be empty");
            valid = false;
        }
        if (isEmpty(address)) {
            address.setError("Address can't be empty");
            valid = false;
        }
        if (isEmpty(password)) {
            password.setError("password can't be empty");
            valid = false;
        }else if(TextUtils.getTrimmedLength(password.getText())<8){
            password.setError("password length should be more than 7");
            valid = false;
        }
        if (isEmpty(passwordConfirm) || ( passwordConfirm.getText()!=null && !passwordConfirm.getText().toString().equals(password.getText().toString()))) {
            passwordConfirm.setError("Password doesn't match");
            valid = false;
        }

        if(valid){
            return true;
        }else {
            return false;
        }
    }

    private boolean isEmpty(TextInputEditText view) {
        if (TextUtils.isEmpty(view.getText())) {
            view.requestFocus();
            return true;
        }
        return false;
    }
}
