package com.birkagal.algocube;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class Activity_Splash extends AppCompatActivity {

    private TextView main_txt_name;
    private ImageView main_img_logo;
    private boolean isAnimDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        findViews();
        make_name();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float height = (float) displayMetrics.heightPixels;

        animate(main_img_logo, height, 360f);
        animate(main_txt_name, height, 0f);
    }

    private void animate(@NotNull final View view, float height, float rotation) {
        int ANIMATION_DURATION = 3000;
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);
        view.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .rotation(rotation)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new LinearInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startApp();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
    }

    private void startApp() {
        if (isAnimDone) {
            startActivity(new Intent(Activity_Splash.this, Activity_Home.class));
            finish();
        } else {
            isAnimDone = true;
        }
    }

    private void make_name() {
        String algo = "<font color='#5bd0fc'>algo</font>";
        String cube = "<font color='#ed6d66'>cube</font>";
        main_txt_name.setText(Html.fromHtml(algo + cube));
    }

    private void findViews() {
        main_txt_name = findViewById(R.id.main_txt_name);
        main_img_logo = findViewById(R.id.main_img_logo);
    }
}