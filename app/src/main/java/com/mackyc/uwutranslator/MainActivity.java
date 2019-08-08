package com.mackyc.uwutranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mackyc.uwutranslator.R;
import com.mackyc.uwutranslator.clipboardhandler.Clipboard;
import com.mackyc.uwutranslator.translatormodules.uwuTranslator;

public class MainActivity extends AppCompatActivity {

    EditText translateInput, translateResult;
    Button translateClipboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        translateInput = findViewById(R.id.translate_edit_text);
        translateResult = findViewById(R.id.translate_result_edit_text);

        translateClipboardButton = findViewById(R.id.translate_clipboard_button);
    }

    public void onClickHandler(View view) {

        switch (view.getId()) {

            // This handles the click of the translate button.
            case R.id.translate_button:

                String result;

                // This if statement checks if there is some text to translate
                if (translateInput.getText().toString().length() > 0) {
                    // In this case, the user has something to translate.
                    result = uwuTranslator.translate(translateInput.getText().toString());
                    translateClipboardButton.setEnabled(true);
                } else {
                    // In this case, the user didn't have anything to translate.
                    result = uwuTranslator.translate("There's nothing to translate! D:<");
                    translateClipboardButton.setEnabled(false);
                }

                translateResult.setText(result);
                break;

            case R.id.translate_clipboard_button:
                result = uwuTranslator.translate(translateInput.getText().toString());
                Clipboard.addPlainText(this, result);
                break;
        }
    }
}
