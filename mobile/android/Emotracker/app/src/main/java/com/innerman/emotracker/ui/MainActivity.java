package com.innerman.emotracker.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.innerman.emotracker.R;
import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.MessageState;
import com.innerman.emotracker.model.RegistrationDTO;
import com.innerman.emotracker.model.TokenDTO;
import com.innerman.emotracker.model.WebMessage;
import com.innerman.emotracker.service.TokenService;
import com.innerman.emotracker.service.UserService;

public class MainActivity extends ActionBarActivity {

    private EditText fullnameField;
    private EditText usernameField;
    private EditText emailField;
    private EditText passwordField;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppSettings.loadConfig( getResources() );

        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_activity_main));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        fullnameField = (EditText) findViewById(R.id.fullnameField);
        fullnameField.addTextChangedListener(new EmptyTextWatcher(fullnameField, getString(R.string.fullname_not_null)));

        usernameField = (EditText) findViewById(R.id.usernameField);
        usernameField.addTextChangedListener(new EmptyTextWatcher(usernameField, getString(R.string.username_not_null)));

        emailField = (EditText) findViewById(R.id.emailField);
        emailField.addTextChangedListener(new EmptyTextWatcher(emailField, getString(R.string.email_not_null)));

        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordField.addTextChangedListener(new EmptyTextWatcher(passwordField, getString(R.string.password_not_null)));

        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearErrors();

                boolean valid = isFormValid();
                if( !valid ) {
                    return;
                }

                setFormEnabled(false);

                SignUpHttpRequestTask task = new SignUpHttpRequestTask();
                task.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    protected boolean isFormValid() {

        int count = 0;

        if( fullnameField.getText() == null ) {
            fullnameField.setError(getString(R.string.fullname_not_null));
            count++;
        }
        else {
            String s = fullnameField.getText().toString().trim();
            if( s == null || s.length() <= 0 ) {
                fullnameField.setError(getString(R.string.fullname_not_null));
                count++;
            }
        }

        if( usernameField.getText() == null ) {
            usernameField.setError(getString(R.string.username_not_null));
            count++;
        }
        else {
            String s = usernameField.getText().toString().trim();
            if( s == null || s.length() <= 0 ) {
                usernameField.setError(getString(R.string.username_not_null));
                count++;
            }
        }

        if( emailField.getText() == null ) {
            emailField.setError(getString(R.string.email_not_null));
            count++;
        }
        else {
            String s = emailField.getText().toString().trim();
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
            String s = passwordField.getText().toString().trim();
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

    protected void clearErrors() {
        fullnameField.setError(null);
        usernameField.setError(null);
        emailField.setError(null);
        passwordField.setError(null);
    }

    protected void setFormEnabled(boolean flag) {
        fullnameField.setEnabled(flag);
        usernameField.setEnabled(flag);
        emailField.setEnabled(flag);
        passwordField.setEnabled(flag);
        signupButton.setEnabled(flag);
    }

    protected void showMessage(String message) {

        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 70);
        toast.show();
    }



    protected RegistrationDTO getRegistrationDTO() {

        RegistrationDTO dto = new RegistrationDTO();

        if( fullnameField.getText() != null ) {
            dto.setFullName(fullnameField.getText().toString().trim() );
        }
        if( usernameField.getText() != null ) {
            dto.setUserName( usernameField.getText().toString().trim() );
        }
        if( emailField.getText() != null ) {
            dto.setEmail( emailField.getText().toString().trim() );
        }
        if( passwordField.getText() != null ) {
            dto.setPassword( passwordField.getText().toString().trim() );
        }

        return dto;
    }

    public class SignUpHttpRequestTask extends AsyncTask<Void, Void, WebMessage> {

        private UserService userService = new UserService();
        private TokenService tokenService = new TokenService();

        @Override
        protected WebMessage doInBackground(Void... voids) {

            WebMessage res = new WebMessage();

            WebMessage<TokenDTO> token = tokenService.createToken();
            if( token.getState().equals(MessageState.OK) ) {

                RegistrationDTO dto = getRegistrationDTO();
                dto.setTokenId(token.getResult().getId());
                dto.setKey( token.getResult().getKey() );
                dto.setToken(token.getResult().getToken());

                res = userService.signUpUser(dto);
            }
            else {
                res.setState(token.getState());
                res.setMessage(token.getMessage());
            }


            return res;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
