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
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.presenters.EventListPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class FinishedEventsFragment extends Fragment implements EventAdapter.EventItemClickListener, BaseView {
    private View Layout;

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private static EventListDocument documents;

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_finishedevents);
        adapter = new EventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.finishedevents_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();
        documents = new EventListDocument();
        documents.attach(this);

        initRecylerView();

        if (Layout != null) {
            NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
            np.getNotifications(User.getInstance().getToken());

            EventListPresenter ep = new EventListPresenter(documents);
            ep.getfinEvents(User.getInstance().getToken());
        }
    }

    @Override
    public void onItemChanged(EventDocument item) {

    }

    @Override
    public void update() {
        if(documents.getEvents().size()!=0){
            for (EventDocument doc: documents.getEvents()) {
                adapter.addItem(doc);
                Log.d("A token:assasa ",doc.getTitle());
            }
        }
    }
}
