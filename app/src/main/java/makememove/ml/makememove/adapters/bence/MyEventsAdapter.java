package makememove.ml.makememove.adapters.bence;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.activities.fragments.eventfragments.EventModifyFragment;
import makememove.ml.makememove.adapters.EventAdapter;
import makememove.ml.makememove.dpsystem.documents.EventDocument;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.EventViewHolder> {

    private final List<EventDocument> items;
    private MyEventsAdapter.MyEventsClickListener listener;
    public MyEventsAdapter(MyEventsAdapter.MyEventsClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyEventsAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.myeventseventcsempe,parent,false);
        return new MyEventsAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsAdapter.EventViewHolder holder, int position){
        EventDocument item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.creatorTextView.setText("Creator: "+item.getCreatorMockup().getUserName());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        holder.dateTextView.setText("Date: "+dt.format(item.getDate()));
        holder.eventTypeTextView.setText(item.getCategory().getName());
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

    public interface MyEventsClickListener {
        void onItemChanged(EventDocument item);
    }


    class EventViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EventDocument item;
        TextView creatorTextView;
        TextView dateTextView;
        TextView eventTypeTextView;
        TextView id;
        Button bt_modify;


        EventViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                title= itemView.findViewById(R.id.tv_title);
                creatorTextView = itemView.findViewById(R.id.tv_EventCreator);
                dateTextView = itemView.findViewById(R.id.tv_Eventdate);
                eventTypeTextView = itemView.findViewById(R.id.tv_eventtype);
                id=itemView.findViewById(R.id.tv_eventID);
                bt_modify=itemView.findViewById(R.id.bt_unfinmodify);


                bt_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventModifyFragment eventModifyFragment = new EventModifyFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, eventModifyFragment).addToBackStack(null)
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

