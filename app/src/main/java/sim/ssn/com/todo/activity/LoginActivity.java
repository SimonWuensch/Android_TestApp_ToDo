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

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.CustomSharedPreferences;
import sim.ssn.com.todo.resource.User;

public class LoginActivity extends Activity {

    private static String TAG = LoginActivity.class.getSimpleName();

    private EditText etLoginName;
    private EditText etLoginPassword;
    private Button bLogin;

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