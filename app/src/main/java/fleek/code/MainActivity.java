package fleek.code;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityMainBinding;
import fleek.code.ui.dialogs.ModalDialog;
import fleek.code.utils.FileManager;
import fleek.code.utils.ObjectMap;
import fleek.code.utils.PermissionManager;
import fleek.code.utils.Utils;

public class MainActivity extends ThemedActivity {

    private ActivityMainBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigationFragment);
        final NavController navController = navHostFragment.getNavController();
        final BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.getCurrentSdkVersion() >= 30) {
            PermissionManager.checkPermissions(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            initWorkspace();
        } else PermissionManager.checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void sendRequestPermissionsDialog(String ...permissions) {
        ModalDialog.create()
                .setIcon(R.drawable.ic_folder)
                .setTitle(getString(R.string.permissionStorageTitle))
                .setText(getString(R.string.permissionStorageText))
                .setButtons(ObjectMap.of(
                        getString(R.string.permissionStorageButton), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if (permissions[0].equals(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
                                    startActivity(new Intent(
                                            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                            Uri.parse("package:" + getPackageName())));
                                } else PermissionManager.requestPermission(MainActivity.this, permissions);
                            }
                        }))
                .setDismissible(false)
                .show(this);
    }

    public void initWorkspace() {
        try {
            FileManager.initWorkspace(this);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(String... permissions) {
        super.onRequestPermissionsResult(permissions);
        initWorkspace();
    }

    @Override
    public void onRequestPermissionsDialog(String ...permissions) {
        super.onRequestPermissionsDialog(permissions);
        if (permissions[0].equals(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            if (Utils.getCurrentSdkVersion() >= 30 && !Environment.isExternalStorageManager()) {
                sendRequestPermissionsDialog(permissions);
            }
        } else sendRequestPermissionsDialog(permissions);
    }
}