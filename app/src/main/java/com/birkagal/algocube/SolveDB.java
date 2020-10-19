package com.birkagal.algocube;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SolveDB {

    private static SolveDB instance;
    private final String USERS = "users";
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private Gson gson;
    private ArrayList<Solve> solves;

    public static SolveDB getInstance() {
        return instance;
    }

    public static SolveDB initHelper() {
        if (instance == null)
            instance = new SolveDB();
        return instance;
    }

    private SolveDB() {
        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        fbDatabase.setPersistenceEnabled(true);
        mDatabase = fbDatabase.getReference();
    }

    public void instantiateDB() {


        user = FirebaseAuth.getInstance().getCurrentUser(); // get current user
        gson = new Gson();
        this.solves = new ArrayList<>();
        retrieveSolves();
    }

    private void retrieveSolves() {
        mDatabase.child(this.USERS).child(user.getUid()).addListenerForSingleValueEvent(listenerDB);
    }

    public void uploadSolve(long time) {
        this.solves.add(new Solve(time));
        mDatabase.child(this.USERS).child(this.user.getUid()).setValue(gson.toJson(this.solves));
    }

    public void clearSolves() {
        mDatabase.child(this.USERS).child(this.user.getUid()).removeValue();
        this.solves = new ArrayList<>();
    }

    public String getFormattedTime(Long time) {
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        Long millis = (time / 10) % 100;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, millis);
    }

    public ArrayList<Solve> getSolves() {
        return solves;
    }

    private final ValueEventListener listenerDB = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            solves = new ArrayList<>();
            Object value = snapshot.getValue();
            if (value != null) {
                if (value instanceof String) {
                    try {
                        solves = gson.fromJson((String) value, new TypeToken<ArrayList<Solve>>() {
                        }.getType());
                    } catch (Exception ex) {
                        Log.d("ptt", "loadPost:onCancelled " + ex.getMessage());
                    }
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("ptt", "loadPost:onCancelled", error.toException());
        }
    };
}
