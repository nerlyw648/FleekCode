package fleek.code.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import fleek.code.R;
import fleek.code.activities.ThemedActivity;

public class ModalDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Dialog dialog = new Dialog(getContext(), R.style.dialogStyle);

        //dialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        //dialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.setContentView(R.layout.modal_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_corners);

        final ImageView modalIcon = dialog.findViewById(R.id.modalIcon);
        final TextView modalTitle = dialog.findViewById(R.id.modalTitle);
        final TextView modalText = dialog.findViewById(R.id.modalText);
        final MaterialButton modalButton = dialog.findViewById(R.id.modalButton);

        modalIcon.setImageResource(R.drawable.ic_folder);
        modalTitle.setText("Требуется разрешение");
        modalText.setText("Для нормальной работы с проектами требуется разрешение");
        modalButton.setText("Разрешить");

        return dialog;
    }

    public static ModalDialog show(ThemedActivity activity) {
        final ModalDialog modalDialog = new ModalDialog();
        final DialogFragment fragment = (DialogFragment) activity.getSupportFragmentManager().findFragmentByTag("modalDialog");

        if (fragment != null) fragment.dismissAllowingStateLoss();

        modalDialog.showNow(activity.getSupportFragmentManager(), "modalDialog");
        return modalDialog;
    }
}