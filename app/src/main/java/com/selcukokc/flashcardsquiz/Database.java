package com.selcukokc.flashcardsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "flashcards.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /* db.execSQL("CREATE TABLE \"cards\" (\n" +
                "\t\"word_id\"\tINTEGER,\n" +
                "\t\"term\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"word_id\")\n" +
                ");");  */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* db.execSQL("DROP TABLE IF EXISTS cards");
        onCreate(db); */
    }





}
