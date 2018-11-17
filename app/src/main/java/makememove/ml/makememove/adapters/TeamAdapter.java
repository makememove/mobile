package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.globals.GlobalClass;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder>{

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

    public interface TeamClickListener{
        void onItemChanged(Team item);
        void onItemRemoved(Team item);
        void onItemJoined(Team item);
        void onItemLeft(Team item);
        void onAllItemsRemoved();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        Button join;
        Button leave;

        Team item;

        TeamViewHolder(final View itemView) {
            super(itemView);

            if (itemView != null) {
               teamName = itemView.findViewById(R.id.tv_teamnameset);
               join = itemView.findViewById(R.id.bt_join);
               leave = itemView.findViewById(R.id.bt_leave);

               join.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Log.d("Capacity","Capacity "+item.getCapacity()+" "+items.size());
                       if(item.getCapacity()!=items.size()) {
                           listener.onItemJoined(item);
                           join.setVisibility(View.GONE);
                           leave.setVisibility(View.VISIBLE);
                       }
                       else
                           Snackbar.make(itemView, "The team is already in its full capacity!", Snackbar.LENGTH_LONG).show();
                   }
               });
               leave.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       listener.onItemLeft(item);
                       join.setVisibility(View.VISIBLE);
                       leave.setVisibility(View.GONE);
                   }
               });
            }
        }
    }
}
