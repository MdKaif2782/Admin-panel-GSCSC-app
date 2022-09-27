package com.gscsc.adminpanel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        context = this;
        email = findViewById(R.id.email_input);
        serial = findViewById(R.id.serial_number);
        checkBox = findViewById(R.id.checkbox);
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
        if (view.isPressed()) {
            Log.d("Invite", "Button Pressed");
            if (checkBox.isChecked()) {
                String emailText = email.getText().toString();
                if (emailText.isEmpty()) {
                    System.out.println("Email is empty");
                    Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Email is not empty");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Permissions")
                            .whereEqualTo("email",emailText)
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        System.out.println(document.getId());
                                        if (document.getString("email").equals(emailText)) {
                                            if (document.getBoolean("permission")){
                                                Toast.makeText(context, "User already has permission", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "User already used his permission\n " +
                                                        "If you want to update his permission long press the invite button", Toast.LENGTH_LONG).show();
                                            }
                                            return;
                                        } else {
                                            db.collection("Permissions").document(String.valueOf(serialNumber)).set(new Permission(emailText, TRUE))
                                                    .addOnCompleteListener(task2 -> {
                                                        if (task2.isSuccessful()) {
                                                            Toast.makeText(context, "Invitation Sent", Toast.LENGTH_SHORT).show();
                                                            System.out.println("Invitation Sent");
                                                            email.setText("");
                                                            updateSerial();
                                                        } else {
                                                            Toast.makeText(context, "Invitation Failed", Toast.LENGTH_SHORT).show();
                                                            System.out.println("Invitation Failed");
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                }
            } else {
                Toast.makeText(context, "Please check the box", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onLongClick(View v) {
        System.out.println("Long Clicked");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
        }else {
            db.collection("Permissions").whereEqualTo("email", email.getText().toString()).get()
                    .addOnCompleteListener(task->{
                       if (task.isSuccessful()){
                           for (DocumentSnapshot document: task.getResult()){
                                 if (document.getString("email").equals(email.getText().toString())){
                                     if (document.getBoolean("permission")){
                                         Toast.makeText(context, "User already has permission", Toast.LENGTH_SHORT).show();
                                     }else {
                                            db.collection("Permissions").document(document.getId()).update("permission", true)
                                                    .addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()){
                                                            Toast.makeText(context, "Permission Updated", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(context, "Permission Update Failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                     }
                                      return;
                                 }else {
                                     Toast.makeText(context, "User does not exist\n You cannot update permission of a null user", Toast.LENGTH_SHORT).show();
                                 }
                           }
                       }else {
                           Toast.makeText(context, "Unknown Error happened", Toast.LENGTH_SHORT).show();
                       }
                       isLongPressed = false;
                    });
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
}