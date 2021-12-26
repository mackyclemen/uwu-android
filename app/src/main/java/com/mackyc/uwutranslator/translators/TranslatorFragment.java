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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.mackyc.uwutranslator.R;
import com.mackyc.uwutranslator.clipboard.ClipboardHandler;
import com.mackyc.uwutranslator.database.history.HistoryObject;
import com.mackyc.uwutranslator.database.history.HistoryObjectFactory;
import com.mackyc.uwutranslator.database.history.HistoryObjectModel;

import java.util.Locale;


public class TranslatorFragment extends Fragment {

    private final String TAG = this.getTag();

    private HistoryObjectModel historyObjectModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        HistoryObjectFactory factory = new HistoryObjectFactory(requireActivity().getApplication());
        historyObjectModel = new ViewModelProvider(this, factory).get(HistoryObjectModel.class);

        return inflater.inflate(R.layout.fragment_main_translator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final EditText translateInput = view.findViewById(R.id.translate_edit_text);
        final EditText translateResult = view.findViewById(R.id.translate_result_edit_text);

        final Button translateClipboardButton = view.findViewById(R.id.translate_clipboard_button);
        final Button translateButton = view.findViewById(R.id.translate_button);
        translateButton.setOnClickListener(btnTranslateView -> {
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
        });

        translateClipboardButton.setOnClickListener(btnClipboard -> {
            String result = uwuTranslator.translate(translateInput.getText().toString());
            ClipboardHandler.addPlainText(btnClipboard.getContext(), result);
        });

        translateInput.setOnClickListener(inputTranslateView -> translateResult.setEnabled(false));

        translateInput.setOnFocusChangeListener((inputTranslateView, isFocused) -> {
            if(isFocused) {
                translateResult.setEnabled(false);
            }
        });
    }
}

