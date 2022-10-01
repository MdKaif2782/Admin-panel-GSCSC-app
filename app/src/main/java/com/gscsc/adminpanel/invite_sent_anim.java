package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class invite_sent_anim extends AppCompatActivity {
    private LottieAnimationView animationView;
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sent_anim);
        animationView = findViewById(R.id.lottie_rocket);
        textView = findViewById(R.id.invitation_sent_text);
        textView2 = findViewById(R.id.awesome_text);
        //close after animation is done
        animationView.addAnimatorListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                textView.setVisibility(android.view.View.VISIBLE);
                textView2.setVisibility(android.view.View.VISIBLE);
                //wait 1.5s
                //add animation to text
                textView2.setAnimation(android.view.animation.AnimationUtils.loadAnimation(invite_sent_anim.this, R.anim.invitation_sent_text_anim));
                textView.setAnimation(android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(),R.anim.invitation_sent_text_anim));
                textView.postDelayed(() -> {
                    finish();
                }, 1500);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {

            }
        });
    }
}