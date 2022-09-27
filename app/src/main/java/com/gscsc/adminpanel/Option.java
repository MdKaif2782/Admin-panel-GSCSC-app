package com.gscsc.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Option extends AppCompatActivity {
    private Intent inviteIntent;
    private Context context;
    private int screenHeight;
    private ImageView admin_panel_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        context = this;
        inviteIntent = new Intent(this, Invite.class);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        admin_panel_image = findViewById(R.id.admin_panel_image);

        resizeImage(screenHeight);
    }
    public void switchToInvite(View view) {
       context.startActivity(inviteIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void resizeImage(int height){
        int newHeight = (int) (height * 0.34);
        int imageWidth = admin_panel_image.getWidth();
        int imageHeight = admin_panel_image.getHeight();
        int newWidth = (int) (imageWidth * (newHeight / imageHeight));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);
        admin_panel_image.setLayoutParams(params);
    }
}