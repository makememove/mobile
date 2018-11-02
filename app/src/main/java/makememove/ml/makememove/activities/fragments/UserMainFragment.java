package makememove.ml.makememove.activities.fragments;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.persistence.SportAdapter;
import makememove.ml.makememove.persistence.SportItem;
import makememove.ml.makememove.persistence.SportListDatabase;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.SportList;
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
    private List<Sport> sportList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportList = new ArrayList<Sport>();
        database = Room.databaseBuilder(
                GlobalClass.context,
                SportListDatabase.class,
                "sport-list"
        ).build();
        getSports(User.getInstance().getToken());
        System.out.printf("Token: "+User.getInstance().getToken());
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
                return database.sportItemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<SportItem> shoppingItems) {
                adapter.update(shoppingItems);
            }
        }.execute();
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

    public void getSports(String token){
        DataHandler dh =  DataHandler.getInstance();
        dh.getAllSports(token,new Callback<SportList>() {
            @Override
            public void onResponse(Call<SportList> call, Response<SportList> response) {
                if(response.isSuccessful()){
                    SportList sportok = response.body();
                    addSports(sportok.getSports());
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
                    System.out.printf("Sikeres volt a followSport!");
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
                            onShoppingItemCreated(getSportItem(position));
                            followSport(token,position+1);
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
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }


}

