package com.selcukokc.flashcardsquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardDesignHolder> {
    private List<Cards> cardsList;
    private Context mContext;


    public CardsAdapter(List<Cards> cardsList, Context mContext) {
        this.cardsList = cardsList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,parent,false);
        return new CardDesignHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDesignHolder holder, int position) {
        final Cards card = cardsList.get(position);
        holder.txtTerm.setText(card.getTerm());
        holder.txtDescription.setText(card.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailActivity.class);
                intent.putExtra("object", card);
                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public class CardDesignHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtTerm,txtDescription;

        public CardDesignHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtTerm = itemView.findViewById(R.id.txtTerm);
            txtDescription = itemView.findViewById(R.id.txtDescription);


        }
    }


}
