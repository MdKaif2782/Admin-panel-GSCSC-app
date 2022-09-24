package com.gscsc.adminpanel;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        context = this;
        email = findViewById(R.id.email_input);
        serial = findViewById(R.id.serial_number);

        updateSerial();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void onInviteButtonPressed(View view) {
        String emailText = email.getText().toString();
        if (emailText.isEmpty()) {
            System.out.println("Email is empty");
            Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show();
        }
        else {
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
    }

    public void updateSerial() {
        //read data from collection Permissions
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
                this.serial.setText(String.valueOf("Serial No: "+serialNumber));

            } else {
                System.out.println("Error getting documents: "+ task.getException());
            }
        });

    }
}