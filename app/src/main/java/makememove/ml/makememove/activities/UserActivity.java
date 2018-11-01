package makememove.ml.makememove.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.autentication.inner.NormalAuth;
import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.SportList;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private ImageButton bt_menu;
    private ImageButton bt_notification;
    private PopupWindow menu;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d("Value",""+1);

        fragmentManager = getSupportFragmentManager();
        UserMainFragment userMainFragment = new UserMainFragment();
        fragmentManager.beginTransaction().replace(R.id.content,userMainFragment,"mainFragment").addToBackStack(null).commit();

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


        DataHandler dh = DataHandler.getInstance();
        dh.getAllSports(new Callback<SportList>() {
            @Override
            public void onResponse(Call<SportList> call, Response<SportList> response) {

                if(response.isSuccessful()) {
                    SportList sportok = response.body();
                    for (Sport sport: sportok.getSports()
                            ) {
                        System.out.printf(sport.getName()+" "+sport.getId()+"Sport");
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                 // Snackbar.make(findViewById(R.id.login_coorlayout), "Can't connect you to the server", Snackbar.LENGTH_LONG).show();
            }
        });

       bt_menu=findViewById(R.id.bt_menu);
       bt_menu.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               LayoutInflater inflater = getLayoutInflater();
               LinearLayout header= findViewById(R.id.header);
               menu = new PopupWindow(inflater.inflate(R.layout.menu ,null, false),Resources.getSystem().getDisplayMetrics().widthPixels/2,FrameLayout.LayoutParams.MATCH_PARENT, true);
               menu.showAtLocation(findViewById(R.id.content), Gravity.TOP|Gravity.LEFT , 0,0);
               Button bt_logout=menu.getContentView().findViewById(R.id.bt_logout);
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
       });



    }
}
