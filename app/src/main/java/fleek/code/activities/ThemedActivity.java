package fleek.code.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import fleek.code.R;

public class ThemedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final MaterialToolbar materialToolbar = findViewById(R.id.toolbar);

        if (materialToolbar != null) {
            materialToolbar.setNavigationIcon(R.drawable.ic_back);
            materialToolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }
    }

    public void onRequestPermissionResult(String ...permissions) {}

    public void onRequestPermissionDialog(String ...permissions) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onRequestPermissionResult(permissions);
        } else onRequestPermissionDialog(permissions[0]);
    }
}
