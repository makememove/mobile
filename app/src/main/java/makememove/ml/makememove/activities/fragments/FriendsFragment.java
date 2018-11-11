package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.EventAdapter;
import makememove.ml.makememove.adapters.FriendAddAdapter;
import makememove.ml.makememove.adapters.FriendDecisionAdapter;
import makememove.ml.makememove.adapters.FriendRemoveAdapter;
import makememove.ml.makememove.adapters.FriendlistAdapter;
import makememove.ml.makememove.adapters.UserItem;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Friend;
import makememove.ml.makememove.dpsystem.presenters.DataHandler;
import makememove.ml.makememove.dpsystem.presenters.FriendPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class FriendsFragment extends Fragment
        implements FriendRemoveAdapter.FriendItemClickListener,
        FriendDecisionAdapter.FriendItemClickListener,
        FriendlistAdapter.FriendItemClickListener,
        BaseView {

    private View Layout;

    private RecyclerView sentRecyclerView;
    private FriendRemoveAdapter sentAdapter;

    private RecyclerView recievedRecyclerView;
    private FriendDecisionAdapter recievedAdapter;

    private RecyclerView friendRecyclerView;
    private FriendlistAdapter friendAdapter;

    private static FriendDocument document;
    private static FriendDocument friendDocument;

    private void initRecylerViewSent(){
        sentRecyclerView = this.getView().findViewById(R.id.rv_friendsentrequestlist);
        sentAdapter = new FriendRemoveAdapter(this);
        sentRecyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        sentRecyclerView.setAdapter(sentAdapter);
    }
    private void initRecylerViewRecieved(){
        recievedRecyclerView = this.getView().findViewById(R.id.rv_friendreceivedlist);
        recievedAdapter = new FriendDecisionAdapter(this);
        recievedRecyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recievedRecyclerView.setAdapter(recievedAdapter);
    }
    private void initRecylerViewFriend(){
        friendRecyclerView = this.getView().findViewById(R.id.rv_friendslist);
        friendAdapter = new FriendlistAdapter(this);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        friendRecyclerView.setAdapter(friendAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friends_fragment, container, false);
    }

    @Override
    public void onItemChanged(UserItem item) {

    }

    @Override
    public void onItemRemoved(UserItem item) {

    }

    @Override
    public void onAllItemsRemoved() {

    }

    @Override
    public void update() {
        if(sentRecyclerView.getAdapter().getItemCount()==0&&document.getSent().size()!=0){
            for (UserItem doc: document.getSent()) {
                Log.d("BugFix","sentBugFix"+doc.getUserName());
                sentAdapter.addItem(doc);
            }
        }

        if(recievedRecyclerView.getAdapter().getItemCount()==0&&document.getRequests().size()!=0){
            for (UserItem doc: document.getRequests()) {
                Log.d("BugFix","recievedBugFix"+doc.getUserName());
                recievedAdapter.addItem(doc);
            }
        }

        if(friendRecyclerView.getAdapter().getItemCount()==0&&User.getInstance().getFriends().size()!=0){
            for (Friend doc: User.getInstance().getFriends()) {
                friendAdapter.addItem(new UserItem(doc.getId(),doc.getUserName()));
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        document = new FriendDocument();
        document.attach(this);

        friendDocument = new FriendDocument();
        friendDocument.attach(this);

        Layout = this.getView();
        if (Layout != null) {
            initRecylerViewSent();
            initRecylerViewRecieved();
            initRecylerViewFriend();

            FriendPresenter fp = new FriendPresenter(document);
            fp.getSentFriendsRequests(User.getInstance().getToken());
            fp.getRecievedFriendRequests(User.getInstance().getToken());

        }
    }
}
