package com.gscsc.adminpanel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Context context;
    private Intent intent;
    private ImageView progressBar;
    private TextInputLayout emailLayout, passwordLayout;
    private int screenWidth;
    private ImageView illustration;
    private View success;
    private View failure;
    private TextView login_text;
    private LottieAnimationView loading_anim;
    private ConstraintLayout login_button_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        success = findViewById(R.id.success);
        failure = findViewById(R.id.failure);
        login_text = findViewById(R.id.login_text);
        loading_anim = findViewById(R.id.loading_anim);
        login_button_layout = findViewById(R.id.login_button_layout);
        

        context = this;
        intent = new Intent(context, Option.class);;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        illustration = findViewById(R.id.login_illus);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            context.startActivity(intent);
            finish();
        }
        resizeImage(screenHeight);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateView();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               updateView();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void login(View v){
        Log.d("LoginActivity", "loginAnimation: Clicked");
        login_text.setVisibility(View.GONE);
        loading_anim.setVisibility(View.VISIBLE);
        loading_anim.setAnimation(R.raw.loading);
        loading_anim.setRepeatCount(0);
        loading_anim.playAnimation();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                    System.out.println("Login Successful");
                    login_button_layout.setBackground(null);
                    success.setVisibility(View.VISIBLE);
                    success.setAnimation(AnimationUtils.loadAnimation(context, R.anim.text_alpha));
                    loading_anim.cancelAnimation();
                    loading_anim.setAnimation(R.raw.success);
                    loading_anim.playAnimation();
                    loading_anim.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //wait for 1 second
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            context.startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                    login_button_layout.setBackground(null);
                    login_button_layout.setClickable(false);
                    failure.setVisibility(View.VISIBLE);
                    failure.setAnimation(AnimationUtils.loadAnimation(context, R.anim.text_alpha));
                    loading_anim.cancelAnimation();
                    loading_anim.setAnimation(R.raw.failed);
                    loading_anim.playAnimation();
                    System.out.println("Login Failed");
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void resizeImage(int height) {
        int newHeight = (int) (height * 0.32);

        //dp to px
        int imageWidth = (int) (215 * getResources().getDisplayMetrics().density);
        int newWidth = (int) (newHeight * (imageWidth / (251 * getResources().getDisplayMetrics().density)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        illustration.setLayoutParams(params);
    }


    public void loginAnimation(View view) {
        Boolean isLogin = false;
        //wait for 3 second
        Log.d("LoginActivity", "loginAnimation: Clicked");
        login_text.setVisibility(View.GONE);
        loading_anim.setVisibility(View.VISIBLE);
        loading_anim.setAnimation(R.raw.loading);
        loading_anim.setRepeatCount(0);
        loading_anim.playAnimation();
        //wait for 3 second
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(isLogin){
                            login_button_layout.setBackground(null);
                            success.setVisibility(View.VISIBLE);
                            success.setAnimation(AnimationUtils.loadAnimation(context, R.anim.text_alpha));
                            loading_anim.cancelAnimation();
                            loading_anim.setAnimation(R.raw.success);
                            loading_anim.playAnimation();
                        }else {
                            login_button_layout.setBackground(null);
                            login_button_layout.setClickable(false);
                            failure.setVisibility(View.VISIBLE);
                            failure.setAnimation(AnimationUtils.loadAnimation(context, R.anim.text_alpha));
                            loading_anim.cancelAnimation();
                            loading_anim.setAnimation(R.raw.failed);
                            loading_anim.playAnimation();
                        }

                    }
                },
                3000);
    }
    public void updateView() {
        loading_anim.cancelAnimation();
        loading_anim.setVisibility(View.GONE);
        login_text.setVisibility(View.VISIBLE);
        login_button_layout.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
        login_button_layout.setClickable(true);
        success.setVisibility(View.GONE);
        failure.setVisibility(View.GONE);
    }
}