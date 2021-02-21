package com.selcukokc.flashcardsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class CreateCardActivity extends AppCompatActivity {
    private EditText editTextTerm,editTextDescription;
    private Button buttonSave;
    private Database db;
    private String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        editTextTerm = findViewById(R.id.editTextTerm);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSave = findViewById(R.id.buttonSave);

        tableName = getIntent().getStringExtra("tableName");

        db = new Database(this);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String term = editTextTerm.getText().toString().trim();
               String description = editTextDescription.getText().toString().trim();

               if(TextUtils.isEmpty(term)){
                   Snackbar.make(v,"Input a term", Snackbar.LENGTH_LONG).show();
                   return;
               }

               if(TextUtils.isEmpty(description)){
                    Snackbar.make(v,"Input a description", Snackbar.LENGTH_LONG).show();
                    return;
               }

               new CardsDao().addCard(db,term,description,tableName);

                Intent intent = new Intent(CreateCardActivity.this, CardsActivity.class);
                intent.putExtra("tableName",tableName);
                startActivity(intent);
                finish();

            }
        });

    }
}