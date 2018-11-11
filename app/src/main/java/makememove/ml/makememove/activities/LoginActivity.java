package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import makememove.ml.makememove.R;
import makememove.ml.makememove.autentication.inner.NormalAuth;
import makememove.ml.makememove.dpsystem.documents.TokenDocument;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.user.Admin;
import makememove.ml.makememove.user.Creator;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private  Button bt_sign_up;
    private Button bt_login;
    private EditText et_user_and_email;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_sign_up= findViewById(R.id.bt_sign_up);
        et_user_and_email = findViewById(R.id.et_user_and_email);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(findViewById(R.id.login_coorlayout), "Please wait...", Snackbar.LENGTH_LONG).show();

                final NormalAuth nAuth = new NormalAuth();
                nAuth.login(et_user_and_email.getText().toString(),et_user_and_email.getText().toString(),et_password.getText().toString(), new Callback<TokenDocument>() {
                    @Override
                    public void onResponse(Call<TokenDocument> call, Response<TokenDocument> response) {
                        TokenHandler tokenHandler = new TokenHandler();
                        if(response.isSuccessful()) {
                            tokenHandler.setToken(response.body().getToken());
                            setUserType(response.body().getType());
                        }

                        if(tokenHandler.availableToken()) {
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                            Snackbar.make(findViewById(R.id.login_coorlayout), "Invalid Username/Email or password", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Snackbar.make(findViewById(R.id.login_coorlayout), "Can't connect you to the server", Snackbar.LENGTH_LONG).show();
                    }
                });

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
    public void setUserType(int type){
        switch(type){
            case 0:
                User.getInstance().setUserType(new Admin());
                User.getInstance().setUserString("Admin");
                break;
            case 1:
                User.getInstance().setUserType(new Creator());
                User.getInstance().setUserString("Creator");
                break;
            case 2:
                User.getInstance().setUserType(new Normal());
                User.getInstance().setUserString("Normal");
                break;
            default:
                User.getInstance().setUserType(new Normal());
                User.getInstance().setUserString("Normal");
                break;
        }
    }
}
