package makememove.ml.makememove.activities.fragments.eventfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.TeamAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.TeamDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.dpsystem.presenters.TeamPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class EventDetailsTeamsFragment extends Fragment implements TeamAdapter.TeamClickListener, BaseView {
    private ImageButton add_team;
    private String m_Text = "";
    private View Layout;
    private RecyclerView recyclerView;
    private static TeamAdapter adapter;
    private static TeamDocument teams;
    private static int joinedTeam = -1;
    private static AuthInputDocument createdTeamId;
    private static String createdTeamName;
    private RecyclerView.LayoutManager lm = new LinearLayoutManager(GlobalClass.context);
    private static int refreshedState = 0;

    public static int getJoinedTeam() {
        return joinedTeam;
    }

    public static void setJoinedTeam(int joinedTeam) {
        adapter.leaveTeamButton(joinedTeam);
        EventDetailsTeamsFragment.joinedTeam = joinedTeam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetailsteams_fragment, container, false);
    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_teamrecyler);
        adapter = new TeamAdapter(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
    public static void refreshRecyclerView(){
        TeamAdapter.clearEverything();
        TeamPresenter tp = new TeamPresenter(teams);
        tp.getTeams(User.getInstance().getToken(),EventDetailsFragment.getCurrentEvent().getId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        teams = new TeamDocument();
        teams.attach(this);


        // TODO saját magamnál ne legyen barárnak jelölés gomb (jelenleg van gomb, csak nem rákattintható)

        if(Layout != null) {
            initRecylerView();

            TeamPresenter tp = new TeamPresenter(teams);
            Log.d("eventid","eventid "+EventDetailsFragment.getCurrentEvent().getId());
            tp.getTeams(User.getInstance().getToken(),EventDetailsFragment.getCurrentEvent().getId());


            add_team= Layout.findViewById(R.id.ib_addteam);
            if(EventDetailsFragment.getCurrentEvent().getClosed()==1)
                add_team.setVisibility(View.GONE);
            add_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     m_Text = "";

                    AlertDialog.Builder builder = new AlertDialog.Builder(Layout.getContext());
                    builder.setTitle("Teamname:");

                    final EditText input = new EditText(Layout.getContext());

                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(input.getText().toString().length()>3) {
                                if(EventDetailsFragment.getCurrentEvent().getMaxAttending()>adapter.getItemCount()) {
                                    Log.d("Capacity","Capacity "+EventDetailsFragment.getCurrentEvent().getMaxAttending()+" "+adapter.getItemCount());
                                    m_Text = input.getText().toString();

                                    Team createdTeam = new Team();
                                    createdTeam.setEventId(+EventDetailsFragment.getCurrentEvent().getId());
                                    createdTeamName = input.getText().toString();
                                    createdTeam.setName(createdTeamName);
                                   // if(EventDetailsFragment.getCurrentEvent().getMemberLimit()!=null)
                                    createdTeam.setCapacity(EventDetailsFragment.getCurrentEvent().getMemberLimit());
                                    adapter.addItem(createdTeam);

                                    createTeam(createdTeam);
                                }
                                else
                                    Snackbar.make(getActivity().findViewById(R.id.content), "The event is already in its full team capacity!", Snackbar.LENGTH_LONG).show();

                            }else{
                                Snackbar.make(getActivity().findViewById(R.id.content), "Error: Teamname must be at least 4 character!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
        }

    }

    public void createTeam(Team createdTeam){
        createdTeamId = new AuthInputDocument();
        createdTeamId.attach(this);
        PostPresenter pp = new PostPresenter(createdTeamId);
        pp.leaveTeam(User.getInstance().getToken(),joinedTeam);
        createdTeamId.setId(null);
        pp.createTeam(User.getInstance().getToken(), createdTeam);
        //pp.joinTeam(User.getInstance().getToken(),User.getInstance().getId());
    }

    @Override
    public void onChanged() {
        if(teams.getEvent().getTeams().size()==EventDetailsFragment.getCurrentEvent().getMaxAttending()){
            add_team.setVisibility(View.GONE);
        }
        else add_team.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemRemoved(Team item) {

    }

    @Override
    public void onItemJoined(Team item) {
        AuthInputDocument refreshDocument = new AuthInputDocument();
        refreshDocument.attach(this);
        PostPresenter pp = new PostPresenter(refreshDocument);
        pp.leaveTeam(User.getInstance().getToken(),joinedTeam);
        Log.d("Csapat","Elhagyott csapat: "+joinedTeam+" csatlakozott csapat: "+item.getId());
        setJoinedTeam(item.getId());
        pp.joinTeam(User.getInstance().getToken(),item.getId());
        refreshedState = 1;
    }

    @Override
    public void onItemLeft(Team item) {
        setJoinedTeam(-1);
        PostPresenter pp = new PostPresenter();
        pp.leaveTeam(User.getInstance().getToken(),item.getId());
        add_team.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAllItemsRemoved() {

    }


    public boolean creationOnTheWay(){
        if(createdTeamId != null && createdTeamId.getId()== null){
            Log.d("teamid","teamid true");

            return true;
        }
        Log.d("teamid","teamid false");
        if(createdTeamId!=null)
            Log.d("teamid3","teamid3 "+createdTeamId.getId());
        return false;
    }

    @Override
    public void update() {

        if(this.getView()!= null && !creationOnTheWay() && refreshedState!=1) {
            initRecylerView();
            if(recyclerView.getAdapter().getItemCount()==0&&teams.getEvent().getTeams().size()!=0){
                for (Team current: teams.getEvent().getTeams()) {
                    adapter.addItem(current);
                }
            }
            if(teams.getEvent().getTeams().size()==EventDetailsFragment.getCurrentEvent().getMaxAttending()){
                add_team.setVisibility(View.GONE);
            }
            else add_team.setVisibility(View.VISIBLE);
        }
        if(createdTeamId!=null && createdTeamId.getId()!=null){
            refreshRecyclerView();
        }
        if(createdTeamId!=null&&createdTeamId.getId()!=null){
            Log.d("teamid","teamid "+createdTeamId.getId());
            PostPresenter pp = new PostPresenter();
            setJoinedTeam(createdTeamId.getId());
            pp.joinTeam(User.getInstance().getToken(),
                    createdTeamId.getId());
            createdTeamId = null;
        }
        if(refreshedState == 1) {
            refreshRecyclerView();
            refreshedState = 0;
        }

    }
}
