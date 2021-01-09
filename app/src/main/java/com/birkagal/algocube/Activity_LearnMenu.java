package com.birkagal.algocube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class Activity_LearnMenu extends AppCompatActivity {

    private Button learn_menu_btn_learn3, learn_menu_btn_learn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_menu);

        findViews();

        learn_menu_btn_learn3.setOnClickListener(bottomClickListener);
        learn_menu_btn_learn2.setOnClickListener(bottomClickListener);
    }

    private final View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomClicked(view);
        }
    };

    private void bottomClicked(@NotNull View view) {
        Intent intent = new Intent();
        int id = view.getId();

        if (id == learn_menu_btn_learn3.getId())
            intent = new Intent(Activity_LearnMenu.this, Activity_Learn3.class);
        else if (id == learn_menu_btn_learn2.getId()) {
            Toast.makeText(this, "Coming SOON!", Toast.LENGTH_LONG).show();
            return;
//             NEED TO EXPEND APP TO SUPPORT 2X2!
//            intent = new Intent(Activity_LearnMenu.this, Activity_Learn2.class);
        }
        startActivity(intent);
    }

    private void findViews() {
        learn_menu_btn_learn3 = findViewById(R.id.learn_menu_btn_learn3);
        learn_menu_btn_learn2 = findViewById(R.id.learn_menu_btn_learn2);
    }

}