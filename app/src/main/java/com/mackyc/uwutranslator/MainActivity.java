package com.mackyc.uwutranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText translateInput;
    EditText translateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        translateInput = findViewById(R.id.translate_edit_text);
        translateResult = findViewById(R.id.translate_result_edit_text);
    }

    public void onClickHandler(View view) {
        switch(view.getId()) {
            case R.id.translate_button:

                String result;

                if(translateInput.getText().toString().length() > 0) {
                    result = uwuTranslator.translate(translateInput.getText().toString());
                } else {
                    result = uwuTranslator.translate("There's nothing to translate! D:<");
                }

                translateResult.setText(result);
                break;
        }
    }
}
