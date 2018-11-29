package makememove.ml.makememove.activities.fragments.eventfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.adapters.EventAdapter;
import makememove.ml.makememove.adapters.MyEventsAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.presenters.EventListPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class UnfinishedEventFragment extends Fragment implements MyEventsAdapter.MyEventsClickListener,EventAdapter.EventItemClickListener ,BaseView {
    private View Layout;

    private RecyclerView rv_myevents;
    private MyEventsAdapter myevents_adapter;
    private static EventListDocument mydocument;
    private RecyclerView rv_unfinevents;
    private EventAdapter unfin_adapter;
    private static EventListDocument unfindocument;

    private void initrv_myevents(){
        rv_myevents = Layout.findViewById(R.id.rv_myeventslist);
        myevents_adapter = new MyEventsAdapter(this);
        rv_myevents.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        rv_myevents.setAdapter(myevents_adapter);
    }

    private void initrv_unfinevents(){
        rv_unfinevents = Layout.findViewById(R.id.rv_joinedeventlist);
        unfin_adapter = new EventAdapter(this);
        rv_unfinevents.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        rv_unfinevents.setAdapter(unfin_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.unfinishedevents_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();

        mydocument = new EventListDocument();
        mydocument.attach(this);
        unfindocument = new EventListDocument();
        unfindocument.attach(this);

        initrv_myevents();
        initrv_unfinevents();

        if (Layout != null) {
            NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
            np.getNotifications(User.getInstance().getToken());

            EventListPresenter ep = new EventListPresenter(mydocument);
            ep.getMyEvents(User.getInstance().getToken(),User.getInstance().getId());

            EventListPresenter ep1 = new EventListPresenter(unfindocument);
            ep1.getUnfinEvents(User.getInstance().getToken());
        }
    }

    @Override
    public void onItemChanged(EventDocument item) {

    }

    @Override
    public void update() {
        Log.d("Az update","Itt van");
        initrv_myevents();
        initrv_unfinevents();
        if(mydocument.getEvents().size()!=0){
            initrv_myevents();
            for (EventDocument doc: mydocument.getEvents()) {
                if(doc.getClosed()!=1) {
                    myevents_adapter.addItem(doc);
                    Log.d("A token:assasa ", doc.getTitle());
                }
            }
        }

        if(unfindocument.getEvents().size()!=0){
            for (EventDocument doc: unfindocument.getEvents()) {
                if(doc.getClosed()!=1) {
                    unfin_adapter.addItem(doc);
                    Log.d("A token:assasa ", doc.getTitle());
                }
            }
        }
    }
}
