package fleek.code.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ObjectMap<String, DialogInterface.OnClickListener> neutralButton;
    private ObjectMap<String, DialogInterface.OnClickListener> negativeButton;
    private ObjectMap<String, DialogInterface.OnClickListener> positiveButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());

        if (icon != -1) dialog.setIcon(icon);
        if (title != null) dialog.setTitle(title);
        if (text != null) dialog.setMessage(text);

        if (neutralButton != null) dialog.setNeutralButton((String) neutralButton.keySet().toArray()[0],
                (DialogInterface.OnClickListener) neutralButton.values().toArray()[0]);
        if (negativeButton != null) dialog.setNegativeButton((String) negativeButton.keySet().toArray()[0],
                (DialogInterface.OnClickListener) negativeButton.values().toArray()[0]);
        if (positiveButton != null) dialog.setPositiveButton((String) positiveButton.keySet().toArray()[0],
                (DialogInterface.OnClickListener) positiveButton.values().toArray()[0]);

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

   public ModalDialog setNeutralButton(String name, DialogInterface.OnClickListener onClickListener) {
        neutralButton = ObjectMap.of(name, onClickListener);
        return this;
   }

    public ModalDialog setNegativeButton(String name, DialogInterface.OnClickListener onClickListener) {
        negativeButton = ObjectMap.of(name, onClickListener);
        return this;
    }

    public ModalDialog setPositiveButton(String name, DialogInterface.OnClickListener onClickListener) {
        positiveButton = ObjectMap.of(name, onClickListener);
        return this;
    }

    public ModalDialog setDismissible(boolean dismissible) {
        setCancelable(dismissible);
        return this;
    }

    public ModalDialog show(ThemedActivity activity) {
        final DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("modalDialog");

        if (fragment != null) fragment.dismissAllowingStateLoss();

        showNow(activity.getSupportFragmentManager(), "modalDialog");
        return this;
    }
}