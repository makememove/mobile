package makememove.ml.makememove.activities.fragments;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import makememove.ml.makememove.R;
import makememove.ml.makememove.adapters.NotificationAdapter;
import makememove.ml.makememove.adapters.UserItem;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.NotificationDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Notify;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;


public class NotificationFragment extends Fragment implements NotificationAdapter.NotificationClickListener,
        BaseView {

    private View Layout;

    private RecyclerView rv_notifications;
    private NotificationAdapter notificationAdapter;

    public static NotificationDocument document = new NotificationDocument();


    private void initRecylerViewnotifications(){
        rv_notifications = this.getView().findViewById(R.id.rv_notificationlist);
        notificationAdapter = new NotificationAdapter(this);
        rv_notifications.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        rv_notifications.setAdapter(notificationAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        document.attach(this);

        Layout=this.getView();

        if(Layout != null) {
            initRecylerViewnotifications();

            NotificationPresenter np = new NotificationPresenter(document);
            np.getNotifications(User.getInstance().getToken());

        }

    }

    @Override
    public void update() {
        if(rv_notifications.getAdapter().getItemCount()==0&&document.getNotifications().size()!=0){
            for (Notify doc: document.getNotifications()) {
                Log.d("BugFix","sentBugFix"+doc.getId());
                notificationAdapter.addItem(doc);
            }
        }
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
}
