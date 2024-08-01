package fleek.code.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;

public class Utils {
    public static void startActivity(Context context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static void startActivity(ThemedActivity context, Class<?> clazz, int flags) {
        context.startActivity(new Intent(context, clazz)
                .addFlags(flags));
    }

    public static void startActivity(ThemedActivity context, Intent intent) {
        context.startActivity(intent);
    }

    public static void setEmptyLayoutVisible(View rootView, String title, String text) {
        final LinearLayout emptyLayout = rootView.findViewById(R.id.emptyLayout);
        final TextView emptyTitle = emptyLayout.findViewById(R.id.emptyTitle);
        final TextView emptyText = emptyLayout.findViewById(R.id.emptyText);
        emptyTitle.setText(title);
        emptyText.setText(text);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    public static void setEmptyLayoutInvisible(View rootView) {
        final LinearLayout emptyLayout = rootView.findViewById(R.id.emptyLayout);
        emptyLayout.setVisibility(View.INVISIBLE);
    }

    public static int getCurrentSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static void ui(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
