package fleek.code.activities.project;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivitySelectTemplateBinding;
import fleek.code.models.Template;
import fleek.code.ui.adapters.TemplatesAdapter;
import fleek.code.utils.ObjectList;
import fleek.code.utils.Utils;

public class SelectTemplateActivity extends ThemedActivity {

    private ActivitySelectTemplateBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectTemplateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getTemplates();
    }

    private void getTemplates() {
        binding.templatesList.setVisibility(View.INVISIBLE);
        binding.templatesLoad.setVisibility(View.VISIBLE);

        new Thread(() -> {
            final ObjectList<Template> templates = Template.getTemplates();
            Utils.ui(() -> {
                binding.templatesList.setLayoutManager(new GridLayoutManager(this, 3));
                binding.templatesList.setAdapter(new TemplatesAdapter(this, templates));
                binding.templatesLoad.setVisibility(View.INVISIBLE);
                binding.templatesList.setVisibility(View.VISIBLE);
            });
        }).start();
    }
}
