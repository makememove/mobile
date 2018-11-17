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
        update();
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
        return preferredSportList.size();
    }
    public static String getName(int position){
        if(position != -1)
            return preferredSportList.get(position);
        return "SportName";
    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.RecylerView);
        adapter = new SportAdapter(this);
        //loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }


    private void initPreferredSports(String token){
       // addPreferredSports();

        if(recyclerView != null) {
            if (recyclerView.getAdapter().getItemCount() == 0) {
                SportPresenter sp = new SportPresenter(preferredSports);
                preferredSports.attach(this);
                sp.getUserPreferredSports(token);
            }
        }
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

    public static List<Sport> getPreferredSports(){
        return preferredSports.getSports();
    }

    public static void sportRemoveByCategory(List<Sport> sportList, String category){

        for(int i = 0;i<sportList.size();i++){
            if(sportList.get(i).getName().equals(category)){
                sportList.remove(sportList.get(i));
            }
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {
          //      k++;
            initRecylerView();
            initSports(token);
            final String token = User.getInstance().getToken();
            bt_addsport = this.getView().findViewById(R.id.bt_addsport);
            if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
            else bt_addsport.setVisibility(View.VISIBLE);
            bt_addsport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AlertDialogCustom));
                    dialog.setTitle("Choose a Sport: ");



                    final List<String> list = new ArrayList<>();
                    for(int j=0;j<sportList.size();j++){
                        list.add(sportList.get(j).getName());
                    }
                    list.removeAll(preferredSportList);

                    int i = 0;
                    final String [] arrayItems = new String[list.size()];
                    for (String item:list) {
                        arrayItems[i]= item;
                        i++;
                    }

                    dialog.setItems(arrayItems, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            Log.d("tester",Integer.toString(preferredSportList.size()));
                            Log.d("tester",Integer.toString(sportList.size()));

                            if(!preferredSportList.contains(arrayItems[position])) {
                                adapter.addItem(getSportItembycategory(arrayItems[position]));

                                followSport(token, getPosition(list.get(position))+1);
                                int id = getPosition(list.get(position))+1;
                                preferredSports.getSports().add(new Sport(id,sportList.get(id-1).getName()));

                               // initSports(token);
                                preferredSportList.add(arrayItems[position]);
                                if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
                                else bt_addsport.setVisibility(View.VISIBLE);
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
            if(preferredSportList.size()==sportList.size()) {
                Log.d("Addsport","Addsport"+preferredSportList.size()+" "+sportList.size());
            }
        }
    }
    private SportItem getSportItem(int position) {
        SportItem sportItem = new SportItem();
        sportItem.category = sportList.get(position).getName();
        return sportItem;
    }

    private SportItem getSportItembycategory(String name){
        SportItem sportItem = new SportItem();
        for(int i=0;i<sportList.size();i++){
            if(name.equals(sportList.get(i).getName()))sportItem.category=name;
        }
        return sportItem;
    }

    public void onItemRemoved(final SportItem item)
    {
        preferredSportList.remove(item.category);
        sportRemoveByCategory(preferredSports.getSports(),item.category);
        if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
        else bt_addsport.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUnfollow(int position) {
        unfollowSport(token, position);
    }

    @Override
    public void onAllItemsRemoved() {
        preferredSportList.clear();
    }

    @Override
    public void update() {
     //   addSports(sportok.getSports());
        if(sportList.size()==0 ) {
            addSports(sports.getSports());
            initPreferredSports(token);
        }

       // if(preferredSportList.size()==0&&preferredSports.getSports().size()!=0){
       else if(preferredSportList.size()==0) {
            for (Sport sport : preferredSports.getSports()) {
                adapter.addItem(getSportItem(sport.getId() - 1));
                preferredSportList.add(sportList.get(sport.getId() - 1).getName());
            }
            if(preferredSportList.size()==sportList.size())bt_addsport.setVisibility(View.GONE);
            else bt_addsport.setVisibility(View.VISIBLE);
            // }
        }
    }
}

