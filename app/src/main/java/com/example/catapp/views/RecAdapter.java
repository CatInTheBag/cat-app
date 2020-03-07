package com.example.catapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catapp.R;
import com.example.catapp.data.CatFact;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<CatFact> items;
    private Context context;
    private ViewHolder.CatFactItemListener clickListener;

    public RecAdapter(List<CatFact> items, Context context, ViewHolder.CatFactItemListener clickListener) {
        this.items = items;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(v,clickListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CatFact catFact = items.get(i);
        viewHolder.textViewHeader.setText(catFact.getHeader());
        viewHolder.textViewDescription.setText(catFact.getFact());
        viewHolder.setCatFact(catFact);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
