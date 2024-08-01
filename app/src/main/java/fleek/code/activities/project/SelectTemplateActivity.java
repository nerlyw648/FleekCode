package fleek.code.activities.project;

import android.os.Bundle;

import androidx.annotation.Nullable;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivitySelectTemplateBinding;

public class SelectTemplateActivity extends ThemedActivity {

    private ActivitySelectTemplateBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectTemplateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
