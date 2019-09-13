package com.ayesha.shoppingcart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jgabrielfreitas.core.BlurImageView;

public class SignupActivity extends AppCompatActivity {
    private BlurImageView bg;
    private MaterialButton btnSignup, btnLogin, reset;
    private TextInputEditText email, firstName, lastName, number, address, password, passwordConfirm;
    private FirebaseAuth auth;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        bg = findViewById(R.id.bg);
        bg.setBlur(Constants.BLUR_CURRENT_VALUE);
        context = this;

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        number = findViewById(R.id.number);
        address = findViewById(R.id.firstNameEdit);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);

        if (auth.getCurrentUser() != null) Constants.openMain(context);

        btnSignup = findViewById(R.id.btnEdit);
        reset = findViewById(R.id.reset);
        btnLogin = findViewById(R.id.btnSave);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.loginActivity.setBlur(Constants.BLUR_CURRENT_VALUE);
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

        initializeBlur();
    }

    private void signup() {
        if (validate()) {
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Constants.showSnack(reset, "SignUp Complete");
                        auth = FirebaseAuth.getInstance();
                        if (auth.getCurrentUser() != null) {
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + auth.getCurrentUser().getUid() + "/");
                            User currentUser = getUser();
                            if (currentUser == null) {
                                Constants.showSnack(reset, "SignUp failed");
                            } else {
                                dbRef.setValue(currentUser);
                            }
                            Constants.openMain(context);
                        }
                    } else {
                        Constants.showSnack(reset, "SignUp Failed");
                    }
                }
            });
        }
    }

    private void initializeBlur(){
        new ValueAnimator();
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(Constants.BLUR_CURRENT_VALUE, Constants.BLUR_FINAL_VALUE_SIGNUP);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                bg.setBlur(value);
                Constants.BLUR_CURRENT_VALUE = value;
            }
        });
        valueAnimator.setDuration(Constants.ANIMATION_DURATION);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                valueAnimator.start();

            }
        }, 500);
    }

    private User getUser() {
        if (validate()) {
            return new User(
                    email.getText().toString(),
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    number.getText().toString(),
                    address.getText().toString());
        }
        return null;
    }

    private void clearAllFields() {
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
        } else if (TextUtils.getTrimmedLength(password.getText()) < 8) {
            password.setError("password length should be more than 7");
            valid = false;
        }
        if (isEmpty(passwordConfirm) || (passwordConfirm.getText() != null && !passwordConfirm.getText().toString().equals(password.getText().toString()))) {
            passwordConfirm.setError("Password doesn't match");
            valid = false;
        }

        return valid;
    }

    private boolean isEmpty(TextInputEditText view) {
        if (TextUtils.isEmpty(view.getText())) {
            view.requestFocus();
            return true;
        }
        return false;
    }
}
