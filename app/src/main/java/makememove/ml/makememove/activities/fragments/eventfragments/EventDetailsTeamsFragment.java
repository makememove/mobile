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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.RankAdapter;
import makememove.ml.makememove.adapters.SportAdapter;
import makememove.ml.makememove.adapters.TeamAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.TeamDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.dpsystem.documents.subdocuments.UserRank;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.dpsystem.presenters.TeamPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class EventDetailsTeamsFragment extends Fragment implements TeamAdapter.TeamClickListener, BaseView {
    private ImageButton add_team;
    private String m_Text = "";
    private View Layout;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private TeamDocument teams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetailsteams_fragment, container, false);
    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_teamrecyler);
        adapter = new TeamAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        teams = new TeamDocument();
        teams.attach(this);


        //TODO teammembercsempe inflatelése csatlakozáskor, szerverről lekkérdezett tagokra,
        // TODO barát hozzáadása gomb bekötése, saját magamnál ne legyen barárnak jelölés gomb

        if(Layout != null) {
            initRecylerView();

            TeamPresenter tp = new TeamPresenter(teams);
            Log.d("eventid","eventid "+EventDetailsFragment.getCurrentEvent().getId());
            tp.getTeams(User.getInstance().getToken(),EventDetailsFragment.getCurrentEvent().getId());


            add_team= Layout.findViewById(R.id.ib_addteam);
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
                                    createdTeam.setCapacity(2);
                                    createdTeam.setEventId(+EventDetailsFragment.getCurrentEvent().getId());
                                    createdTeam.setName(input.getText().toString());

                                    // TODO ha lesz createdTeam.setCapacity(EventDetailsFragment.getCurrentEvent().getCapacity());
                                    adapter.addItem(createdTeam);
                                    PostPresenter pp = new PostPresenter();
                                    //TODO csapat felvétele
                                    pp.createTeam(User.getInstance().getToken(), createdTeam);
                                }

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


    @Override
    public void onItemChanged(Team item) {

    }

    @Override
    public void onItemRemoved(Team item) {

    }

    @Override
    public void onItemJoined(Team item) {

    }

    @Override
    public void onItemLeft(Team item) {

    }

    @Override
    public void onAllItemsRemoved() {

    }

    @Override
    public void update() {
        if(recyclerView.getAdapter().getItemCount()==0&&teams.getEvent().getTeams().size()!=0){
            for (Team current: teams.getEvent().getTeams()) {
                adapter.addItem(current);
            }
        }
    }
}
