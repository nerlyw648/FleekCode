package fleek.code.fragments.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.nio.file.Files;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.activities.project.SelectTemplateActivity;
import fleek.code.databinding.FragmentProjectsBinding;
import fleek.code.models.Project;
import fleek.code.ui.adapters.ProjectsAdapter;
import fleek.code.ui.viewmodels.ProjectsFragmentViewModel;
import fleek.code.ui.viewmodels.ViewModelBase;
import fleek.code.utils.storage.FileManager;
import fleek.code.utils.ObjectList;
import fleek.code.utils.Utils;

public class ProjectsFragment extends Fragment implements Observer<ProjectsFragmentViewModel.Data> {

    private FragmentProjectsBinding binding;


    public ProjectsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProjectsFragmentViewModel.class);
        viewModel.getData(new ProjectsFragmentViewModel.Data()).observe(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private ProjectsFragmentViewModel viewModel;
    private ProjectsFragmentViewModel.Data data;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.projectsCreate.setOnClickListener(v -> Utils.startActivity(getActivity(), SelectTemplateActivity.class));
    }

    @Override
    public void onChanged(ProjectsFragmentViewModel.Data data) {
        this.data = data;

        binding.projectsList.setVisibility(!data.isLoaded ? View.INVISIBLE : View.VISIBLE);
        binding.projectsLoad.setVisibility(!data.isLoaded ? View.VISIBLE : View.INVISIBLE);

        if (projectsAdapter != null && projectsAdapter.getItemCount() == 0) {
            binding.projectsList.setVisibility(View.INVISIBLE);
            Utils.setEmptyLayoutVisible(binding.getRoot(),
                    getString(R.string.projectsFragmentEmptyTitle),
                    getString(R.string.projectsFragmentEmptyText));
        } else {
            Utils.setEmptyLayoutInvisible(binding.getRoot());
            binding.projectsList.setVisibility(View.VISIBLE);
        }
    }

    private ProjectsAdapter projectsAdapter;

    private void loadProjects() {
        data.isLoaded = false;
        viewModel.update();

        if (data.loadThread != null) data.loadThread.interrupt();

        data.loadThread = new Thread(() -> {
            final ObjectList<Project> projectsFiles = ObjectList.of();
            if (Thread.currentThread().isInterrupted()) return;

            FileManager.getFilesFromDir(false).stream().forEach(projectPath -> {
                if (Thread.currentThread().isInterrupted()) return;
                if (!Files.isDirectory(projectPath)) return;

                projectsFiles.add(Project.loadProject(projectPath));
            });

            Utils.ui(() -> {
                data.isLoaded = true;
                viewModel.update();
                projectsAdapter = new ProjectsAdapter((ThemedActivity) getActivity(), projectsFiles);
                binding.projectsList.setAdapter(projectsAdapter);
            });
        });

        data.loadThread.start();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.projectsList.setLayoutManager(new LinearLayoutManager(getContext()));
        loadProjects();

        if (data != null) binding.projectsList.getLayoutManager().onRestoreInstanceState(data.projectsState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (data == null) return;
        data.projectsState = binding.projectsList.getLayoutManager().onSaveInstanceState();
    }
}