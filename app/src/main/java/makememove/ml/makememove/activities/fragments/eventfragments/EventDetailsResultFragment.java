package makememove.ml.makememove.activities.fragments.eventfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.ResultAdapter;
import makememove.ml.makememove.adapters.TeamAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocumentContainer;
import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.dpsystem.presenters.EventPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class EventDetailsResultFragment extends Fragment implements ResultAdapter.ResultClickListener, BaseView {

    private View Layout;
    private ResultAdapter adapter;
    private RecyclerView recyclerView;
    private TextView teamResults;
    private static EventDocumentContainer currentEvent;

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_detailsresultteams);
        adapter = new ResultAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetailsresult_fragment, container, false);
    }

    public void getEvent(){
        currentEvent = new EventDocumentContainer();
        currentEvent.attach(this);
        EventPresenter ep = new EventPresenter(currentEvent);
        ep.getEvent(User.getInstance().getToken(),EventDetailsFragment.getCurrentEvent().getId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {
            initRecylerView();
            teamResults = Layout.findViewById(R.id.tv_teamresults);
            teamResults.setVisibility(View.GONE);
            getEvent();
        }
    }


    @Override
    public void onItemChanged(EventDocument item) {

    }

    @Override
    public void update() {
        List<ResultDocument> rankings = currentEvent.getEvent().getRankings();
        if(rankings.size()==0) {
            teamResults.setText("No results were available at the time of request!");
            teamResults.setVisibility(View.VISIBLE);
        }
        else {
            teamResults.setVisibility(View.GONE);
            int i = 0;
            for(ResultDocument team: rankings){
                team.setTeamName(currentEvent.getEvent().getTeamName(i++));
                adapter.addItem(team);
            }
        }
    }
}
