package com.ayesha.shoppingcart;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jgabrielfreitas.core.BlurImageView;

public class LoginActivity extends AppCompatActivity {
    private ConstraintLayout root;
    private BlurImageView bg;
    private TextView email, password;
    private MaterialButton btnLogin, btnRegister;
    private LinearLayout emailCon,passwordCon;

    private FirebaseAuth auth;

    static LoginActivity loginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        bg = findViewById(R.id.bg);
        bg.setBlur(Constants.BLUR_CURRENT_VALUE);

        loginActivity = this;

        auth = FirebaseAuth.getInstance();
        checkUser(auth);

        email = findViewById(R.id.email);
        emailCon = findViewById(R.id.linearLayout);
        password = findViewById(R.id.password);
        passwordCon = findViewById(R.id.linearLayout2);
        btnLogin = findViewById(R.id.btnSave);
        btnRegister = findViewById(R.id.btnEdit);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        initializeBlur();
    }

    void setBlur(float i){
        bg.setBlur(i);
    }

    public void openRegister() {
        Pair<View, String> p1 = Pair.create((View) email, "email");
        Pair<View, String> p2 = Pair.create((View) password, "password");
        Pair<View, String> p3 = Pair.create((View) btnLogin, "login");
        Pair<View, String> p4 = Pair.create((View) btnRegister, "register");
        Pair<View, String> p5 = Pair.create((View) bg, "bgImage");
        Pair<View, String> p6 = Pair.create((View) emailCon, "emailCon");
        Pair<View, String> p7 = Pair.create((View) passwordCon, "passwordCon");

        ActivityOptionsCompat option = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5,p6,p7);
        Intent registerIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivityForResult(registerIntent,1, option.toBundle());
    }

    public void checkUser(FirebaseAuth auth){
        if(auth.getCurrentUser()!=null) openMain();
    }

    private void login(){
        if(validate()){
            auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        auth = FirebaseAuth.getInstance();
                        checkUser(auth);
                    }else {
                        Constants.showSnack(btnLogin,"Login Failed");
                    }
                }
            });
        } else {
            Constants.showSnack(btnLogin,"Fill all fields");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initializeBlur();
    }

    private void initializeBlur(){
        new ValueAnimator();
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(Constants.BLUR_CURRENT_VALUE, Constants.BLUR_FINAL_VALUE_LOGIN);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                System.out.println(valueAnimator.getAnimatedValue());
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
        }, 100);
    }


    public void openMain(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public boolean validate(){
        return email.getText() != null &&
                !email.getText().toString().equals("") &&
                password.getText() != null &&
                !password.getText().toString().equals("");
    }
}
