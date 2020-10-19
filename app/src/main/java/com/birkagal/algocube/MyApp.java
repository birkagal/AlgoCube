package com.birkagal.algocube;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SolveDB.initHelper();
    }
}