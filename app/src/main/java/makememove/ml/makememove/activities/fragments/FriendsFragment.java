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

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.FriendDecisionAdapter;
import makememove.ml.makememove.adapters.FriendRemoveAdapter;
import makememove.ml.makememove.adapters.FriendlistAdapter;
import makememove.ml.makememove.adapters.UserItem;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Friend;
import makememove.ml.makememove.dpsystem.presenters.DataHandler;
import makememove.ml.makememove.dpsystem.presenters.FriendPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment implements BaseView {

    private View Layout;

    private RecyclerView sentRecyclerView;
    private FriendRemoveAdapter sentAdapter;

    private RecyclerView recievedRecyclerView;
    private FriendDecisionAdapter recievedAdapter;

    private RecyclerView friendRecyclerView;
    private FriendlistAdapter friendAdapter;

    private static FriendDocument document;
    private static List<Friend> friendDocument;

    private void initRecylerViewSent(){
        sentRecyclerView = this.getView().findViewById(R.id.rv_friendsentrequestlist);
        sentAdapter = new FriendRemoveAdapter();
        sentRecyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        sentRecyclerView.setAdapter(sentAdapter);
    }
    private void initRecylerViewRecieved(){
        recievedRecyclerView = this.getView().findViewById(R.id.rv_friendreceivedlist);
        recievedAdapter = new FriendDecisionAdapter();
        recievedRecyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recievedRecyclerView.setAdapter(recievedAdapter);
    }
    private void initRecylerViewFriend(){
        friendRecyclerView = this.getView().findViewById(R.id.rv_friendslist);
        friendAdapter = new FriendlistAdapter();
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

        if(friendRecyclerView.getAdapter().getItemCount()==0&&friendDocument.size()!=0){
            for (Friend doc:friendDocument) {
                friendAdapter.addItem(new UserItem(doc.getId(),doc.getUserName()));
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        document = new FriendDocument();
        document.attach(this);
        friendDocument = new ArrayList<Friend>();

        Layout = this.getView();
        if (Layout != null) {
            initRecylerViewSent();
            initRecylerViewRecieved();
            initRecylerViewFriend();

            FriendPresenter fp = new FriendPresenter(document);
            fp.getSentFriendsRequests(User.getInstance().getToken());
            fp.getRecievedFriendRequests(User.getInstance().getToken());
         //   final FriendsFragment fg = this;

            NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
            np.getNotifications(User.getInstance().getToken());

            DataHandler dh = DataHandler.getInstance();
            dh.setUserData(new Callback<UserDocument>() {
                @Override
                public void onResponse(Call<UserDocument> call, Response<UserDocument> response) {
                    if(response.isSuccessful()){
                        UserDocument ud = response.body();
                       // ud.attach(fg);
                        friendDocument = ud.getUser().getFriends();
                        //ud.sendNotification();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
    }
}
