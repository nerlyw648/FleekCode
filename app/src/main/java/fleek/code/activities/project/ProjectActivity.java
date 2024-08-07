package fleek.code.activities.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.nio.file.Paths;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ActivityProjectBinding;
import fleek.code.models.Project;
import fleek.code.ui.dialogs.ModalDialog;
import fleek.code.ui.viewmodels.ProjectActivityViewModel;
import fleek.code.ui.viewmodels.ProjectsFragmentViewModel;
import fleek.code.utils.ObjectMap;
import fleek.code.utils.Utils;
import fleek.code.utils.storage.FileManager;

public class ProjectActivity extends ThemedActivity implements Observer<ProjectActivityViewModel.Data> {
    private ActivityProjectBinding binding;
    private String projectName;
    private ProjectActivityViewModel viewModel;
    private ProjectActivityViewModel.Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectName = getIntent().getStringExtra("projectName");

        binding.toolbar.setTitle(projectName);

        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);

        viewModel = new ViewModelProvider(this).get(ProjectActivityViewModel.class);
        viewModel.getData(new ProjectActivityViewModel.Data()).observe(this, this);
    }

    @Override
    public void onChanged(ProjectActivityViewModel.Data data) {
        this.data = data;

        if (data.project == null && !data.isLoading) {
            data.isLoaded = false;
            data.isLoading = true;
            viewModel.update();

            data.loadThread = new Thread(() -> {
                data.project = Project.loadProject(Paths.get(FileManager
                        .getPath().toString() + "/" + projectName));

                if (Thread.currentThread().isInterrupted()) return;

                Utils.ui(() -> {
                    data.isLoaded = true;
                    data.isLoading = false;
                    viewModel.update();
                });
            });

            data.loadThread.start();
        } else {
            binding.toolbar.setSubtitle(data.project.getProjectType());

        }

        binding.projectLoad.setVisibility(data.isLoaded ? View.INVISIBLE : View.VISIBLE);
    }

    public void showConfirmExitDialog() {
        ModalDialog.create()
                .setIcon(R.drawable.ic_help)
                .setTitle(getString(R.string.projectActivityConfirmExitTitle))
                .setText(getString(R.string.projectActivityConfirmExitText))
                .setNegativeButton(getString(R.string.buttonDeny), (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(getString(R.string.buttonAccept), (dialogInterface, i) -> {
                    destroyTasks();
                    finish();
                })
                .show(this);
    }

    private void destroyTasks() {
        if (data.loadThread != null) data.loadThread.interrupt();
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            showConfirmExitDialog();
        }
    };
}