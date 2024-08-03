package $packagename;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

    super.onCreate(savedInstanceState);

    // Keep the splash screen visible for this Activity.
    splashScreen.setKeepOnScreenCondition(() -> true);
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }
}