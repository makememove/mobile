package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        holder.creatorTextView.setText("Creator: "+Integer.toString(item.getCreator()));//TODO név kiírása e helyett
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        holder.dateTextView.setText("Date: "+dt.format(item.getDate()));
        holder.eventTypeTextView.setText("Category ID: "+Integer.toString(item.getCategoryId()));//TODO kategória nevének kiírása, nem IDt
        holder.id.setText(Integer.toString(position+1));

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
        TextView dateTextView;
        TextView eventTypeTextView;
        TextView id;


        EventViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                titleButton = itemView.findViewById(R.id.eventButton);
                creatorTextView = itemView.findViewById(R.id.tv_EventCreator);
                dateTextView = itemView.findViewById(R.id.tv_Eventdate);
                eventTypeTextView = itemView.findViewById(R.id.tv_eventtype);
                id=itemView.findViewById(R.id.tv_eventID);


                titleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventDetailsFragment eventDetailsFragment = EventDetailsFragment.newInstance(item);
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, eventDetailsFragment).addToBackStack(null)
                                .commit();
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
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

