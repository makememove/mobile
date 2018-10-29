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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.persistence.SportAdapter;
import makememove.ml.makememove.persistence.SportItem;
import makememove.ml.makememove.persistence.SportListDatabase;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class UserMainFragment extends Fragment implements SportAdapter.SportItemClickListener{
    private ImageButton bt_addsport;
    private View Layout;
    private RecyclerView recyclerView;
    private SportAdapter adapter;
    private SportListDatabase database;
   // private NewShoppingItemDialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  Room.databaseBuilder().fallbackToDestructiveMigration();
        database = Room.databaseBuilder(
                GlobalClass.context,
                SportListDatabase.class,
                "sport-list"
        ).build();

      //  database.myDao().nukeTable();

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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Layout=this.getView();
        if(Layout != null) {
            initRecylerView();
            bt_addsport = this.getView().findViewById(R.id.bt_addsport);
            bt_addsport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AlertDialogCustom));
                    dialog.setTitle("Choose a Sport");
                    dialog.setItems(R.array.sports_array, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            onShoppingItemCreated(getSportItem(position));
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
        sportItem.category = SportItem.Category.getByOrdinal(position);
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
}

