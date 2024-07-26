package fleek.code.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;
import fleek.code.utils.ObjectList;
import fleek.code.utils.ObjectMap;

public class ModalDialog extends DialogFragment {
    private int icon = -1;
    private String title, text;
    private ObjectMap<String, DialogInterface.OnClickListener> buttons;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        if (icon != -1) dialog.setIcon(icon);
        if (title != null) dialog.setTitle(title);
        if (text != null) dialog.setMessage(text);

        if (buttons != null) buttons.forEach(dialog::setPositiveButton);

        return dialog.create();
    }

    public static ModalDialog create() {
        return new ModalDialog();
    }

    public ModalDialog setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public ModalDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ModalDialog setText(String text) {
        this.text = text;
        return this;
    }

    public ModalDialog setButtons(ObjectMap<String, DialogInterface.OnClickListener> buttons) {
        this.buttons = buttons;
        return this;
    }

    public ModalDialog show(ThemedActivity activity) {
        final DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("modalDialog");

        if (fragment != null) fragment.dismissAllowingStateLoss();

        showNow(activity.getSupportFragmentManager(), "modalDialog");
        return this;
    }
}