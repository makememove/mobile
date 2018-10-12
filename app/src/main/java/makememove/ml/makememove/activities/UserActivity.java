package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import makememove.ml.makememove.GlobalClass;
import makememove.ml.makememove.R;
import makememove.ml.makememove.datahandlers.users.TokenSaver;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        try {
            User.getInstance().setInstance(new Normal());
            TokenSaver ts = new TokenSaver();
            if (ts.availableToken()) {
                ts.loadToken();
            } else {
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
