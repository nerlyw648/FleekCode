package fleek.code.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.activities.project.CreateProjectActivity;
import fleek.code.databinding.ItemTemplateBinding;
import fleek.code.models.Template;
import fleek.code.utils.ObjectList;
import fleek.code.utils.Utils;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.Holder> {

    private final ThemedActivity activity;
    private final ObjectList<Template> templates;

    public TemplatesAdapter(ThemedActivity activity, ObjectList<Template> templates) {
        this.activity = activity;
        this.templates = templates;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public ItemTemplateBinding binding;

        public Holder(ItemTemplateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemTemplateBinding.inflate(activity.getLayoutInflater(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Template template = templates.get(position);

        holder.binding.templateImage.setImageDrawable(template.preview);
        holder.binding.templateName.setText(template.name);
        holder.binding.templateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(activity, CreateProjectActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }
}