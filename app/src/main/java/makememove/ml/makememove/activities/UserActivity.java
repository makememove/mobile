package makememove.ml.makememove.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import makememove.ml.makememove.R;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;

public class UserActivity extends AppCompatActivity {
    private Button bt_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Todo token validitásának ellenörzése

        try {
            TokenHandler ts = new TokenHandler();
            if (ts.availableToken()) {
                ts.loadToken();
            } else {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bt_logout=findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TokenHandler tokenHandler= new TokenHandler();
                tokenHandler.clear();

                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
