package com.gscsc.adminpanel;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;

public class Invite extends AppCompatActivity {
    private TextView serial;
    private EditText email;
    private Context context;
    private int serialNumber;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        context = this;
        email = findViewById(R.id.email_input);
        serial = findViewById(R.id.serial_number);
        checkBox = findViewById(R.id.checkbox);
        updateSerial();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void onInviteButtonPressed(View view) {
        if (checkBox.isChecked()) {
            String emailText = email.getText().toString();
            if (emailText.isEmpty()) {
                System.out.println("Email is empty");
                Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("Email is not empty");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Permissions").document(String.valueOf(serialNumber)).set(new Permission(emailText, TRUE))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
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
        } else {
            Toast.makeText(context, "Please check the box", Toast.LENGTH_SHORT).show();
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
}