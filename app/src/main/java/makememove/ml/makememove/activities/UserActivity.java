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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.CreateEventFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.FindEventFragment;
import makememove.ml.makememove.activities.fragments.FriendsFragment;
import makememove.ml.makememove.activities.fragments.ProfileFragment;
import makememove.ml.makememove.activities.fragments.RanklistFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.FinishedEventsFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.UnfinishedEventFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.datahandler.TokenHandler;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.presenters.DataHandler;
import makememove.ml.makememove.adapters.SportAdapter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.user.Admin;
import makememove.ml.makememove.user.Creator;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseView {

    private SportAdapter.SportItemClickListener listener;
    public static FragmentManager fragmentManager;
    ActionBarDrawerToggle toggle;
    private ImageButton ib_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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

    private void initfragmentmanager(){
        fragmentManager = getSupportFragmentManager();
        UserMainFragment userMainFragment = new UserMainFragment();
        listener = userMainFragment;
        fragmentManager.beginTransaction().replace(R.id.content,userMainFragment,"mainFragment").commit();
    }

    private void initDrawer(Response<UserDocument> response){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

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

    private void initNotification(){
        ib_notification=findViewById(R.id.ib_notification);

        NotificationFragment.document.attach(this);
        NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
        np.getNotifications(User.getInstance().getToken());

            ib_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, new NotificationFragment(), "not")
                            .commit();
                }
            });

    }


    public void setUserData(){
        DataHandler dh =  DataHandler.getInstance();
        dh.setUserData(new Callback<UserDocument>() {
            @Override
            public void onResponse(Call <UserDocument> call, Response<UserDocument> response) {
                if(response.isSuccessful()){
                    UserDocument up = response.body();
                    setUserType(response.body().getUser().getType());
                    User.setEveryThing(up.getUser());
                    initDrawer(response);
                    initfragmentmanager();
                    initNotification();
                }
                else{
                    Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        } else if (id == R.id.nav_createevent) {
             CreateEventFragment userFragment= new CreateEventFragment();
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
         } else if (id == R.id.nav_unfinishedevent) {
             UnfinishedEventFragment userFragment= new UnfinishedEventFragment();
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
         } else if (id == R.id.nav_finishedevent) {
             FinishedEventsFragment userFragment= new FinishedEventsFragment();
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             fragmentManager.beginTransaction()
                     .replace(R.id.content, userFragment)
                     .commit();
        }else if (id == R.id.nav_profile) {
             ProfileFragment userFragment= new ProfileFragment();
             fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

    @Override
    public void update() {
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if(NotificationFragment.document.getNotifications().size()!=0) {
            ib_notification.startAnimation(animShake);
        }
        else ib_notification.clearAnimation();

    }
}
