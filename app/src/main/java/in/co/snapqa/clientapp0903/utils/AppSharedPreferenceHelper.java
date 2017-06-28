package in.co.snapqa.clientapp0903.utils;

import android.content.Context;
import android.content.SharedPreferences;

import in.co.snapqa.clientapp0903.SnapApplication;

public class AppSharedPreferenceHelper {
    public static final String PHONE = "phone";
    public static final String KEY = "key";
    private static final String PREF_NAME = "snapQATutorPref";
    // Shared Preferences
    private static SharedPreferences pref;
    // Editor for Shared preferences
    private static SharedPreferences.Editor editor;

    // Constructor
    private AppSharedPreferenceHelper() {
    }

    private static void openSharedPreferencesInEditMode() {
        editor = SnapApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE).edit();
    }

    private static void openSharedPreferencesInReadMode() {
        pref = SnapApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
    }


    //    apply is fast than Commit
    private static void closeSharedPreferences() {
        editor.apply();
    }

    public static void set(String key, String value) {
        openSharedPreferencesInEditMode();
        editor.putString(key, value);
        closeSharedPreferences();
    }

    public static String getString(String key) {
        openSharedPreferencesInReadMode();
        return pref.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        openSharedPreferencesInReadMode();
        return pref.getString(key, defaultValue);
    }

    public static boolean contains(String key) {
        openSharedPreferencesInReadMode();
        return pref.contains(key);
    }

    public static void set(String key, int value) {
        openSharedPreferencesInEditMode();
        editor.putInt(key, value);
        closeSharedPreferences();
    }

    public static void set(String key, long value) {
        openSharedPreferencesInEditMode();
        editor.putLong(key, value);
        closeSharedPreferences();
    }

    public static int getInt(String key) {
        openSharedPreferencesInReadMode();
        return pref.getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        openSharedPreferencesInReadMode();
        return pref.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        openSharedPreferencesInReadMode();
        return pref.getLong(key, defaultValue);
    }

    public static void set(String key, boolean value) {
        openSharedPreferencesInEditMode();
        editor.putBoolean(key, value);
        closeSharedPreferences();
    }

    public static boolean getBoolean(String key) {
        openSharedPreferencesInReadMode();
        return pref.getBoolean(key, false);
    }

    public static void deleteAll() {
        openSharedPreferencesInEditMode();
        editor.clear();
        closeSharedPreferences();
    }
}
