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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.FragmentProjectsBinding;
import fleek.code.models.Project;
import fleek.code.ui.adapters.ProjectsAdapter;
import fleek.code.ui.viewmodels.ProjectsFragmentViewModel;
import fleek.code.utils.FileManager;
import fleek.code.utils.ObjectList;
import fleek.code.utils.Utils;

public class ProjectsFragment extends Fragment implements Observer<ProjectsFragmentViewModel.Data> {

    private FragmentProjectsBinding binding;


    public ProjectsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProjectsFragmentViewModel.class);
        viewModel.getData().observe(this, this);
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
    }

    @Override
    public void onChanged(ProjectsFragmentViewModel.Data data) {
        this.data = data;

        binding.projectsList.setVisibility(!data.isLoaded ? View.INVISIBLE : View.VISIBLE);
        binding.projectsLoad.setVisibility(!data.isLoaded ? View.VISIBLE : View.INVISIBLE);

    }

    private ProjectsAdapter projectsAdapter;

    private void loadProjects() {
        data.isLoaded = false;
        viewModel.update();

        if (data.loadThread != null) data.loadThread.interrupt();

        data.loadThread = new Thread(() -> {
            try {
                final ObjectList<Project> projectsFiles = ObjectList.of();

                FileManager.getFilesFromDir(false)
                        .stream().forEach(projectPath -> {
                            if (!Files.isDirectory(projectPath)) return;

                            final Project project = new Project();
                            project.name = projectPath.getFileName().toString();

                            final Path path = Paths.get(projectPath.toString() + "/settings.gradle");
                            if (Files.exists(path)) {
                                final String settings = FileManager.readFile(path.toString());
                                final Matcher includeMatcher = Pattern.compile("include(\s|)'(.*?)'").matcher(settings);

                                if (includeMatcher.find()) {
                                    final String includeModule = includeMatcher.group(2);

                                    if (Files.exists(Paths.get(projectPath + "/" + includeModule + "/src/main/AndroidManifest.xml"))) {
                                        project.type = Project.ANDROID;
                                    } else project.type = Project.JAVA;

                                }
                            } else {
                                final ObjectList<Path> javaNonGradleProject = FileManager.getFilesFromDir(projectPath.toString(), true);

                                if (javaNonGradleProject.stream()
                                        .filter(filePath ->
                                                Pattern.compile("\\.java").matcher(filePath.getFileName().toString()).find())
                                        .findFirst().orElse(null) != null) {
                                    project.type = Project.JAVA;
                                    project.isGradleSupport = false;
                                } else project.type = Project.UNKNOWN;
                            }

                            projectsFiles.add(project);
                        });

                Utils.ui(() -> {
                    data.isLoaded = true;
                    viewModel.update();
                    projectsAdapter = new ProjectsAdapter((ThemedActivity) getActivity(), projectsFiles);
                    binding.projectsList.setAdapter(projectsAdapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        data.loadThread.start();
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            binding.projectsList.setLayoutManager(new LinearLayoutManager(getContext()));
            loadProjects();

            if (data != null) binding.projectsList.getLayoutManager().onRestoreInstanceState(data.projectsState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (data == null) return;
        data.projectsState = binding.projectsList.getLayoutManager().onSaveInstanceState();
    }
}