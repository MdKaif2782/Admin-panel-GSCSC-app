package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class amongus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amongus);
        ImageView imageView = findViewById(R.id.amongus);
        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_and_slide_away));
        TextView textView = findViewById(R.id.eject_message);
        textView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_alpha));
        // finish the activity after 5 seconds
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                },
                6500);
    }
}