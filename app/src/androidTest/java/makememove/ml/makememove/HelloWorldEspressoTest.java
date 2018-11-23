package makememove.ml.makememove;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import makememove.ml.makememove.activities.LoginActivity;
import makememove.ml.makememove.activities.UserActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void headertest() {
        onView(withId(R.id.iv_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.et_user_and_email)).check(matches(isDisplayed()));
        onView(withId(R.id.et_password)).check(matches(isDisplayed()));
        onView(withId(R.id.et_user_and_email)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.bt_sign_up),withText("Sign Up"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id. bt_login),withText("LOGIN"))).check(matches(isDisplayed()));

    }
}
