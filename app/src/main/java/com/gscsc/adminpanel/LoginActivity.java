package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    }
    public void login(View v){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(4f);
        circularProgressDrawable.setCenterRadius(35f);
        circularProgressDrawable.setColorFilter(getResources().getColor(R.color.purple_500), android.graphics.PorterDuff.Mode.SRC_IN);
        circularProgressDrawable.start();



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
                    context.startActivities(new Intent[]{intent});
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
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
        //wait for 3 second
        Log.d("LoginActivity", "loginAnimation: Clicked");
        login_text.setVisibility(View.GONE);
        loading_anim.setVisibility(View.VISIBLE);
        loading_anim.setAnimation(R.raw.loading);
        loading_anim.playAnimation();
        //wait for 3 second
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        success.setVisibility(View.VISIBLE);
                        loading_anim.cancelAnimation();
                        loading_anim.setAnimation(R.raw.success);
                        loading_anim.playAnimation();
                    }
                },
                3000);




    }
}