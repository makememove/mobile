package makememove.ml.makememove.activities.fragments;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.dpsystem.presenters.SportPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.adapters.SportAdapter;
import makememove.ml.makememove.persistence.SportItem;
import makememove.ml.makememove.persistence.SportListDatabase;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.user.User;

public class UserMainFragment extends Fragment implements SportAdapter.SportItemClickListener, BaseView {
    private ImageButton bt_addsport;
    private View Layout;
    private RecyclerView recyclerView;
    private SportAdapter adapter;
    private SportListDatabase database;
    private static SportListDocument sports;
    private static SportListDocument preferredSports;
    private static List<Sport> sportList;
    private static List<String> preferredSportList;
    private String token = User.getInstance().getToken();

    public static ArrayList<String> getAllSports(){
        if(sportList!=null){
            ArrayList<String> sportlistinstrings = new ArrayList<String>();
            for(int i=0;i<sportList.size();i++){
                sportlistinstrings.add(sportList.get(i).getName());
            }
            return sportlistinstrings;
        }
            return null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportList = new ArrayList<Sport>();
        preferredSportList = new ArrayList<String>();
        sports = new SportListDocument();
        preferredSports = new SportListDocument();
        database = Room.databaseBuilder(
                GlobalClass.context,
                SportListDatabase.class,
                "sport-list"
        ).fallbackToDestructiveMigration().build();
        initSports(token);
    }

    public static int getPosition(String item){
        for(int i = 0;i<sportList.size();i++){
            if(sportList.get(i).getName().equals(item))
                return i;
        }
        return -1;
    }

    public static int getPreferredPosition(String item){
        for(int i = 0;i<preferredSportList.size();i++){
            if(preferredSportList.get(i).equals(item))
                return i;
        }
        return -1;
    }

    public static int getListSize(){
        return sportList.size();
    }
    public static String getName(int position){
        if(position != -1)
            return sportList.get(position).getName();
        return "SportName";
    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.RecylerView);
        adapter = new SportAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<SportItem>>() {

            @Override
            protected List<SportItem> doInBackground(Void... voids) {
                List<SportItem> list = database.sportItemDao().getAll();
            return list;
            }

            @Override
            protected void onPostExecute(List<SportItem> sportItems) {
                adapter.update(sportItems);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void addPreferredSports(){
        new AsyncTask<Void, Void, List<SportItem>>() {
            @Override
            protected List<SportItem> doInBackground(Void... voids) {
                return database.sportItemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<SportItem> sportItems) {
                for (SportItem item:sportItems) {
                    preferredSportList.add(item.category);
                }
            }
        }.execute();

    }

    private void initPreferredSports(String token){
        addPreferredSports();

        if(recyclerView != null) {
            if (recyclerView.getAdapter().getItemCount() == 0) {
                SportPresenter sp = new SportPresenter(preferredSports);
                preferredSports.attach(this);
                sp.getUserPreferredSports(token);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemChanged(final SportItem item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.sportItemDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("UserMainFragment", "SportItem update was successful");
            }
        }.execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usermain_fragment, container, false);
    }

    public void addSports(List<Sport> item){
        sportList.addAll(item);
    }

    public void initSports(final String token){
      //  SportListDocument sportok = new SportListDocument();
        SportPresenter sp = new SportPresenter(sports);
        sports.attach(this);
        sp.getAllSports(token);
    }


    public void followSport(String token, int position){
        PostPresenter pp = new PostPresenter();
        pp.postPreferredSport(token,position);
    }


    public void unfollowSport(String token, int position){
        PostPresenter pp = new PostPresenter();
        pp.unpostPreferredSport(token,position);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {
          //      k++;
            initRecylerView();
            final String token = User.getInstance().getToken();
            bt_addsport = this.getView().findViewById(R.id.bt_addsport);
            if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
            else bt_addsport.setVisibility(View.VISIBLE);
            bt_addsport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AlertDialogCustom));
                    dialog.setTitle("Choose a Sport: ");
                    String[] arrayItems = null;
                    arrayItems = new String[sportList.size()];
                    int i = 0;
                    for (Sport item:sportList) {
                        arrayItems[i]= item.getName();
                        i++;
                    }
                    dialog.setItems(arrayItems, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            if(!preferredSportList.contains(sportList.get(position).getName())) {
                                onShoppingItemCreated(getSportItem(position));
                                followSport(token, position + 1);
                                preferredSportList.add(sportList.get(position).getName());
                            }
                            else
                                Snackbar.make(getActivity().findViewById(R.id.content), "The selected sport is already in the preferred list!", Snackbar.LENGTH_LONG).show();
                        }

                    });
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
            });
        }
    }
    private SportItem getSportItem(int position) {
        SportItem sportItem = new SportItem();
        sportItem.category = sportList.get(position).getName();
        return sportItem;
    }
    @SuppressLint("StaticFieldLeak")
    public void onShoppingItemCreated(final SportItem newItem) {
        new AsyncTask<Void, Void, SportItem>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected SportItem doInBackground(Void... voids) {
                newItem.id = database.sportItemDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(SportItem sportItem) {
                adapter.addItem(sportItem);
                if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
                else bt_addsport.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void onItemRemoved(final SportItem item)
    {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.sportItemDao().deleteItem(item);

                preferredSportList.remove(item.category);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
                else bt_addsport.setVisibility(View.VISIBLE);
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }

    @Override
    public void onUnfollow(int position) {
        unfollowSport(token, position);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onAllItemsRemoved() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.sportItemDao().deleteAll();
                preferredSportList.clear();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
                else bt_addsport.setVisibility(View.VISIBLE);
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }

    @Override
    public void update() {
     //   addSports(sportok.getSports());
        if(sportList.size()==0 ) {
            addSports(sports.getSports());
            initPreferredSports(token);
        }

        if(preferredSportList.size()==0&&preferredSports.getSports().size()!=0){
            for (Sport sport : preferredSports.getSports()) {
                onShoppingItemCreated(getSportItem(sport.getId() - 1));
                preferredSportList.add(sportList.get(sport.getId() - 1).getName());
            }
        }


    }
}

