package fleek.code.activities.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityProjectBinding;
import fleek.code.ui.dialogs.ModalDialog;
import fleek.code.utils.ObjectMap;

public class ProjectActivity extends ThemedActivity {
    private ActivityProjectBinding binding;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectName = getIntent().getStringExtra("projectName");

        binding.toolbar.setTitle(projectName);

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showConfirmExitDialog();
            }
        });
    }

    public void showConfirmExitDialog() {
        ModalDialog.create()
                .setIcon(R.drawable.ic_help)
                .setTitle(getString(R.string.projectActivityConfirmExitTitle))
                .setText(getString(R.string.projectActivityConfirmExitText))
                .setNegativeButton(getString(R.string.buttonDeny), (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(getString(R.string.buttonAccept), (dialogInterface, i) -> finish())
                .show(this);
    }
}