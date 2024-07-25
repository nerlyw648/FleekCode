package fleek.code;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityMainBinding;

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
}