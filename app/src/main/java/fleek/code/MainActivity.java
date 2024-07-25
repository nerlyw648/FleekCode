package fleek.code;

import android.os.Bundle;
import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityMainBinding;

public class MainActivity extends ThemedActivity {

    private ActivityMainBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());
    }
}