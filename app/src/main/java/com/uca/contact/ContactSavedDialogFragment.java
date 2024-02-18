package com.uca.contact;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ContactSavedDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Contact saved!")
                .setMessage("Contact saved!")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                });
        return builder.create();
    }
}
