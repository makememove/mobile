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
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.presenters.EventListPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.dpsystem.presenters.SportPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.adapters.SportAdapter;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.user.User;

public class UserMainFragment extends Fragment implements SportAdapter.SportItemClickListener, BaseView {
    private ImageButton bt_addsport;

    private View Layout;
    private RecyclerView recyclerView;
    private SportAdapter adapter;
    private TextView tv_event1;
    private TextView tv_event2;
    private TextView tv_event3;
    private TextView tv_event1date;
    private TextView tv_event2date;
    private TextView tv_event3date;

    private static SportListDocument sports;
    private static SportListDocument preferredSports;
    private static List<Sport> sportList;
    private static List<String> preferredSportList;
    private String token = User.getInstance().getToken();
    private static EventListDocument documents;



    private void initRecylerView(){
        recyclerView = Layout.findViewById(R.id.RecylerView);
        adapter = new SportAdapter(this);

        for(int i=0;i<preferredSports.getSports().size();i++){
            adapter.addItem(preferredSports.getSports().get(i));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }


    private void initPreferredSports(String token){
        SportPresenter sp = new SportPresenter(preferredSports);
        preferredSports.attach(this);
        sp.getUserPreferredSports(token);
    }

    public void initSports(final String token){
        SportPresenter sp = new SportPresenter(sports);
        sports.attach(this);
        sp.getAllSports(token);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportList = new ArrayList<Sport>();
        preferredSportList = new ArrayList<String>();
        sports = new SportListDocument();
        preferredSports = new SportListDocument();
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
        documents = new EventListDocument();
        documents.attach(this);

        Layout=this.getView();
        if(Layout != null) {
            initRecylerView();
            initSports(token);
            initPreferredSports(token);

            tv_event1=Layout.findViewById(R.id.tv_event1);
            tv_event2=Layout.findViewById(R.id.tv_event2);
            tv_event3=Layout.findViewById(R.id.tv_event3);
            tv_event1date=Layout.findViewById(R.id.tv_event1date);
            tv_event2date=Layout.findViewById(R.id.tv_event2date);
            tv_event3date=Layout.findViewById(R.id.tv_event3date);

            EventListPresenter ep = new EventListPresenter(documents);
            ep.getUnfinEvents(User.getInstance().getToken());

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
        NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
        np.getNotifications(User.getInstance().getToken());
    }



    public void addSports(List<Sport> item){
        sportList.addAll(item);
    }




    public void followSport(String token, int position){
        AuthInputDocument doc = new AuthInputDocument();
        PostPresenter pp = new PostPresenter();
        doc.attach(this);
        pp.setDocument(doc);;
        pp.postPreferredSport(token,position);
        Log.d("hívás:","follow");
    }


    public void unfollowSport(String token, int position){
        AuthInputDocument doc = new AuthInputDocument();
        PostPresenter pp = new PostPresenter();
        doc.attach(this);
        pp.setDocument(doc);;
        pp.unpostPreferredSport(token,position);
        Log.d("hívás:","unfollow");
    }


    public void onItemRemoved(final Sport item)
    {
        preferredSportList.remove(item.getName());
        sportRemoveByCategory(preferredSports.getSports(),item.getName());
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
        if(sports.getSports().size()!=0){
            sportList.clear();
            for (Sport s: sports.getSports()) {
                sportList.add(s);
            }
        }

        if(preferredSports.getSports().size()!=0){
            preferredSportList.clear();
            for (Sport s: preferredSports.getSports()) {
                preferredSportList.add(s.getName());
            }
        }

        if(preferredSports.getSports().size()==sports.getSports().size())
            bt_addsport.setVisibility(View.GONE);
        else bt_addsport.setVisibility(View.VISIBLE);

        setUpcomingEvents();
        initRecylerView();
    }

    private void setUpcomingEvents(){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        switch (documents.getEvents().size()){
            case 0:
                break;
            case 1:
                tv_event1.setText(documents.getEvents().get(0).getTitle());
                tv_event1date.setText(SDF.format(documents.getEvents().get(0).getDate()));
                break;
            case 2:
                tv_event1.setText(documents.getEvents().get(0).getTitle());
                tv_event1date.setText(SDF.format(documents.getEvents().get(0).getDate()));
                tv_event2.setText(documents.getEvents().get(1).getTitle());
                tv_event2date.setText(SDF.format(documents.getEvents().get(1).getDate()));
                break;

                default:
                    tv_event1.setText(documents.getEvents().get(0).getTitle());
                    tv_event1date.setText(SDF.format(documents.getEvents().get(0).getDate()));
                    tv_event2.setText(documents.getEvents().get(1).getTitle());
                    tv_event2date.setText(SDF.format(documents.getEvents().get(1).getDate()));
                    tv_event3.setText(documents.getEvents().get(2).getTitle());
                    tv_event3date.setText(SDF.format(documents.getEvents().get(2).getDate()));
        }
    }



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



    public static int getPosition(String item){
        for(int i = 0;i<sportList.size();i++){
            if(sportList.get(i).getName().equals(item))
                return i;
        }
        return -1;
    }

    public static Sport getPreferredId(String item){
        for(int i = 0;i<preferredSports.getSports().size();i++){
            if(preferredSports.getSports().get(i).getName().equals(item))
                return preferredSports.getSports().get(i);
        }
        return null;
    }

    public static int getPreferredPosition(Sport sport){
        return preferredSports.getSports().indexOf(sport);
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

    private Sport getSportItem(int position) {
        Sport sportItem = new Sport();
        sportItem.setName(sportList.get(position).getName());
        return sportItem;
    }

    private Sport getSportItembycategory(String name){
        Sport sportItem = new Sport();
        for(int i=0;i<sportList.size();i++){
            if(name.equals(sportList.get(i).getName()))sportItem.setName(name);
        }
        return sportItem;
    }
}

