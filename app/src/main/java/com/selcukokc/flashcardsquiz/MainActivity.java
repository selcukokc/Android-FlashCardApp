package com.selcukokc.flashcardsquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Database db;
    private ArrayList<String> cardsetList;
    private CardsSetAdapter cardsSetAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rv);
        fab=findViewById(R.id.fab);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CREATE CARD SET");
        setSupportActionBar(toolbar);

        db=new Database(this);
        cardsetList = new CardsDao().allCardSets(db);

        cardsSetAdapter = new CardsSetAdapter(cardsetList,MainActivity.this);
        rv.setAdapter(cardsSetAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertDesign = getLayoutInflater().inflate(R.layout.alertdialog_design, null);
                final EditText editTextAlert = alertDesign.findViewById(R.id.editTextAlert);

                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("CREATE CARD SET");
                ad.setView(alertDesign);

                ad.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txtCardSetName;
                        txtCardSetName = editTextAlert.getText().toString();
                        new CardsDao().addTable(txtCardSetName,db);
                        recreate();

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







    }
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}