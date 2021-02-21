package com.selcukokc.flashcardsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private TextView txtQuestionCounter,txtTerm,txtCorrectAnswer;
    private EditText editTextAnswer;
    private Button btnQuit,btnNext;
    private ImageView imageViewSend, imageViewControl;
    private Database db;
    private ArrayList<Cards> questionsList;
    private Cards cardQuestion;
    private int questionCounter = 0;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestionCounter = findViewById(R.id.txtQuestionCounter);
        txtTerm = findViewById(R.id.txtTerm);
        txtCorrectAnswer = findViewById(R.id.txtCorrectAnswer);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        btnQuit = findViewById(R.id.btnQuit);
        btnNext = findViewById(R.id.btnNext);
        imageViewControl = findViewById(R.id.imageViewControl);
        imageViewSend = findViewById(R.id.imageViewSend);

        tableName = getIntent().getStringExtra("tableName");

        db = new Database(this);
        questionsList = new CardsDao().allCards(db,tableName);
        setQuestion();

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionControl();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterControl();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CardsActivity.class);
                intent.putExtra("tableName",tableName);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setQuestion(){
        editTextAnswer.setText("");
        imageViewControl.setVisibility(View.INVISIBLE);
        txtCorrectAnswer.setTextColor(getResources().getColor(R.color.questionTxtColor));
        txtCorrectAnswer.setVisibility(View.INVISIBLE);
        txtQuestionCounter.setText((questionCounter+1)+"/"+questionsList.size());
        cardQuestion = questionsList.get(questionCounter);

        txtTerm.setText(cardQuestion.getTerm());

    }


    public void counterControl(){
        questionCounter++;
        if(questionCounter!=questionsList.size()){
            setQuestion();
        }
        else{
            Toast.makeText(getApplicationContext(),"QUIZ HAS BEEN COMPLETED",Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), CardsActivity.class);
                    finish();

        }

    }

    public void questionControl(){

        cardQuestion = questionsList.get(questionCounter);
        String strDescription = cardQuestion.getDescription();
        String strAnswer = editTextAnswer.getText().toString().trim();

        if(strDescription.toUpperCase().equals(strAnswer.toUpperCase())){
            txtCorrectAnswer.setVisibility(View.VISIBLE);
            imageViewControl.setVisibility(View.VISIBLE);
            imageViewControl.setImageResource(R.drawable.true_img);
            txtCorrectAnswer.setTextColor(getResources().getColor(R.color.trueTxtColor));
            txtCorrectAnswer.setText("CORRECT!");
        }
        else{
            txtCorrectAnswer.setVisibility(View.VISIBLE);
            imageViewControl.setVisibility(View.VISIBLE);
            imageViewControl.setImageResource(R.drawable.false_img);
            txtCorrectAnswer.setTextColor(getResources().getColor(R.color.falseTxtColor));
            txtCorrectAnswer.setText(strDescription);
        }





    }

}