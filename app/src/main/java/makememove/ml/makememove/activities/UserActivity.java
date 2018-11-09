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
import android.widget.ImageView;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.CreateEventFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.FindEventFragment;
import makememove.ml.makememove.activities.fragments.FriendsFragment;
import makememove.ml.makememove.activities.fragments.ProfileFragment;
import makememove.ml.makememove.activities.fragments.RanklistFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.UnfinishedEventFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.presenters.DataHandler;
import makememove.ml.makememove.persistence.SportAdapter;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton bt_notification;
    private SportAdapter.SportItemClickListener listener;
    public static FragmentManager fragmentManager;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        fragmentManager = getSupportFragmentManager();
        UserMainFragment userMainFragment = new UserMainFragment();
        listener = userMainFragment;
        fragmentManager.beginTransaction().replace(R.id.content,userMainFragment,"mainFragment").commit();
    }

    public void setUserData(){
        DataHandler dh =  DataHandler.getInstance();
        dh.setUserData(new Callback<UserDocument>() {
            @Override
            public void onResponse(Call <UserDocument> call, Response<UserDocument> response) {
                if(response.isSuccessful()){
                    UserDocument up = response.body();
                    User.setEveryThing(up.getUser());

                    TextView username=drawer.findViewById(R.id.tv_usernamemenu);
                    username.setText(response.body().getUser().getUserName());

                    TextView level=drawer.findViewById(R.id.tv_levelnumbermenu);
                    level.setText(Integer.toString(response.body().getUser().getLevel()));

                    TextView xp=drawer.findViewById(R.id.tv_xpmenu);
                    xp.setText(Integer.toString(response.body().getUser().getExperience()));

                    ImageView picture=drawer.findViewById(R.id.iv_profilemenu);
                    if(response.body().getUser().getPicture()!=null) {
                        //TODO profile picture beállítása
                    }



                }
                else{
                    Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
             listener = userFragment;
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
         } else if (id == R.id.nav_unfinishedevent) {
             UnfinishedEventFragment userFragment= new UnfinishedEventFragment();
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

             listener.onAllItemsRemoved();

             Intent intent = new Intent(UserActivity.this, LoginActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
