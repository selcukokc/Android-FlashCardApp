package com.selcukokc.flashcardsquiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CardsSetAdapter extends RecyclerView.Adapter<CardsSetAdapter.CardDesignHolder> {
    private List<String> cardsSetList;
    private Context mContext;
    Database db;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public CardsSetAdapter(List<String> cardsSetList, Context mContext) {
        this.cardsSetList = cardsSetList;
        this.mContext = mContext;
        db = new Database(mContext);
    }

    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design2,parent,false);
        return new CardDesignHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardDesignHolder holder, int position) {
        final String strSet = cardsSetList.get(position);
        holder.textView.setText(strSet);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                ad.setMessage("Do you want to delete this card set");
                ad.setTitle("Warning");
                ad.setIcon(R.drawable.delete);
                ad.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tableName;
                        tableName = holder.textView.getText().toString();
                        new CardsDao().dropTable(db,tableName);
                        Toast.makeText(mContext,"CARD SET HAS BEEN DELETED",Toast.LENGTH_SHORT).show();
                        ((MainActivity)mContext).recreate();

                    }
                });

                ad.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,"CANCELLED",Toast.LENGTH_SHORT).show();

                    }
                });
                ad.create().show();

            }

        });

        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertDesign = LayoutInflater.from(mContext).inflate(R.layout.alertdialog_design, null);
                final EditText editTextAlert = alertDesign.findViewById(R.id.editTextAlert);

                AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                ad.setTitle("EDIT CARD SET NAME");
                ad.setView(alertDesign);
                ad.setIcon(R.drawable.edit);
                ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txtCardSetName, editedCardSetName;
                        txtCardSetName = holder.textView.getText().toString().trim();
                        editedCardSetName = editTextAlert.getText().toString().trim();
                        editedCardSetName=editedCardSetName.replaceAll("\\s+","");
                        editedCardSetName=editedCardSetName.replaceAll("\\W+","");


                        if(TextUtils.isEmpty(editedCardSetName)) {
                            Toast.makeText(mContext,"THIS FIELD CAN'T BE EMPTY",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            new CardsDao().updateTableName(db, txtCardSetName, editedCardSetName);
                            ((MainActivity) mContext).recreate();
                        }

                    }
                });

                ad.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                ad.create().show();



            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp=mContext.getSharedPreferences("tableNameSP", Context.MODE_PRIVATE);
                editor=sp.edit();
                editor.putString("tableName",holder.textView.getText().toString());
                editor.commit();


                Intent intent = new Intent(mContext,CardsActivity.class);
                intent.putExtra("tableName", holder.textView.getText().toString());
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return cardsSetList.size();
    }

    public class CardDesignHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageButton imageButton,imageButton2;
        CardView cardView;

        public CardDesignHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton2 = itemView.findViewById(R.id.imageButton2);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }


}
