package makememove.ml.makememove.persistence;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.SportEventStatusFragment;

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
        holder.selectButton.setText(item.category.name());

        holder.item = item;
    }

    public void addItem(SportItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
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
    }


    class SportViewHolder extends RecyclerView.ViewHolder {
        Button selectButton;
        SportItem item;

        SportViewHolder(View itemView) {
            super(itemView);
            selectButton = itemView.findViewById(R.id.sportButton);
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SportEventStatusFragment sportEventFragment= new SportEventStatusFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }
}
