package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import makememove.ml.makememove.R;
import makememove.ml.makememove.autentication.inner.NormalAuth;
import makememove.ml.makememove.datahandler.TokenHandler;

public class LoginActivity extends AppCompatActivity {

    private  Button bt_sign_up;
    private Button bt_login;
    private EditText et_user;
    private EditText et_email;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_sign_up= findViewById(R.id.bt_sign_up);
        et_user = findViewById(R.id.et_user_and_email);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    NormalAuth nAuth = new NormalAuth();
                    nAuth.login(et_email.getText().toString(),et_user.getText().toString(),et_password.getText().toString());

                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
