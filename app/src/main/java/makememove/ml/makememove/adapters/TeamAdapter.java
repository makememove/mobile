package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> implements MemberAdapter.MemberClickListener, BaseView {

    private List<Team> items;
    private TeamAdapter.TeamClickListener listener;
    public TeamAdapter(TeamAdapter.TeamClickListener listener){
        items = new ArrayList<Team>();
        this.listener = listener;
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

    }

    @Override
    public void onAllItemsRemoved() {

    }

    @Override
    public void update() {

    }

    public interface TeamClickListener{
        void onItemChanged(Team item);
        void onItemRemoved(Team item);
        void onItemJoined(Team item);
        void onItemLeft(Team item);
        void onAllItemsRemoved();
    }

    private void initRecylerView(RecyclerView recyclerView, MemberAdapter adapter, View view){
        recyclerView = view.findViewById(R.id.rv_teammemberrecyler);
        adapter = new MemberAdapter(this);
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

               initRecylerView(recyclerView,adapter,itemView);

               join.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //if(adapter!=null) {
//                           if (item.getCapacity() > adapter.getItemCount()) {

                               listener.onItemJoined(item);

                               UserItem user = new UserItem();
                               user.setUserName(User.getInstance().getUserName());
                               user.setId(User.getInstance().getId());
                               //adapter.addItem(user);

                               join.setVisibility(View.GONE);
                               leave.setVisibility(View.VISIBLE);
                      //     } else
                       //        Snackbar.make(itemView, "The team is already in its full capacity!", Snackbar.LENGTH_LONG).show();
                    //   }
                   }
               });
               leave.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       listener.onItemLeft(item);
                       //adapter.removeItem(item.getId());
                       join.setVisibility(View.VISIBLE);
                       leave.setVisibility(View.GONE);
                   }
               });
            }
        }
    }
}
