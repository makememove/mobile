package makememove.ml.makememove.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import makememove.ml.makememove.R;
import makememove.ml.makememove.autentication.inner.NormalAuth;
import makememove.ml.makememove.datahandler.AuthTokenpack;
import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.datahandler.TokenHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirmpassword;
    private Button bt_registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_username= findViewById(R.id.et_username);
        et_email= findViewById(R.id.et_email);
        et_password= findViewById(R.id.et_password);
        et_confirmpassword= findViewById(R.id.et_confirmpassword);
        bt_registrate = findViewById(R.id.bt_registrate);


        bt_registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_password.getText().toString().equals(et_confirmpassword.getText().toString())) {

                    NormalAuth nAuth = new NormalAuth();
                    nAuth.signup(et_email.getText().toString(),et_username.getText().toString(),et_password.getText().toString(), new Callback<AuthTokenpack>() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            if(response.isSuccessful())DataHandler.getInstance().setToken(response);
                            TokenHandler tokenHandler=new TokenHandler();

                            if(tokenHandler.availableToken()) {
                                Intent intent = new Intent(RegistrationActivity.this, UserActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }

            }
        });
    }
}
