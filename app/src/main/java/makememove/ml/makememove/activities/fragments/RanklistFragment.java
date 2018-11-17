package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.RankAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.RankDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.UserRank;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.dpsystem.presenters.RankPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class RanklistFragment extends Fragment implements BaseView {
    private View Layout;

    private RankDocument rankDocument;
    private RecyclerView recyclerView;
    private RankAdapter adapter;
    private static int sportID;

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_ranklistrecyler);
        adapter = new RankAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    public static RanklistFragment newInstance(int sportID) {

        Bundle args = new Bundle();


        RanklistFragment fragment = new RanklistFragment();
        fragment.setArguments(args);
        return fragment;
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

        Layout = this.getView();
        if (Layout != null) {
            initRecylerView();
            RankPresenter rankPresenter = new RankPresenter(rankDocument);
            rankPresenter.getRankList(User.getInstance().getToken(),2, 100);
        }

        NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
        np.getNotifications(User.getInstance().getToken());
    }
}
