package com.meme.p2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meme.p2.R;
import com.meme.p2.SearchModel;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{
    public TextView name , location , drug;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        location = (TextView)itemView.findViewById(R.id.location);
        drug = (TextView)itemView.findViewById(R.id.drug);


    }
}

public class SearchAda extends RecyclerView.Adapter<SearchViewHolder> {
    private Context context;
    private List<SearchModel> searchModels;

    public SearchAda(Context context, List<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item,parent,false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.name.setText(searchModels.get(position).getPh_name());
        holder.location.setText(searchModels.get(position).getPh_address());
        holder.drug.setText(searchModels.get(position).getMed_name());


    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }
}
