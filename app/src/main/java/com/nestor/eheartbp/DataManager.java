package com.nestor.eheartbp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "eHeartBP";
    
    // Keys para SharedPreferences
    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_PULSE = "pulse";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_PULSE_LIST = "pulse_list";
    private static final String KEY_DIASTOLIC_LIST = "diastolic_list";
    private static final String KEY_SYSTOLIC_LIST = "systolic_list";
    private static final String KEY_TIME_LIST = "time_list";
    
    private DataManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context.getApplicationContext());
        }
        return instance;
    }
    
    // Métodos para la última medición
    public void saveLastMeasurement(int systolic, int diastolic, int pulse, long timestamp) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_SYSTOLIC, systolic);
        editor.putInt(KEY_DIASTOLIC, diastolic);
        editor.putInt(KEY_PULSE, pulse);
        editor.putLong(KEY_TIMESTAMP, timestamp);
        editor.apply();
    }
    
    public int getLastSystolic() {
        return prefs.getInt(KEY_SYSTOLIC, 0);
    }
    
    public int getLastDiastolic() {
        return prefs.getInt(KEY_DIASTOLIC, 0);
    }
    
    public int getLastPulse() {
        return prefs.getInt(KEY_PULSE, 0);
    }
    
    public long getLastTimestamp() {
        return prefs.getLong(KEY_TIMESTAMP, 0);
    }
    
    // Métodos para las listas de mediciones
    public void savePulseList(List<String> pulseList) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_PULSE_LIST, new java.util.HashSet<>(pulseList));
        editor.apply();
    }
    
    public void saveDiastolicList(List<String> diastolicList) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_DIASTOLIC_LIST, new java.util.HashSet<>(diastolicList));
        editor.apply();
    }
    
    public void saveSystolicList(List<String> systolicList) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_SYSTOLIC_LIST, new java.util.HashSet<>(systolicList));
        editor.apply();
    }
    
    public void saveTimeList(List<String> timeList) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_TIME_LIST, new java.util.HashSet<>(timeList));
        editor.apply();
    }
    
    public List<String> getPulseList() {
        java.util.Set<String> set = prefs.getStringSet(KEY_PULSE_LIST, new java.util.HashSet<>());
        return new ArrayList<>(set);
    }
    
    public List<String> getDiastolicList() {
        java.util.Set<String> set = prefs.getStringSet(KEY_DIASTOLIC_LIST, new java.util.HashSet<>());
        return new ArrayList<>(set);
    }
    
    public List<String> getSystolicList() {
        java.util.Set<String> set = prefs.getStringSet(KEY_SYSTOLIC_LIST, new java.util.HashSet<>());
        return new ArrayList<>(set);
    }
    
    public List<String> getTimeList() {
        java.util.Set<String> set = prefs.getStringSet(KEY_TIME_LIST, new java.util.HashSet<>());
        return new ArrayList<>(set);
    }
    
    // Método para limpiar todos los datos
    public void clearAllData() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
} 