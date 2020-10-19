package com.birkagal.algocube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class Activity_Home extends AppCompatActivity {

    private Button home_btn_learn, home_btn_timer, home_btn_stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();

        home_btn_learn.setOnClickListener(bottomClickListener);
        home_btn_timer.setOnClickListener(bottomClickListener);
        home_btn_stats.setOnClickListener(bottomClickListener);

    }

    private void findViews() {
        home_btn_learn = findViewById(R.id.home_btn_learn);
        home_btn_timer = findViewById(R.id.home_btn_timer);
        home_btn_stats = findViewById(R.id.home_btn_stats);
    }

    private View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomClicked(view);
        }
    };

    private void bottomClicked(@NotNull View view) {
        Intent intent = new Intent();
        int id = view.getId();

        if (id == home_btn_learn.getId())
            intent = new Intent(Activity_Home.this, Activity_LearnMenu.class);
        else if (id == home_btn_timer.getId())
            intent = new Intent(Activity_Home.this, Activity_Timer.class);
        else if (id == home_btn_stats.getId())
            intent = new Intent(Activity_Home.this, Activity_Stats.class);

        startActivity(intent);
    }

}