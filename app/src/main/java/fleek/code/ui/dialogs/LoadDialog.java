package fleek.code.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;

public class LoadDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.dialog_load);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().setDimAmount(0.8f);

        return dialog;
    }

    public LoadDialog setDismissible(boolean dismissible) {
        setCancelable(dismissible);
        return this;
    }

    private static void findDialogAndDismiss(ThemedActivity activity) {
        final DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("modalDialog");
        if (fragment != null) fragment.dismissAllowingStateLoss();
    }

    public static void hide(ThemedActivity activity) {
        findDialogAndDismiss(activity);
    }

    public static LoadDialog show(ThemedActivity activity) {
        final LoadDialog loadDialog = new LoadDialog();
        findDialogAndDismiss(activity);

        loadDialog.setDismissible(false);
        loadDialog.showNow(activity.getSupportFragmentManager(), "modalDialog");

        return loadDialog;
    }
}