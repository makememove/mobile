package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.RankAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.RankDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.UserRank;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.dpsystem.presenters.RankPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.User;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class RanklistFragment extends Fragment implements BaseView {
    private View Layout;

    private RankDocument rankDocument;
    private RecyclerView recyclerView;
    private RankAdapter adapter;
    private static int index = 0;
    private static List<Sport>preferedSports;
    private TextView sportName;
    private ImageButton previous;
    private ImageButton next;

    public void initPreferedSports(){
        List<Sport> sports = UserMainFragment.getPreferredSports();
        preferedSports = sports;
    }

    public static RanklistFragment newInstance(int ind) {

        Bundle args = new Bundle();
        index = ind;

        RanklistFragment fragment = new RanklistFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_ranklistrecyler);
        adapter = new RankAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void update() {
        if(recyclerView.getAdapter().getItemCount()==0&&rankDocument.getSport().getRanklist().size()!=0){
            for (UserRank current: rankDocument.getSport().getRanklist()) {
                adapter.addItem(current);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ranklist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rankDocument = new RankDocument();
        rankDocument.attach(this);
        initPreferedSports();

        Layout = this.getView();
        if (Layout != null) {
            initRecylerView();
            RankPresenter rankPresenter = new RankPresenter(rankDocument);
            rankPresenter.getRankList(User.getInstance().getToken(),preferedSports.get(index).getId(), 100);


            sportName = Layout.findViewById(R.id.tv_actualsport);
            previous = Layout.findViewById(R.id.ib_prev);
            next = Layout.findViewById(R.id.ib_next);

            sportName.setText(preferedSports.get(index).getName());
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index == 0){
                        index = preferedSports.size()-1;
                        sportName.setText(preferedSports.get(index).getName());
                    }
                    else{
                        index--;
                        sportName.setText(preferedSports.get(index).getName());
                    }
                    RanklistFragment sportEventFragment = RanklistFragment.newInstance(index);
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment)
                            .commit();
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index == preferedSports.size()-1){
                        index = 0;
                        sportName.setText(preferedSports.get(index).getName());
                    }
                    else{
                        index++;
                        sportName.setText(preferedSports.get(index).getName());
                    }
                    RanklistFragment sportEventFragment = RanklistFragment.newInstance(index);
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment)
                            .commit();
                }
            });


        }

        NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
        np.getNotifications(User.getInstance().getToken());
    }
}
