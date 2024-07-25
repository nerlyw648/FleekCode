package fleek.code.utils;

import android.content.Context;
import android.content.Intent;

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
}
