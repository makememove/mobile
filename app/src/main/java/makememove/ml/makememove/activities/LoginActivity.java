package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import makememove.ml.makememove.R;
import makememove.ml.makememove.autentication.NormalLoginer;
import makememove.ml.makememove.autentication.NormalRegister;

public class LoginActivity extends AppCompatActivity {

    private  Button bt_sign_up;
    private Button login;
    private EditText user;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_sign_up= findViewById(R.id.bt_sign_up);
        user = findViewById(R.id.et_user_and_email);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.bt_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    NormalLoginer nloginer = new NormalLoginer();
                    nloginer.login(email.getText().toString(),user.getText().toString(),password.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

    }
}
