package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import makememove.ml.makememove.R;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;


public class ModifyResultAdapter extends RecyclerView.Adapter<ModifyResultAdapter.ModifyResultViewHolder> {

    private final List<ResultDocument> items;
    private ModifyResultAdapter.ModifyResultClickListener  listener;

    public ModifyResultAdapter(){
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ModifyResultAdapter.ModifyResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.resultformodifycsempe,parent,false);
        return new ModifyResultAdapter.ModifyResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModifyResultAdapter.ModifyResultViewHolder holder, final int position){
        final ResultDocument item = items.get(position);
        holder.tv_id.setText(Integer.toString(position+1));
        holder.tv_teamname.setText(items.get(position).getTeamName());
        holder.ib_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position != 0) {
                    items.remove(item);
                    items.add(position - 1, item);
                    notifyItemInserted(items.size() - 1);
                    notifyDataSetChanged();
                }
            }
        });

        holder.ib_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position != items.size()-1) {
                    items.remove(item);
                    items.add(position + 1, item);
                    notifyItemInserted(items.size() - 1);
                    notifyDataSetChanged();
                }
            }
        });
    }
    public List<ResultDocument> getItems(){
        return items;
    }

    public void addItem(ResultDocument item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface ModifyResultClickListener{
        void onAdapterChanged();
    }


    class ModifyResultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_teamname;
        ImageButton ib_up;
        ImageButton ib_down;
        ResultDocument item;
        int position;

        ModifyResultViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_resultmodifyposition);
            tv_teamname= itemView.findViewById(R.id.tv_resultmodifyteamname);
            ib_up =itemView.findViewById(R.id.ib_resultmodifyup);
            ib_down = itemView.findViewById(R.id.ib_resultmodifydown);



        }
    }
}
