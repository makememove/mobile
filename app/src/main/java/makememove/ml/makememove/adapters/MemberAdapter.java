package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{

    private List<UserItem> items;
    private MemberAdapter.MemberClickListener listener;
    public MemberAdapter(){
        items = new ArrayList<UserItem>();
    }
    public MemberAdapter(MemberAdapter.MemberClickListener listener){
        items = new ArrayList<UserItem>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rankcsempe,parent,false);
        return new MemberAdapter.MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position){
        UserItem item = items.get(position);

        holder.tv_id.setText(position+1);
        holder.tv_userName.setText(item.getUserName());

        holder.item = item;
    }

    public void addItem(UserItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int id){
        for(int i = 0;i<items.size();i++){
            if(items.get(i).getId()==id) {
                items.remove(items.get(i));
                notifyDataSetChanged();
            }
        }
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface MemberClickListener{
        void onItemChanged(UserItem item);
        void onFriendRequestSent(UserItem item);
        void onAllItemsRemoved();
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
                ib_request = itemView.findViewById(R.id.ib_memberrequest);

                ib_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onFriendRequestSent(item);
                        ib_request.setVisibility(View.GONE);
                        Snackbar.make(itemView, "The request has been sent!", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
