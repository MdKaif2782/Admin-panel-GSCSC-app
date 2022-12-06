package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.gscsc.adminpanel.JavaMailAPI.JavaMailAPI;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import static java.lang.Boolean.TRUE;

public class Invite extends AppCompatActivity {
    private TextView serial;
    private EditText email;
    private Context context;
    private int serialNumber;
    private CheckBox checkBox;
    private int screenWidth;
    private ImageView illustration;
    private Button invite;
    private Boolean isLongPressed = false;
    private String emailAddress;
    private LottieAnimationView loading_anim;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        context = this;
        email = findViewById(R.id.email_input);
        serial = findViewById(R.id.serial_number);
        checkBox = findViewById(R.id.checkbox);
        loading_anim = findViewById(R.id.loading_anim);
        illustration = findViewById(R.id.login_illustration);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        findViewById(R.id.submit_invite).setOnLongClickListener(v->{onLongClick(v); return true;});
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        resizeImage(screenHeight);
        updateSerial();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onInviteButtonPressed(View view) {
            emailAddress = email.getText().toString();
            Boolean isAValidEmail = email.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$");
            AtomicReference<Boolean> emailIsInDatabase = new AtomicReference<>(false);
            Log.d("Invite", "Button Pressed");
            emailAddress = email.getText().toString();
            if (checkBox.isChecked()) {
                String emailText = email.getText().toString();
                if (emailText.isEmpty()) {
                    System.out.println("Email is empty");
                    Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (isAValidEmail) {
                        view.setVisibility(View.INVISIBLE);
                        loading_anim.setVisibility(View.VISIBLE);
                        loading_anim.playAnimation();

                        System.out.println("Email is not empty");
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Permissions")
                                .whereEqualTo("email", emailText)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            if (document.getString("email").equals(emailText)) {
                                                emailIsInDatabase.set(true);
                                                if (document.getBoolean("permission")) {
                                                    view.setVisibility(View.VISIBLE);
                                                    loading_anim.setVisibility(View.INVISIBLE);
                                                    loading_anim.pauseAnimation();
                                                    Toast.makeText(context, "User already has permission", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    view.setVisibility(View.VISIBLE);
                                                    loading_anim.setVisibility(View.INVISIBLE);
                                                    loading_anim.pauseAnimation();
                                                    Toast.makeText(context, "User already used his permission\n" +
                                                            "If you want to update his permission long press the invite button", Toast.LENGTH_LONG).show();
                                                }
                                                return;
                                            } else {
                                                System.out.println("Email is not in database");
                                                emailIsInDatabase.set(false);
                                            }
                                        }
                                        if (!emailIsInDatabase.get()) {
                                            db.collection("Permissions").document(String.valueOf(serialNumber)).set(new Permission(emailText, TRUE))
                                                    .addOnCompleteListener(task2 -> {
                                                        if (task2.isSuccessful()) {
                                                            Toast.makeText(context, "Invitation Sent", Toast.LENGTH_SHORT).show();
                                                            System.out.println("Invitation Sent");
                                                            email.setText("");
                                                            view.setVisibility(View.VISIBLE);
                                                            loading_anim.setVisibility(View.INVISIBLE);
                                                            loading_anim.pauseAnimation();
                                                            updateSerial();
                                                            sendMail();
                                                            switchToAnimation();

                                                        } else {
                                                            view.setVisibility(View.VISIBLE);
                                                            loading_anim.setVisibility(View.INVISIBLE);
                                                            loading_anim.pauseAnimation();
                                                            Toast.makeText(context, "Invitation Failed", Toast.LENGTH_SHORT).show();
                                                            System.out.println("Invitation Failed");
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(context, "Please check the box", Toast.LENGTH_SHORT).show();
            }
    }


    public void onLongClick(View v) {
        AtomicReference<Boolean> emailIsInDatabase = new AtomicReference<>();
        emailIsInDatabase.set(false);
        Log.d("Invite", "Long Button Pressed");
        if (email.getText().toString().isEmpty()){
            Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
        }else {
            if (checkBox.isChecked()) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Permissions").get().addOnCompleteListener(task -> {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.getString("email").equals(email.getText().toString())) {
                            emailIsInDatabase.set(true);
                            if (document.getBoolean("permission")) {
                                Toast.makeText(context, "User already has permission", Toast.LENGTH_SHORT).show();
                            } else {
                                db.collection("Permissions").document(document.getId()).update("permission", true);
                                Toast.makeText(context, "Permission Updated", Toast.LENGTH_SHORT).show();
                                sendMail();
                                switchToAnimation();
                            }
                            break;
                        }
                    }
                    if (!emailIsInDatabase.get()) {
                        Toast.makeText(context, "Email is not in database", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "Please check the box", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateSerial() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference permissions = db.collection("Permissions");
        ArrayList<String> serial = new ArrayList<>();
        permissions.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    serial.add(document.getId());
                }
                System.out.println("ID IS "+ serial.get(serial.size()-1));
                serialNumber = Integer.parseInt(serial.get(serial.size()-1));
                serialNumber++;
                this.serial.setText("Serial No: "+serialNumber);
                runOnUiThread(() -> {
                    this.serial.setVisibility(View.VISIBLE);
                });

            } else {
                Log.d("Invite", "Error getting documents: ", task.getException());
                System.out.println("Error getting documents: "+ task.getException());
            }
        });

    }
    public void resizeImage(int height) {
        int newHeight = (int) (height * 0.37);
        int imageWidth = illustration.getDrawable().getIntrinsicWidth();
        int newWidth = (int) (newHeight * (imageWidth / illustration.getDrawable().getIntrinsicHeight()));
        System.out.println("New Width: "+newWidth);
        System.out.println("New Height: "+newHeight);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        illustration.setLayoutParams(params);
    }
    public void sendMail(){
        String emailText = emailAddress;
        String subject = "Invitation to GSCSC";
        String body = "Your serial number is "+serialNumber+"\nForm link : https://youtu.be/f3KzmVblyHw";
        JavaMailAPI javaMailAPI = new JavaMailAPI(context, emailText, subject, body);
        javaMailAPI.execute();
        emailAddress= "";
    }

    public void switchToAnimation(){
        Intent intent = new Intent(context, invite_sent_anim.class);
        startActivity(intent);
    }

}
