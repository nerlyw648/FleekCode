package fleek.code.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.Path;

import fleek.code.activities.ThemedActivity;
import fleek.code.databinding.ItemProjectBinding;
import fleek.code.utils.ObjectList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.Holder> {

    private ThemedActivity activity;
    private ObjectList<Path> projects;

    public ProjectsAdapter(ThemedActivity activity, ObjectList<Path> projects) {
        this.activity = activity;
        this.projects = projects;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public ItemProjectBinding binding;

        public Holder(ItemProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemProjectBinding.inflate(activity.getLayoutInflater(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Path project = projects.get(position);
        holder.binding.projectName.setText(project.getFileName().toString());
        holder.binding.projectType.setText("Android Project");
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
