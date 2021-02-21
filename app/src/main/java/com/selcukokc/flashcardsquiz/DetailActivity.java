package com.selcukokc.flashcardsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextTerm, editTextDescription;
    private Cards card;
    private Database db;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar=findViewById(R.id.toolbar);
        editTextTerm=findViewById(R.id.editTextTerm);
        editTextDescription=findViewById(R.id.editTextDescription);

        sp=getSharedPreferences("tableNameSP",MODE_PRIVATE);
        editor=sp.edit();
        tableName=sp.getString("tableName","NULL");


        toolbar.setTitle("Edit Card");
        setSupportActionBar(toolbar);

        card = (Cards) getIntent().getSerializableExtra("object");
        db = new Database(this);

        editTextTerm.setText(card.getTerm());
        editTextDescription.setText(card.getDescription());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemEdit:
                String term = editTextTerm.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();


                if(TextUtils.isEmpty(term)){
                    Snackbar.make(toolbar,"Input term",Snackbar.LENGTH_LONG).show();
                    return false;
                }

                if(TextUtils.isEmpty(description)){
                    Snackbar.make(toolbar,"Input description",Snackbar.LENGTH_LONG).show();
                    return false;
                }

                new CardsDao().updateCard(db,card.getWord_id(),term,description,tableName);

                Intent intent = new Intent(DetailActivity.this, CardsActivity.class);
                intent.putExtra("tableName", tableName);
                startActivity(intent);
                finish();
                return true;

                case R.id.itemDelete:
                Snackbar.make(toolbar,"Do you want to delete this card ?",Snackbar.LENGTH_SHORT)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                new CardsDao().deleteCard(db,card.getWord_id(),tableName);
                                Intent intent = new Intent(DetailActivity.this, CardsActivity.class);
                                intent.putExtra("tableName", tableName);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
                return true;

            default:
        }


        return super.onOptionsItemSelected(item);
    }
}