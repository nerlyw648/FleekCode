package fleek.code.fragments.navigation;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import fleek.code.R;

public class ToolsFragment extends PreferenceFragmentCompat {

    public ToolsFragment() {}

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}