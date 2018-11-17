package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.dpsystem.documents.subdocuments.UserRank;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.user.User;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder>{

    private List<UserRank> items;
    private RankAdapter.RankItemClickListener listener;
    public RankAdapter(){
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rankcsempe,parent,false);
        return new RankAdapter.RankViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.RankViewHolder holder, int position){
        UserRank item = items.get(position);
        holder.rank.setText(Integer.toString(position+1));
        holder.username.setText(item.getUserName());

        holder.item = item;
    }

    public void addItem(UserRank item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(UserItem item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void update(List<UserRank> sportItems) {
        items.clear();
        items.addAll(sportItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface RankItemClickListener{
        void onItemChanged(UserItem item);
        void onItemRemoved(UserItem item);
        void onAllItemsRemoved();
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        TextView rank;
        ImageView profile;
        TextView username;

        UserRank item;

        RankViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                rank = itemView.findViewById(R.id.tv_rankposition);
                profile = itemView.findViewById(R.id.iv_rankprofile);
                username = itemView.findViewById(R.id.tv_rankusername);
            }
        }
    }
}
