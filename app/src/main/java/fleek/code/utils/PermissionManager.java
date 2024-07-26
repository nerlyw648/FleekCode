package fleek.code.utils;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;

import fleek.code.activities.ThemedActivity;

public class PermissionManager {
    public static void checkPermission(ThemedActivity context, String ...permissions) {
        if (ContextCompat.checkSelfPermission(
                context, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            context.onRequestPermissionResult(permissions);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                context, permissions[0])) {
            context.onRequestPermissionDialog(permissions);
        } else requestPermission(context, permissions);
    }

    public static void requestPermission(ThemedActivity context, String ...permissions) {
        context.requestPermissions(permissions, 1);
    }
}