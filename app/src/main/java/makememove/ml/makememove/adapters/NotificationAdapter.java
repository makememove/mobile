package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.FriendsFragment;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.FinishedEventsFragment;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocumentContainer;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Notify;
import makememove.ml.makememove.dpsystem.presenters.EventPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.user.User;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> implements BaseView {
    private final List<Notify> items;
    private static EventDocumentContainer ed;

    private NotificationAdapter.NotificationClickListener listener;
    public NotificationAdapter(NotificationAdapter.NotificationClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.notify_csempe,parent,false);
        return new NotificationAdapter.NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position){
        final Notify item = items.get(position);
        ed= new EventDocumentContainer();
        ed.attach(this);
        final EventPresenter ep = new EventPresenter(ed);


        switch (item.getType()){
            case 0:
                holder.iv_icon.setImageResource(R.drawable.nav_create);
                break;
            case 1:
                holder.iv_icon.setImageResource(R.drawable.nav_setting);
                break;
            case 2:
                holder.iv_icon.setImageResource(R.drawable.nav_friends);
                break;
            case 3:
                holder.iv_icon.setImageResource(R.drawable.nav_finished);
                break;
            default:
                break;
        }
        holder.tv_message.setText(item.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostPresenter pp = new PostPresenter();
                pp.deleteNotification(User.getInstance().getToken(),item.getId());

                switch (item.getType()){
                    case 0:
                        ep.getEvent(User.getInstance().getToken(),item.getEvent_id());
                        break;
                    case 1:
                        ep.getEvent(User.getInstance().getToken(),item.getEvent_id());
                        break;
                    case 2:
                        FriendsFragment friendsFragment= new FriendsFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, friendsFragment)
                                .commit();
                        break;
                    case 3:
                        FinishedEventsFragment finFragment= new FinishedEventsFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, finFragment)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });

    }


    public void addItem(Notify item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(Notify item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void update(List<Notify> notifications) {
        items.clear();
        items.addAll(notifications);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public void update() {
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        EventDetailsFragment.setCurrentEvent(ed.getEvent());


        fragmentManager.beginTransaction()
                .replace(R.id.content, eventDetailsFragment)
                .commit();
    }

    public interface NotificationClickListener{
        void onItemChanged(UserItem item);
        void onItemRemoved(UserItem item);
        void onAllItemsRemoved();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_icon;
            TextView tv_message;

        NotificationViewHolder(View itemView) {
            super(itemView);
                iv_icon=itemView.findViewById(R.id.iv_noticon);
                tv_message = itemView.findViewById(R.id.tv_notmessage);



        }
    }
}