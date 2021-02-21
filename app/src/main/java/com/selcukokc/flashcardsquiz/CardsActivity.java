package com.selcukokc.flashcardsquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd, fabQuiz;
    private RecyclerView rv;
    private Database db;
    private ArrayList<Cards> cardsList;
    private CardsAdapter cardsAdapter;
    private String tableName;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        fabAdd=findViewById(R.id.fabAdd);
        fabQuiz=findViewById(R.id.fabQuiz);
        rv=findViewById(R.id.rv);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("ADD CARD & TAKE A QUIZ");
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = new Database(this);

        tableName = getIntent().getStringExtra("tableName");
        cardsList = new CardsDao().allCards(db,tableName);

        cardsAdapter = new CardsAdapter(cardsList,getApplicationContext());
        rv.setAdapter(cardsAdapter);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardsActivity.this, CreateCardActivity.class);
                intent.putExtra("tableName",tableName);
                startActivity(intent);
                finish();

            }
        });

        fabQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean control = new CardsDao().isCardSetEmpty(tableName,db);
                if(control==true){  // if selected card set is empty, quiz will not be created.
                    Toast.makeText(getApplicationContext(),"THIS SET DOES NOT HAVE ANY CARDS",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(CardsActivity.this, QuizActivity.class);
                    intent.putExtra("tableName", tableName);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}