package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.CustomSharedPreferences;
import sim.ssn.com.todo.data.FacebookManager;
import sim.ssn.com.todo.resource.User;

public class LoginActivity extends Activity {

    private EditText etLoginName;
    private EditText etLoginPassword;

    private FacebookManager facebookManager;
    private Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initVariables();
        checkSharedPrefsLogin();
    }

    private void checkSharedPrefsLogin(){
        User user = CustomSharedPreferences.getUser(this);
        if(!user.isDefaultUser()){
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            doLogin();
            this.finish();
        }
    }

    private void initVariables(){
        facebookManager = new FacebookManager(this);

        this.etLoginName = (EditText) findViewById(R.id.activity_login_etName);
        this.etLoginPassword = (EditText) findViewById(R.id.activity_login_etPassword);
        Button bLogin = (Button) findViewById(R.id.activity_login_bLogin);

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
    }

    public void doLogin(){
        startActivity(mainActivityIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int expected = CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
        if(requestCode == expected){
            facebookManager.getmCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }
}
