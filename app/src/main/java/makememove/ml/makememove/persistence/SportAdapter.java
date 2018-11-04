package makememove.ml.makememove.persistence;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;
import makememove.ml.makememove.activities.fragments.SportEventStatusFragment;
import makememove.ml.makememove.activities.fragments.UserMainFragment;

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
        holder.selectButton.setText(item.category);

        holder.item = item;
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
        void onAllItemsRemoved();
    }


    class SportViewHolder extends RecyclerView.ViewHolder {
        Button selectButton;
        ImageButton removeButton;

        SportItem item;


        SportViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                selectButton = itemView.findViewById(R.id.sportButton);
                removeButton = itemView.findViewById(R.id.removeButton);

                selectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SportEventStatusFragment sportEventFragment = new SportEventStatusFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("SportID",UserMainFragment.getPosition(item.category));
                        sportEventFragment.setArguments(bundle);
                        //TODO argumentumok lekezélese (lekérdezés, megjelenítés) a SportEventStatusFragment-ben
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, sportEventFragment)
                                .commit();
                    }
                });

                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item != null) {
                            removeItem(item);
                            listener.onItemRemoved(item);
                        }
                    }
                });

            }
        }
    }
}
