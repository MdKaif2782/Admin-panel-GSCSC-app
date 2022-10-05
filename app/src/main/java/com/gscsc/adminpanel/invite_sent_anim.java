package com.gscsc.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class invite_sent_anim extends AppCompatActivity {
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sent_anim);
        animationView = findViewById(R.id.lottie_rocket);
        //close after animation is done
        animationView.addAnimatorListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                View view = findViewById(R.id.lottie_rocket);
                view.setAnimation(android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rocket_apha));

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                finish();
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