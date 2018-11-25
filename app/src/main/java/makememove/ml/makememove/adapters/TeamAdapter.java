package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsTeamsFragment;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.MemberDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.dpsystem.presenters.MemberPresenter;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> implements MemberAdapter.MemberClickListener, BaseView {

    private static List<Team> items;
    private MemberAdapter rvAdapter;
    private MemberDocument memberDocument;
    private TeamAdapter.TeamClickListener listener;
    public TeamAdapter(TeamAdapter.TeamClickListener listener){
        items = new ArrayList<Team>();
        this.listener = listener;
    }

    public void leaveAndJoin(int position){

    }

    public void leaveTeamButton(int teamId){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.teamcsempe,parent,false);
        return new TeamAdapter.TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.TeamViewHolder holder, int position){
        Team item = items.get(position);
        holder.teamName.setText(item.getName());
        holder.adapter.setTeamId(item.getId());

        initRecylerView(holder.recyclerView,holder.adapter,holder.itemView);

        if(item.getId()==EventDetailsTeamsFragment.getJoinedTeam()) {
            holder.join.setVisibility(View.GONE);
            holder.leave.setVisibility(View.VISIBLE);
        }
        else {
            holder.join.setVisibility(View.VISIBLE);
            holder.leave.setVisibility(View.GONE);
        }
        if(EventDetailsFragment.getCurrentEvent().getClosed()==1){
            holder.join.setVisibility(View.GONE);
            holder.leave.setVisibility(View.GONE);
        }
        holder.item = item;
    }

    public void addItem(Team item) {
        items.add(item);

        notifyItemInserted(items.size() - 1);
    }


    public void removeItem(Team item){
        items.remove(item);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public void onItemChanged(UserItem item) {

    }

    @Override
    public void onFriendRequestSent(UserItem item) {
        PostPresenter pp = new PostPresenter();
        pp.sendFriendRequest(User.getInstance().getToken(),item.getId());
    }

    @Override
    public void onAllItemsRemoved(int teamId) {
        for(int i = 0;i<items.size();i++){
            if(items.get(i).getId()==teamId)
                removeItem(items.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void update() {

        if(rvAdapter.getItemCount()==0&&memberDocument.getTeam().getUsers().size()!=0){
            for (UserItem current: memberDocument.getTeam().getUsers()) {
                rvAdapter.addItem(current);
            }
        }
    }

    public static void clearEverything(){
        items.clear();
    }

    public interface TeamClickListener{
        void onChanged();
        void onItemRemoved(Team item);
        void onItemJoined(Team item);
        void onItemLeft(Team item);
        void onAllItemsRemoved();
    }

    private void initRecylerView(RecyclerView recyclerView, MemberAdapter adapter, View view){
        recyclerView = view.findViewWithTag("teammemberrv");
        rvAdapter = adapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }


    class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        Button join;
        Button leave;
        RecyclerView recyclerView;
        MemberAdapter adapter;

        Team item;


        TeamViewHolder(final View itemView) {
            super(itemView);
            if (itemView != null) {
               teamName = itemView.findViewById(R.id.tv_teamnameset);
               join = itemView.findViewById(R.id.bt_join);
               leave = itemView.findViewById(R.id.bt_leave);
               adapter = new MemberAdapter(TeamAdapter.this);

               if(item!=null) {
                   if (EventDetailsTeamsFragment.getJoinedTeam() != item.getId()) {
                       join.setVisibility(View.VISIBLE);
                       leave.setVisibility(View.GONE);
                   }
               }

               join.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //if(adapter!=null) {
                           if (item.getCapacity()==null || item.getCapacity() > adapter.getItemCount()) {
                               listener.onItemJoined(item);

                               UserItem user = new UserItem();
                               user.setUserName(User.getInstance().getUserName());
                               user.setId(User.getInstance().getId());
                                listener.onChanged();
                               adapter.addItem(user);

                               join.setVisibility(View.GONE);
                               leave.setVisibility(View.VISIBLE);
                              // EventDetailsTeamsFragment.refreshRecyclerView();

                               //     } else
                       //        Snackbar.make(itemView, "The team is already in its full capacity!", Snackbar.LENGTH_LONG).show();
                               }
                   }
               });
               leave.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       adapter.removeItem(User.getInstance().getId());
                       listener.onItemLeft(item);
                       join.setVisibility(View.VISIBLE);
                       leave.setVisibility(View.GONE);
                   }
               });
            }
        }
    }
}
