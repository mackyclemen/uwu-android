package com.mackyc.uwutranslator.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogHandler extends DialogFragment {

    public String TAG = this.getTag();
    private String message = "DialogFragment message";
    private String title;
    private String positiveButton, negativeButton, neutralButton;

    public DialogHandler() {
        setRetainInstance(true);
    }

    public interface OnDialogAnswerListener {
        void onPositiveClick();
        void onNeutralClick();
        void onNegativeClick();
    }

    private OnDialogAnswerListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setMessage(message);

        if(title != null) {
            builder.setTitle(title);
        }

        if(positiveButton != null) {
            builder.setPositiveButton(positiveButton, (dialogInterface, i) -> listener.onPositiveClick());
        }

        if(neutralButton != null) {
            builder.setNeutralButton(neutralButton, (dialogInterface, i) -> listener.onNeutralClick());
        }

        if(negativeButton != null) {
            builder.setNegativeButton(negativeButton, (dialogInterface, i) -> listener.onNegativeClick());
        }

        return builder.create();
    }

    public void setOnDialogAnswerListener(DialogHandler.OnDialogAnswerListener listener) {
        this.listener = listener;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    public void setNeutralButton(String neutralButton) {
        this.neutralButton = neutralButton;
    }
}
