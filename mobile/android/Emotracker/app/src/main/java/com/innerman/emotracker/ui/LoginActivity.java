package com.innerman.emotracker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.innerman.emotracker.R;
import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.LoginDTO;
import com.innerman.emotracker.model.MessageState;
import com.innerman.emotracker.model.UserDTO;
import com.innerman.emotracker.model.WebMessage;
import com.innerman.emotracker.service.UserService;

public class LoginActivity extends ActionBarActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button signinButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppSettings.loadConfig(getResources());

        setContentView(R.layout.activity_login);
        setTitle( getString(R.string.title_activity_login) );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        emailField = (EditText) findViewById(R.id.emailField);
        emailField.addTextChangedListener(new EmptyTextWatcher(emailField, getString(R.string.email_not_null)));

        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordField.addTextChangedListener(new EmptyTextWatcher(passwordField, getString(R.string.password_not_null)));

        signinButton = (Button) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearErrors();

                boolean valid = isFormValid();
                if( !valid ) {
                    return;
                }

                setFormEnabled(false);

                new SignInHttpRequestTask().execute(getLoginDTO());
            }
        });

        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void setFormEnabled(boolean flag) {
        emailField.setEnabled(flag);
        passwordField.setEnabled(flag);
        signinButton.setEnabled(flag);
    }

    protected void clearErrors() {
        emailField.setError(null);
        passwordField.setError(null);
    }

    protected boolean isFormValid() {

        int count = 0;

        if( emailField.getText() == null ) {
            emailField.setError(getString(R.string.email_not_null));
            count++;
        }
        else {
            String s = emailField.getText().toString();
            if( s == null || s.length() <= 0 ) {
                emailField.setError(getString(R.string.email_not_null));
                count++;
            }
        }

        if( passwordField.getText() == null ) {
            passwordField.setError(getString(R.string.password_not_null));
            count++;
        }
        else {
            String s = passwordField.getText().toString();
            if( s == null || s.length() <= 0 ) {
                passwordField.setError(getString(R.string.password_not_null));
                count++;
            }
        }

        if( count == 0 ) {
            return true;
        }

        return false;
    }

    protected LoginDTO getLoginDTO() {

        LoginDTO dto = new LoginDTO();

        if( emailField.getText() != null ) {
            dto.setUserName( emailField.getText().toString().trim() );
        }

        if( passwordField.getText() != null ) {
            dto.setPassword( passwordField.getText().toString().trim() );
        }

        return dto;
    }

    protected class SignInHttpRequestTask extends AsyncTask<LoginDTO, Void, WebMessage> {

        private UserService userService = new UserService();

        @Override
        protected WebMessage doInBackground(LoginDTO... loginDTOs) {

            WebMessage<UserDTO> message = new WebMessage();

            if(loginDTOs == null || loginDTOs.length <= 0 ) {
                return message;
            }

            message = userService.signInUser(loginDTOs[0]);

            return message;
        }

        @Override
        protected void onPostExecute(WebMessage webMessage) {
            super.onPostExecute(webMessage);

            if( !webMessage.getState().equals(MessageState.OK)) {
                showMessage(webMessage.getMessage());
                setFormEnabled(true);
            }
            else {
                //show resultsActivity
                showMessage(getString(R.string.yarr));
            }
        }
    }

    protected void showMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 70);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
