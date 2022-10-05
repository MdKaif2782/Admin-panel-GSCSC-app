package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Option extends AppCompatActivity {
    private Intent inviteIntent;
    private Context context;
    private ImageView admin_panel_image;
    private Button invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        context = this;
        inviteIntent = new Intent(this, Invite.class);
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        admin_panel_image = findViewById(R.id.admin_panel_image);
        invite = findViewById(R.id.invite);
        resizeImage(screenHeight);
    }
    public void switchToInvite(View view) {
        //delay 200ms
        invite.postDelayed(() -> {
            context.startActivity(inviteIntent);
        }, 320);

    }
    public void switchToDelete(View view) {
        //Slide animation
        Intent deleteIntent = new Intent(context, DeleteActivity.class);
        invite.postDelayed(() -> {
            context.startActivity(deleteIntent);
        }, 320);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void resizeImage(int height){
        int newHeight = (int) (height * 0.37);
        int newWidth = 187*newHeight/220;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        admin_panel_image.setLayoutParams(params);
    }

    public void notReadyYet(View view) {
        Toast.makeText(context, "Can't be ready until Website is complete", Toast.LENGTH_SHORT).show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        finish();
    }
}