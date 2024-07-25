package fleek.code;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedList;

import fleek.code.utils.Prefs;

public class App extends Application {
    private static Context context;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
    }

    public static boolean isAccountEntried() {
        return Prefs.containsKey("userId");
    }

    public static String getUserId() {
        return Prefs.getString("userId");
    }

    public static String getAuthKey() {
        return Prefs.getString("authKey");
    }

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
