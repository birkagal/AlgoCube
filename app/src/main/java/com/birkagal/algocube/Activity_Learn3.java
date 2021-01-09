package com.birkagal.algocube;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Activity_Learn3 extends AppCompatActivity {

    private BottomNavigationView learn3_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn3);

        findViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.learn3_fragment_container,
                new OLLFragment()).commit();
        learn3_nav.setOnNavigationItemSelectedListener(navListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_oll) {
                selectedFragment = new OLLFragment();
            } else if (id == R.id.nav_pll) {
                selectedFragment = new PLLFragment();
            } else if (id == R.id.nav_beginner) {
                selectedFragment = new BeginnerFragment();
            } else if (id == R.id.nav_notation) {
                selectedFragment = new NotationFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.learn3_fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };

    private void findViews() {
        learn3_nav = findViewById(R.id.learn3_nav);
        learn3_nav.setItemIconTintList(null);
    }
}