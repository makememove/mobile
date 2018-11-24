package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsTeamsFragment;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.MemberDocument;
import makememove.ml.makememove.dpsystem.presenters.MemberPresenter;
import makememove.ml.makememove.user.User;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> implements  BaseView {

    private List<UserItem> items;
    private MemberAdapter.MemberClickListener listener;
    private MemberDocument memberDocument;
    private Integer teamId;


    public MemberAdapter(){
        items = new ArrayList<UserItem>();

    }
    public MemberAdapter(MemberAdapter.MemberClickListener listener){
        items = new ArrayList<UserItem>();
        this.listener = listener;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        memberDocument = new MemberDocument();
        memberDocument.attach(this);
        MemberPresenter mp = new MemberPresenter(memberDocument);
        mp.getTeamMembers(User.getInstance().getToken(),teamId);

    }

    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.teammembercsempe,parent,false);
        return new MemberAdapter.MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position){
        UserItem item = items.get(position);

        holder.tv_id.setText(Integer.toString(position+1));
        holder.tv_userName.setText(item.getUserName());
        if(item.getId()==User.getInstance().getId())
            holder.ib_request.setEnabled(false);

        holder.item = item;
    }



    public void addItem(UserItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int id){
        if(items.size()!=0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == id) {
                    items.remove(items.get(i));
                    notifyDataSetChanged();
                }
            }
            if (items.size() == 0) {
                listener.onAllItemsRemoved(teamId);
                notifyDataSetChanged();
            }
        }
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public void update() {
        if(this.getItemCount()==0&&memberDocument.getTeam().getUsers().size()!=0){
            for (UserItem current: memberDocument.getTeam().getUsers()) {
                addItem(current);
                if(current.getId()==User.getInstance().getId())
                    EventDetailsTeamsFragment.setJoinedTeam(teamId);
            }
        }
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public interface MemberClickListener{
        void onItemChanged(UserItem item);
        void onFriendRequestSent(UserItem item);
        void onAllItemsRemoved(int teamId);
    }
    public void removeById(int userId){

    }

    class MemberViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id;
        ImageView iv_profile;
        TextView tv_userName;
        ImageButton ib_request;

        UserItem item;

        MemberViewHolder(final View itemView) {
            super(itemView);

            if (itemView != null) {
                tv_id = itemView.findViewById(R.id.tv_memberid);
                iv_profile = itemView.findViewById(R.id.iv_memberprofile);
                tv_userName = itemView.findViewById(R.id.tv_memberusername);
                ib_request = itemView.findViewById(R.id.ib_memberrequestbutton);

                ib_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onFriendRequestSent(item);
                        ib_request.setEnabled(false);
                        listener.onFriendRequestSent(item);
                        Snackbar.make(itemView, "The request has been sent!", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
