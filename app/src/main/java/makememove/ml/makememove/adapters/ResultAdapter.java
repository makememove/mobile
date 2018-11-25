package makememove.ml.makememove.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private final List<ResultDocument> items;
    private ResultAdapter .ResultClickListener  listener;

    public ResultAdapter (ResultAdapter.ResultClickListener listener){
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.resultfordisplaycsempe,parent,false);
        return new ResultAdapter .ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ResultViewHolder holder, int position){
        holder.tv_id.setText(Integer.toString(position+1));
        holder.tv_teamname.setText(items.get(position).getTeamName());
    }

    public void addItem(ResultDocument item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public interface ResultClickListener{
        void onItemChanged(EventDocument item);
    }


    class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_teamname;


        ResultViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_resultdisplayposition);
            tv_teamname= itemView.findViewById(R.id.tv_resultdisplayteamname);
        }
    }
}
