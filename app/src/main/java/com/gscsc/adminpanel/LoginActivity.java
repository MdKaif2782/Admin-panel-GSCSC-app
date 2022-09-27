package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Context context;
    private Intent intent;
    private ImageView progressBar;
    private TextInputLayout emailLayout, passwordLayout;
    private int screenWidth, screenHeight;
    private ImageView illustration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        context = this;
        intent = new Intent(context, Option.class);
        emailLayout = findViewById(R.id.textInputLayout4);
        passwordLayout = findViewById(R.id.textInputLayout3);
        emailLayout.setBoxBackgroundColor(getResources().getColor(R.color.TRANSPARENT));
        emailLayout.setBackground(null);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        illustration = findViewById(R.id.imageView);
        passwordLayout.setBoxBackgroundColor(getResources().getColor(R.color.TRANSPARENT));


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            context.startActivity(intent);
            finish();
        }

        resizeImage();
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
    public void resizeImage(){
        int newHeight = (int) (screenWidth * 0.25);
        int imageWidth = illustration.getDrawable().getIntrinsicWidth();
        int newWidth = (int) (newHeight * (imageWidth / illustration.getDrawable().getIntrinsicHeight()));
        //set the new width and height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        illustration.setLayoutParams(params);

    }
}