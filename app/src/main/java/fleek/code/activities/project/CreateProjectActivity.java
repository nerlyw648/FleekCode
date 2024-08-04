package fleek.code.activities.project;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

import fleek.code.App;
import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityCreateProjectBinding;
import fleek.code.utils.FileManager;

public class CreateProjectActivity extends ThemedActivity {

    private ActivityCreateProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.projectName.addTextChangedListener(textWatcher);
        binding.projectPackage.addTextChangedListener(textWatcher);
        binding.projectSdkVersion.setOnItemClickListener(onItemClickListener);
        binding.projectLanguage.setOnItemClickListener(onItemClickListener);
    }

    private boolean checkFields() {
        if (binding.projectName.getText().toString().isEmpty()) {
            binding.projectNameLayout.setErrorEnabled(false);
            return false;
        } else if (FileManager.exists(binding.projectName.getText().toString())) {
            binding.projectNameLayout.setError(getString(R.string.createProjectActivityExists));
            binding.projectNameLayout.setErrorEnabled(true);
            return false;
        } else binding.projectNameLayout.setErrorEnabled(false);

        if (binding.projectPackage.getText().toString().isEmpty()) {
            binding.projectPackageLayout.setErrorEnabled(false);
            return false;
        } else if (!Pattern.matches("^([a-zA-Z_][a-zA-Z0-9_]*\\.){1,}[a-zA-Z_][a-zA-Z0-9_]*$", binding.projectPackage.getText().toString())) {
            binding.projectPackageLayout.setError(getString(R.string.createProjectActivityPackageInvalid));
            binding.projectPackageLayout.setErrorEnabled(true);
            return false;
        } else binding.projectPackageLayout.setErrorEnabled(false);

        if (binding.projectSdkVersion.getText().toString().isEmpty()) return false;
        if (binding.projectLanguage.getText().toString().isEmpty()) return false;

        return true;
    }

    final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            binding.projectCreate.setEnabled(checkFields());
        }
    };

    final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.projectCreate.setEnabled(checkFields());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}