package com.birkagal.algocube;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

public class Activity_Login extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showSignInOptions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(Activity_Login.this, Activity_Home.class));
                SolveDB.getInstance().instantiateDB();
            } else {
                if (response != null)
                    Log.d("error_sign_in", "" + response.getError().getMessage());
            }
            finish();
        }
    }

    private void showSignInOptions() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.AnonymousBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("IL").build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_logo_with_text)
                        .setTheme(R.style.LoginTheme)
                        .build(), MY_REQUEST_CODE
        );
    }
}