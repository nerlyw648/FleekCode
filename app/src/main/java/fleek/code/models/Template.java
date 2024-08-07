package fleek.code.models;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.nio.file.Paths;

import fleek.code.App;
import fleek.code.utils.storage.AssetHelper;
import fleek.code.utils.ObjectList;

public class Template {

    public Drawable preview;
    public String name;

    public static ObjectList<Template> getTemplates() {
        final ObjectList<Template> templates = ObjectList.of();

        try {
            final ObjectList<String> templatesDirs = AssetHelper.getAssetDirectories(App.getContext(), "templates");

            for (final String path : templatesDirs) {
                final Template template = new Template();
                template.preview = AssetHelper.getDrawableFromAssets(path + "/icon.png");
                template.name = Paths.get(path).getFileName().toString();
                templates.add(template);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return templates;
    }
}