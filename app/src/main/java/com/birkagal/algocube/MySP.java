package com.birkagal.algocube;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MySP {

    public interface KEYS {
        String USER_SOLVE_LIST = "USER_SOLVE_LIST";
    }

    private static MySP instance;
    private final SharedPreferences prefs;

    public static MySP getInstance() {
        return instance;
    }

    private MySP(@NotNull Context context) {
        prefs = context.getApplicationContext().getSharedPreferences("APP_SP_DB", Context.MODE_PRIVATE);
    }

    private MySP(@NotNull Context context, String sharePreferencesName) {
        prefs = context.getApplicationContext().getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
    }

    public static MySP initHelper(Context context) {
        if (instance == null)
            instance = new MySP(context);
        return instance;
    }

    public static MySP initHelper(Context context, String sharePreferencesName) {
        if (instance == null)
            instance = new MySP(context, sharePreferencesName);
        return instance;
    }

    public void putString(String KEY, String value) {
        prefs.edit().putString(KEY, value).apply();
    }

    public void putObject(String KEY, Object value) {
        prefs.edit().putString(KEY, new Gson().toJson(value)).apply();
    }

    public void putBoolean(String KEY, boolean value) {
        prefs.edit().putBoolean(KEY, value).apply();
    }

    public void putInt(String KEY, int value) {
        prefs.edit().putInt(KEY, value).apply();
    }

    public void putLong(String KEY, long value) {
        prefs.edit().putLong(KEY, value).apply();
    }

    public void putFloat(String KEY, float value) {
        prefs.edit().putFloat(KEY, value).apply();
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public String getString(String KEY, String defvalue) {
        return prefs.getString(KEY, defvalue);
    }

    public <T> T getObject(String KEY, Class<T> mModelClass) {
        Object object = null;
        try {
            object = new Gson().fromJson(prefs.getString(KEY, ""), mModelClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Primitives.wrap(mModelClass).cast(object);
    }

    public boolean getBoolean(String KEY, boolean defvalue) {
        return prefs.getBoolean(KEY, defvalue);
    }

    public int getInt(String KEY, int defValue) {
        return prefs.getInt(KEY, defValue);
    }

    public long getLong(String KEY, long defValue) {
        return prefs.getLong(KEY, defValue);
    }

    public float getFloat(String KEY, float defValue) {
        return prefs.getFloat(KEY, defValue);
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public void removeKey(String KEY) {
        prefs.edit().remove(KEY).apply();
    }

    public boolean contain(String KEY) {
        return prefs.contains(KEY);
    }

    public void registerChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public <T> void putArray(String KEY, ArrayList<T> array) {
        prefs.edit().putString(KEY, new Gson().toJson(array)).apply();
    }

    public <T> ArrayList<T> getArray(String KEY, TypeToken typeToken) {
        // type token == new TypeToken<ArrayList<YOUR_CLASS>>() {}
        ArrayList<T> arr = null;
        try {
            arr = new Gson().fromJson(prefs.getString(KEY, ""), typeToken.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public <S, T> void putMap(String KEY, HashMap<S, T> map) {
        prefs.edit().putString(KEY, new Gson().toJson(map)).apply();
    }

    public <S, T> HashMap<S, T> getMap(String KEY, TypeToken typeToken) {
        // type token == new TypeToken<HashMap<YOUR, MAP>>() {}
        HashMap<S, T> map = null;
        try {
            map = new Gson().fromJson(prefs.getString(KEY, ""), typeToken.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public String getFormattedTime(Long time) {
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        Long millis = (time / 10) % 100;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, millis);
    }

}
