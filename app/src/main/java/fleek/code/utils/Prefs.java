package fleek.code.utils;

import fleek.code.App;

public class Prefs {
    public static String getString(String key) {
        return App.getPreferences().getString(key, "");
    }

    public static long getLong(String key) {
        return App.getPreferences().getLong(key, 0);
    }

    public static boolean getBoolean(String key) {
        return App.getPreferences().getBoolean(key, false);
    }

    public static int getInteger(String key) {
        return App.getPreferences().getInt(key, 0);
    }

    public static void setString(String key, String value) {
        App.getPreferences().edit().putString(key, value).apply();
    }

    public static void setLong(String key, long value) {
        App.getPreferences().edit().putLong(key, value).apply();
    }

    public static void setBoolean(String key, boolean value) {
        App.getPreferences().edit().putBoolean(key, value).apply();
    }

    public static void setInteger(String key, int value) {
        App.getPreferences().edit().putInt(key, value).apply();
    }

    public static boolean containsKey(String key) {
        return App.getPreferences().contains(key);
    }
}
