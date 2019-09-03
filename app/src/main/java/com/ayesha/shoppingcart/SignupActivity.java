package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jgabrielfreitas.core.BlurImageView;

public class SignupActivity extends AppCompatActivity {
    private BlurImageView bg;
    private MaterialButton btnSignup, btnLogin;
    private TextInputEditText email, firstName, lastName, number, address, password, passwordConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        bg = findViewById(R.id.bg);
        bg.setBlur(1.5f);

        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);

        btnSignup = findViewById(R.id.btnRegister);
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
    }

    private void signup() {
        validate();
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
