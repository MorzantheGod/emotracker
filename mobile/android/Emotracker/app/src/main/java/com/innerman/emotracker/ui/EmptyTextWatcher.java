package com.innerman.emotracker.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by petrpopov on 08.03.14.
 */
public class EmptyTextWatcher implements TextWatcher {

    private final EditText field;
    private final String message;

    public EmptyTextWatcher(EditText field, String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if( field.getText() == null ) {
            field.setError(message);
        }
        else {
            String s = field.getText().toString();
            if( s == null || s.length() <= 0 ) {
                field.setError(message);
            }
            else {
                field.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
