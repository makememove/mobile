package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                if (validation(et_username.getText().toString(),et_email.getText().toString(),et_password.getText().toString(),et_confirmpassword.getText().toString())) {
                    Snackbar.make(findViewById(R.id.regist_coorlayout), "Please wait...", Snackbar.LENGTH_LONG).show();
                    NormalAuth nAuth = new NormalAuth();
                    nAuth.signup(et_email.getText().toString(),et_username.getText().toString(),et_password.getText().toString(), new Callback<TokenDocument>() {

                        @Override
                        public void onResponse(Call<TokenDocument> call, Response <TokenDocument>response) {

                            TokenHandler tokenHandler=new TokenHandler();
                            if(response.isSuccessful()){
                                tokenHandler.setToken(response.body().getToken());
                                setUserType(response.body().getType());
                            }

                            if(tokenHandler.availableToken()) {
                                Intent intent = new Intent(RegistrationActivity.this, UserActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else Snackbar.make(findViewById(R.id.regist_coorlayout), "Username/Email already exist", Snackbar.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Snackbar.make(findViewById(R.id.regist_coorlayout), "Can't connect you to the server", Snackbar.LENGTH_LONG).show();

                        }
                    });
                }

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

    private  boolean validation(String username,String email, String p1,String p2){

        if(username.length()<5){
            et_username.setError("Username must be at least 5 character!");
            et_username.requestFocus();
            return false;
        }

        if(username.length()>20){
            et_username.setError("Username must be at most 20 character!");
            et_username.requestFocus();
            return false;
        }

        if(!isEmailValid(email)){
            et_email.setError("Not valid email Address!");
            et_email.requestFocus();
            return false;
        }

        if(5>p1.length()){
            et_password.setError("Password must be at least 5 character!");
            et_password.requestFocus();
            return false;
        }

        if(p1.length()>30){
            et_password.setError("Password must be at most 30 character!");
            et_password.requestFocus();
            return false;
        }

        if(!p2.equals(p1)){
            et_confirmpassword.setError("Confirm password must be the same!");
            et_confirmpassword.requestFocus();
            return false;
        }

        return true;
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
