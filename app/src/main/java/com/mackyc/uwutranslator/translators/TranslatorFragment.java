package com.mackyc.uwutranslator.translators;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mackyc.uwutranslator.R;
import com.mackyc.uwutranslator.clipboard.ClipboardHandler;
import com.mackyc.uwutranslator.database.history.HistoryObject;
import com.mackyc.uwutranslator.database.history.HistoryObjectModel;

import java.util.Locale;


public class TranslatorFragment extends Fragment implements View.OnClickListener {

    private final String TAG = this.getTag();

    private EditText translateInput, translateResult;
    private Button translateClipboardButton;
    private HistoryObjectModel historyObjectModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        historyObjectModel = ViewModelProviders.of(this).get(HistoryObjectModel.class);

        return inflater.inflate(R.layout.fragment_main_translator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        translateInput = view.findViewById(R.id.translate_edit_text);
        translateResult = view.findViewById(R.id.translate_result_edit_text);

        Button translateButton = view.findViewById(R.id.translate_button);
        translateClipboardButton = view.findViewById(R.id.translate_clipboard_button);

        translateInput.setOnClickListener(this);

        translateButton.setOnClickListener(this);
        translateClipboardButton.setOnClickListener(this);

        translateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if(isFocused) {
                    translateResult.setEnabled(false);
                }
            }
        });

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
                    HistoryObject object = new HistoryObject(translateInput.getText().toString());
                    result = object.getTranslated();

                    historyObjectModel.insert(object);

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
                break;

            default:
                Log.v(TAG, String.format(Locale.getDefault(), "onClick on viewID %s not implemented.", view.getId()));
        }
    }
}

