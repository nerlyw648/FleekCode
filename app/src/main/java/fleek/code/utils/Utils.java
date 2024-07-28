package fleek.code.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

public class Utils {
    public static void startActivity(Context context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static void startActivity(Context context, Class<?> clazz, int flags) {
        context.startActivity(new Intent(context, clazz)
                .addFlags(flags));
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static int getCurrentSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static void ui(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
