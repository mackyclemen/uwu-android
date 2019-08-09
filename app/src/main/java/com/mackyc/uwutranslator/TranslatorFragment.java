package com.mackyc.uwutranslator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.mackyc.uwutranslator.clipboard.ClipboardHandler;
import com.mackyc.uwutranslator.translators.uwuTranslator;


public class TranslatorFragment extends Fragment implements View.OnClickListener {

    private EditText translateInput, translateResult;
    private Button translateButton, translateClipboardButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_translator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        translateInput = view.findViewById(R.id.translate_edit_text);
        translateResult = view.findViewById(R.id.translate_result_edit_text);

        translateButton = view.findViewById(R.id.translate_button);
        translateClipboardButton = view.findViewById(R.id.translate_clipboard_button);

        translateInput.setOnClickListener(this);

        translateButton.setOnClickListener(this);
        translateClipboardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            // This handles the click of the translate button.
            case R.id.translate_button:

                String result;

                // This if statement checks if there is some text to translate
                if(translateInput.getText().toString().length() > 0) {
                    // In this case, the user has something to translate.
                    result = uwuTranslator.translate(translateInput.getText().toString());
                    translateClipboardButton.setEnabled(true);
                } else {
                    // In this case, the user didn't have anything to translate.
                    result = uwuTranslator.translate("There's nothing to translate! D:<");
                    translateClipboardButton.setEnabled(false);
                }

                translateResult.setEnabled(true);
                translateResult.setText(result);
                break;

            case R.id.translate_clipboard_button:
                result = uwuTranslator.translate(translateInput.getText().toString());
                ClipboardHandler.addPlainText(view.getContext(), result);
                break;

            case R.id.translate_edit_text:
                translateResult.setEnabled(false);
        }
    }
}

