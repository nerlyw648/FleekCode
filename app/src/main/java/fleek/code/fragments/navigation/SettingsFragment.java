package fleek.code.fragments.navigation;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import fleek.code.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {}

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}