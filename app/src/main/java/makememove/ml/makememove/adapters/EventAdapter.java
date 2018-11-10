package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.dpsystem.documents.EventDocument;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<EventDocument> items;
    private EventAdapter.EventItemClickListener listener;
    public EventAdapter(EventAdapter.EventItemClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.eventcsempe,parent,false);
        return new EventAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position){
        EventDocument item = items.get(position);
        holder.titleButton.setText(item.getTitle());
        holder.creatorTextView.setText(item.getCreator().getUserName());
        holder.numPeopleTextView.setText("LATER TODO");
        holder.eventTypeTextView.setText(item.getCategory().getName());

        holder.item = item;
    }

    public void addItem(EventDocument item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface EventItemClickListener{
        void onItemChanged(EventDocument item);
    }


    class EventViewHolder extends RecyclerView.ViewHolder {
        Button titleButton;
        EventDocument item;
        TextView creatorTextView;
        TextView numPeopleTextView;
        TextView eventTypeTextView;


        EventViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                titleButton = itemView.findViewById(R.id.eventButton);
                creatorTextView = itemView.findViewById(R.id.tv_EventCreator);
                numPeopleTextView = itemView.findViewById(R.id.tv_NumPeople);
                eventTypeTextView = itemView.findViewById(R.id.tv_eventtype);

                titleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
                        EventDetailsFragment.setCurrentEvent(item);
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, eventDetailsFragment).addToBackStack(null)
                                .commit();
                    }
                });
            }
        }
    }
}

