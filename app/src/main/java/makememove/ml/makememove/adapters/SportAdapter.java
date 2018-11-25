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
import makememove.ml.makememove.activities.fragments.SportEventStatusFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.Sport;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;


public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private final List<Sport> items;
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
        final Sport item = items.get(position);
        holder.tv_title.setText(item.getName());
        GlobalClass.setSportPicture(item.getName(),holder.iv_sportpicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SportEventStatusFragment sportEventFragment = new SportEventStatusFragment();
                sportEventFragment.setCurrentSport( UserMainFragment.getPreferredId(item.getName()));
                fragmentManager.beginTransaction()
                        .replace(R.id.content, sportEventFragment).addToBackStack(null)
                        .commit();
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(item);
                listener.onItemRemoved(item);
                listener.onUnfollow(UserMainFragment.getPosition(item.getName())+1);
            }
        });

    }

    public void addItem(Sport item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(Sport item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void update(List<Sport> sport) {
        items.clear();
        items.addAll(sport);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface SportItemClickListener{
        void onUnfollow(int position);
        void onAllItemsRemoved();
        void onItemRemoved(Sport item);
    }


    class SportViewHolder extends RecyclerView.ViewHolder {
        ImageButton removeButton;
        TextView  tv_title;
        ImageView iv_sportpicture;

        SportViewHolder(View itemView) {
            super(itemView);
                tv_title=itemView.findViewById(R.id.tv_sporttitle);
                removeButton = itemView.findViewById(R.id.removeButton);
                iv_sportpicture=itemView.findViewById(R.id.iv_Sportpicture);
        }
    }
}
