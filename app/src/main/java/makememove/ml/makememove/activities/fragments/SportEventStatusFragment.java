package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.adapters.EventAdapter;
import makememove.ml.makememove.adapters.SportAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.presenters.EventListPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class SportEventStatusFragment extends Fragment implements EventAdapter.EventItemClickListener, BaseView {

    private View Layout;
    private TextView sportName;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ImageView iv_sportpicture;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private static String sportNameString;
    private static int sportID;
    private static EventListDocument documents;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sporteventstatus_fragment, container, false);

    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.eventrecylerview);
        adapter = new EventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    public static SportEventStatusFragment newInstance(int arg) {

        Bundle args = new Bundle();
        args.putInt("SportID",arg);

        SportEventStatusFragment fragment = new SportEventStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        documents = new EventListDocument();
        documents.attach(this);
        sportID = (int) getArguments().get("SportID");

        Layout = this.getView();
        if (Layout != null) {
            initRecylerView();

            EventListPresenter ep = new EventListPresenter(documents);
            ep.getSportEvents(User.getInstance().getToken(),sportID+1);

            previousButton = Layout.findViewById(R.id.ib_prev);
            nextButton = Layout.findViewById(R.id.ib_next);
            sportName = Layout.findViewById(R.id.tv_actualsport);
            // sportID = (int) getArguments().get("SportID");
            sportNameString = UserMainFragment.getName(sportID);
            sportName.setText(sportNameString);

            iv_sportpicture=Layout.findViewById(R.id.iv_statusSportPicture);
            GlobalClass.setSportPicture(sportNameString,iv_sportpicture);



            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sportID == 0){
                        sportID = UserMainFragment.getListSize()-1;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    else{
                        sportID--;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    SportEventStatusFragment sportEventFragment = SportEventStatusFragment.newInstance(sportID);
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment)
                            .commit();
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sportID+1 == UserMainFragment.getListSize()){
                        sportID =0;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    else{
                        sportID++;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    SportEventStatusFragment sportEventFragment = SportEventStatusFragment.newInstance(sportID);
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment)
                            .commit();
                }
            });
        }
    }

    @Override
    public void update() {
        Log.d("Az update","Itt van");
        if(documents.getEvents().size()!=0){
            for (EventDocument doc: documents.getEvents()) {
                adapter.addItem(doc);
                Log.d("A token:assasa ",doc.getTitle());
            }
        }

    }

    @Override
    public void onItemChanged(EventDocument item) {

    }
}
