package com.innerman.emotracker.ui;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by petrpopov on 09.03.14.
 */
public class BaseActivity extends ActionBarActivity {

    protected void showMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 70);
        toast.show();
    }
}
