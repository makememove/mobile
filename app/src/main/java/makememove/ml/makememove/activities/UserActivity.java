package makememove.ml.makememove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.ArrayList;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.CreateEventFragment;
import makememove.ml.makememove.activities.fragments.FindEventFragment;
import makememove.ml.makememove.activities.fragments.FriendsFragment;
import makememove.ml.makememove.activities.fragments.ProfileFragment;
import makememove.ml.makememove.activities.fragments.RanklistFragment;
import makememove.ml.makememove.activities.fragments.SportEventStatusFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.datahandler.UserPack;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.SportList;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton bt_notification;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        UserMainFragment userMainFragment = new UserMainFragment();
        fragmentManager.beginTransaction().replace(R.id.content,userMainFragment,"mainFragment").addToBackStack(null).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Todo token validitásának ellenörzése

        try {
            TokenHandler ts = new TokenHandler();
            if (ts.availableToken()) {
                ts.loadToken();
                setUserData();
            } else {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void getSports(){
        DataHandler dh =  DataHandler.getInstance();
        dh.getAllSports(new Callback<SportList>() {
            @Override
            public void onResponse(Call<SportList> call, Response<SportList> response) {
                if(response.isSuccessful()){
                    SportList sportok = response.body();
                    for (Sport sport: sportok.getSports()
                         ) {
                        System.out.printf("Sport neve: "+sport.getName()+"\tSport azonosítója: "+sport.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void setUserData(){
        DataHandler dh =  DataHandler.getInstance();
        dh.setUserData(new Callback<UserPack>() {
            @Override
            public void onResponse(Call <UserPack> call, Response<UserPack> response) {
                if(response.isSuccessful()){
                    UserPack up = response.body();
                    System.out.println("User adatai:"+up.getUser().getEmail());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_events) {
             UserMainFragment userFragment= new UserMainFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        } else if (id == R.id.nav_findevent) {
             FindEventFragment userFragment= new FindEventFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        } else if (id == R.id.nav_createevent) {
             CreateEventFragment userFragment= new CreateEventFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        } else if (id == R.id.nav_friends) {
             FriendsFragment userFragment= new FriendsFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        } else if (id == R.id.nav_ranklist) {
             RanklistFragment userFragment= new RanklistFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        }else if (id == R.id.nav_profile) {
             ProfileFragment userFragment= new ProfileFragment();
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        }else if (id == R.id.nav_logout) {
             TokenHandler tokenHandler= new TokenHandler();
             tokenHandler.clear();

             Intent intent = new Intent(UserActivity.this, LoginActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
