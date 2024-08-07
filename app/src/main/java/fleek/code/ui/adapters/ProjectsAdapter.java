package fleek.code.ui.adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.activities.project.ProjectActivity;
import fleek.code.databinding.ItemProjectBinding;
import fleek.code.models.Project;
import fleek.code.utils.ObjectList;
import fleek.code.utils.Utils;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.Holder> {

    private final ThemedActivity activity;
    private final ObjectList<Project> projects;

    public ProjectsAdapter(ThemedActivity activity, ObjectList<Project> projects) {
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
        final Project project = projects.get(position);
        holder.binding.projectName.setText(project.name);

        if (project.type.equals(Project.ANDROID)) {
            holder.binding.projectIcon.setImageTintList(ColorStateList.valueOf(activity.getColor(R.color.colorGreen)));
            holder.binding.projectIcon.setImageResource(R.drawable.ic_android);
            holder.binding.projectType.setText("Android project");
        }
        if (project.type.equals(Project.JAVA)) {
            holder.binding.projectIcon.setImageTintList(ColorStateList.valueOf(activity.getColor(R.color.colorOrange)));
            holder.binding.projectIcon.setImageResource(R.drawable.ic_java);
            holder.binding.projectType.setText(project.isGradleSupport ? "Java project (with Gradle)" : "Java project");
        }
        if (project.type.equals(Project.UNKNOWN)) {
            holder.binding.projectIcon.setImageTintList(ColorStateList.valueOf(activity.getColor(R.color.colorOrange)));
            holder.binding.projectIcon.setImageResource(R.drawable.ic_question);
            holder.binding.projectType.setText("Other project...");
        }

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            private final Project _project = project;

            @Override
            public void onClick(View view) {
                Utils.startActivity(activity, new Intent(activity, ProjectActivity.class)
                        .putExtra("projectName", _project.name));
            }
        };

        holder.binding.getRoot().setOnClickListener(onClickListener);
        holder.binding.projectOpen.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
