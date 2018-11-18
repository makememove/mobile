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
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.user.User;

public class FriendAddAdapter extends RecyclerView.Adapter<FriendAddAdapter.FriendViewHolder>{

    private final List<UserItem> items;
    private FriendAddAdapter.FriendItemClickListener listener;
    public FriendAddAdapter(FriendAddAdapter.FriendItemClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public FriendAddAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.friendreceivedcsempe,parent,false);
        return new FriendAddAdapter.FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAddAdapter.FriendViewHolder holder, int position){
        UserItem item = items.get(position);
        holder.tv_id.setText(Integer.toString(position+1));
        holder.tv_username.setText(item.getUserName());

        holder.item = item;
    }

    public void addItem(UserItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(UserItem item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void update(List<UserItem> sportItems) {
        items.clear();
        items.addAll(sportItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface FriendItemClickListener{
        void onItemChanged(UserItem item);
        void onItemRemoved(UserItem item);
        void onAllItemsRemoved();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        ImageView iv_picture;
        TextView tv_username;
        ImageButton ib_accept;

        UserItem item;

        FriendViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {

                tv_id=itemView.findViewById(R.id.tv_receivefriendiD);
                iv_picture = itemView.findViewById(R.id.iv_receivefriendprofile);
                tv_username = itemView.findViewById(R.id.tv_receivefriendusername);
                ib_accept = itemView.findViewById(R.id.ib_receiveacceptfriendrequest);

                ib_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PostPresenter pp = new PostPresenter();
                        pp.acceptFriendRequest(User.getInstance().getToken(),item.getId());
                        removeItem(item);
                    }
                });

            }
        }
    }
}
