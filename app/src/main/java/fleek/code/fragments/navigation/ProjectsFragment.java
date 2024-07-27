package fleek.code.fragments.navigation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.nio.file.Path;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.FragmentProjectsBinding;
import fleek.code.databinding.ItemProjectBinding;
import fleek.code.ui.adapters.ProjectsAdapter;
import fleek.code.utils.FileManager;
import fleek.code.utils.ObjectList;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;


    public ProjectsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private ProjectsAdapter projectsAdapter;

    @Override
    public void onResume() {
        super.onResume();

        try {
            final ObjectList<Path> projectsFiles = FileManager.getFilesFromDir(false);
            projectsAdapter = new ProjectsAdapter((ThemedActivity) getActivity(), projectsFiles);
            binding.projectsList.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.projectsList.setAdapter(projectsAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}