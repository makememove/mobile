package makememove.ml.makememove.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.SportEventStatusFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.persistence.SportItem;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;


public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private final List<SportItem> items;
    private SportItemClickListener listener;
    public SportAdapter(SportItemClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.sportcsempe,parent,false);
        return new SportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position){
        SportItem item = items.get(position);
        holder.tv_title.setText(item.category);
        holder.item = item;
        GlobalClass.setSportPicture(item.category,holder.iv_sportpicture);

    }

    public void addItem(SportItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(SportItem item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void update(List<SportItem> sportItems) {
        items.clear();
        items.addAll(sportItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface SportItemClickListener{
        void onItemChanged(SportItem item);
        void onItemRemoved(SportItem item);
        void onUnfollow(int position);
        void onAllItemsRemoved();
    }


    class SportViewHolder extends RecyclerView.ViewHolder {
        ImageButton removeButton;
        TextView  tv_title;
        ImageView iv_sportpicture;

        SportItem item;


        SportViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {

                tv_title=itemView.findViewById(R.id.tv_sporttitle);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SportEventStatusFragment sportEventFragment = SportEventStatusFragment.newInstance(
                                UserMainFragment.getPreferredPosition(item.category));
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, sportEventFragment).addToBackStack(null)
                                .commit();
                    }
                });

                removeButton = itemView.findViewById(R.id.removeButton);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item != null) {
                            removeItem(item);
                            listener.onItemRemoved(item);
                            listener.onUnfollow(UserMainFragment.getPosition(item.category)+1);
                        }
                    }
                });

                iv_sportpicture=itemView.findViewById(R.id.iv_Sportpicture);


            }
        }
    }
}
