package fleek.code;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityMainBinding;
import fleek.code.ui.dialogs.ModalDialog;
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
            PermissionManager.checkPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        } else PermissionManager.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionDialog(String ...permissions) {
        super.onRequestPermissionDialog(permissions);
        if (permissions[0].equals(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            if (Utils.getCurrentSdkVersion() >= 30 && !Environment.isExternalStorageManager()) {
                ModalDialog.create()
                        .setIcon(R.drawable.ic_folder)
                        .setTitle(getString(R.string.permissionStorageTitle))
                        .setText(getString(R.string.permissionStorageText))
                        .setButtons(ObjectMap.of(
                                getString(R.string.permissionStorageButton), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        startActivity(new Intent(
                                                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                                Uri.parse("package:" + getPackageName())));
                                    }
                                }))
                        .show(this);
            }
        }
    }
}