package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class invite_sent_anim extends AppCompatActivity {
    private LottieAnimationView animationView;
    private LottieAnimationView animationView2;
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sent_anim);
        animationView = findViewById(R.id.rocket_animation);
        textView = findViewById(R.id.invitation_sent_text);
        textView2 = findViewById(R.id.awesome_text);
        animationView2 = findViewById(R.id.cloud_animation);

        //rocket
        animationView.setBackground(null);
        animationView.setAnimation(R.raw.lottie_rocket_single);
        animationView.setTranslationY(580);
        animationView.playAnimation();


        //cloud
        animationView2.setBackground(null);
        animationView2.setAnimation(R.raw.lottie_cloud);
        animationView2.setTranslationY(500);
        animationView2.playAnimation();


        animationView.addAnimatorListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                //wait 1000ms for thread
                try {
                    Thread.sleep(1000);
                    View view = findViewById(R.id.rocket_animation);
                    view.setAnimation(android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rocket_goes_up_anim));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                //wait 1000ms for thread
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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