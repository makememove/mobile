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
import makememove.ml.makememove.dpsystem.presenters.DataHandler;
import makememove.ml.makememove.dpsystem.presenters.SportPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.persistence.SportAdapter;
import makememove.ml.makememove.persistence.SportItem;
import makememove.ml.makememove.persistence.SportListDatabase;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMainFragment extends Fragment implements SportAdapter.SportItemClickListener{
    private ImageButton bt_addsport;
    private View Layout;
    private RecyclerView recyclerView;
    private SportAdapter adapter;
    private SportListDatabase database;
    private static List<Sport> sportList;
    private static List<String> preferredSportList;
    private String token = User.getInstance().getToken();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportList = new ArrayList<Sport>();
        preferredSportList = new ArrayList<String>();
        database = Room.databaseBuilder(
                GlobalClass.context,
                SportListDatabase.class,
                "sport-list"
        ).fallbackToDestructiveMigration().build();
        getSports(token);
    }

    public static int getPosition(String item){
        for(int i = 0;i<sportList.size();i++){
            if(sportList.get(i).getName().equals(item))
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
                DataHandler dh = DataHandler.getInstance();
                dh.getUserPreferredSports(token, new Callback<SportListDocument>() {
                    @Override
                    public void onResponse(Call<SportListDocument> call, Response<SportListDocument> response) {
                        if (response.isSuccessful()) {
                            SportListDocument sportok = response.body();
                            for (Sport sport : sportok.getSports()) {
                                onShoppingItemCreated(getSportItem(sport.getId() - 1));
                                preferredSportList.add(sportList.get(sport.getId() - 1).getName());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        System.out.printf("Failure occured in getSports() method!");
                    }
                });
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

    public void getSports(final String token){
        //SportPresenter sp = new SportPresenter();

       // sp.getAllSports();
        DataHandler dh =  DataHandler.getInstance();
        dh.getAllSports(token,new Callback<SportListDocument>() {
            @Override
            public void onResponse(Call<SportListDocument> call, Response<SportListDocument> response) {
                if(response.isSuccessful()){
                    SportListDocument sportok = response.body();
                    addSports(sportok.getSports());
                    initPreferredSports(token);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.printf("Failure occured in getSports() method!");
            }
        });
    }

    public void followSport(String token, int position){
        DataHandler dh =  DataHandler.getInstance();
        dh.addPreferredSport(token,position, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.printf("Failure occured in followSport() method!");
            }
        });
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
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
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
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }
}

