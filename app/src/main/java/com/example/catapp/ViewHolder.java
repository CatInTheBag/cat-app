package com.example.catapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView textViewHeader;
    public TextView textViewDescription;
    public LinearLayout linearLayout;
    public CatFact catFact;

    public ViewHolder(@NonNull View itemView, final CatFactItemListener listener) {
        super(itemView);

        textViewHeader = itemView.findViewById(R.id.heading_text_view);
        textViewDescription = itemView.findViewById(R.id.description_text_view);
        linearLayout = itemView.findViewById(R.id.linear_layout);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.onCatFactItemClicked(catFact);
            }
        });
    }

    public void setCatFact(CatFact catFact) {
        this.catFact = catFact;
    }

    public interface CatFactItemListener{
        void onCatFactItemClicked(CatFact catFact);
    }
}