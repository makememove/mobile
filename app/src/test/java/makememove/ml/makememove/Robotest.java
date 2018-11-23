package makememove.ml.makememove;

import android.widget.Button;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import makememove.ml.makememove.activities.LoginActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricTestRunner.class)
public class Robotest {
    private LoginActivity activity;
    private Button bt_signup;
    private Button bt_login;

    @Before
    public void setup(){
        activity=Robolectric.setupActivity(LoginActivity.class);
        bt_signup=activity.findViewById(R.id.bt_sign_up);
        bt_login = activity.findViewById(R.id.bt_login);
    }

    @Test
    public void stringmatchingtest(){
        assertThat(bt_login.getText().toString(),is("login"));
        assertThat(bt_signup.getText().toString(),is("Sign Up"));
    }


}
