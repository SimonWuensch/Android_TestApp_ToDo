package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.CustomSharedPreferences;
import sim.ssn.com.todo.data.FacebookManager;
import sim.ssn.com.todo.resource.User;

public class LoginActivity extends Activity {

    private final static int FACEBOOK_REQUEST_CODE = 20;
    private static String TAG = LoginActivity.class.getSimpleName();

    private EditText etLoginName;
    private EditText etLoginPassword;
    private Button bLogin;

    private LoginButton buttonToLoginWithFacebook;
    private CallbackManager mCallbackManager;

    private Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVariables();
        checkSharedPrefsLogin();
    }

    private void checkSharedPrefsLogin(){
        User user = CustomSharedPreferences.getUser(this);
        if(!user.isDefaultUser()){
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainActivityIntent);
            this.finish();
        }
    }

    private void initVariables(){
        this.etLoginName = (EditText) findViewById(R.id.activity_login_etName);
        this.etLoginPassword = (EditText) findViewById(R.id.activity_login_etPassword);
        this.bLogin = (Button) findViewById(R.id.activity_login_bLogin);

        mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginName = etLoginName.getText().toString();
                String loginPassword = etLoginPassword.getText().toString();

                if(loginName.length() > 0 && loginPassword.length() > 0) {
                    CustomSharedPreferences.addUser(LoginActivity.this, new User(loginName, loginPassword));
                    startActivity(mainActivityIntent);
                }else{
                    Toast.makeText(LoginActivity.this, "Login fehlgeschlagen! Bitte Logindaten überprüfen.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCallbackManager = CallbackManager.Factory.create();

        buttonToLoginWithFacebook = (LoginButton) findViewById(R.id.activity_login_bFaceBook);
        buttonToLoginWithFacebook.setReadPermissions("public_profile","email","user_birthday");
        buttonToLoginWithFacebook.registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FB", "Access Token: " + loginResult.getAccessToken());
                        FacebookManager.getFacebookProfile(loginResult.getAccessToken());
                    }
                    @Override
                    public void onCancel() {
                        Log.d("FB", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        e.printStackTrace();
                        Log.d("FB", "Error");
                    }
                }, FACEBOOK_REQUEST_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int expected = CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
        if( requestCode == expected ){
            mCallbackManager.onActivityResult(requestCode,resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
