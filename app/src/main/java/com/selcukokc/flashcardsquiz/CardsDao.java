package com.selcukokc.flashcardsquiz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;


public class CardsDao {
    public ArrayList<Cards> allCards(Database dba,String tableName){
        ArrayList<Cards> cardsArrayList = new ArrayList<>();
        SQLiteDatabase db = dba.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tableName,null);

        while (c.moveToNext()){
            Cards card = new Cards(c.getInt(c.getColumnIndex("word_id"))
                    ,c.getString(c.getColumnIndex("term"))
                    ,c.getString(c.getColumnIndex("description")));

            cardsArrayList.add(card);
        }

        return cardsArrayList;
    }

    public ArrayList<String> allCardSets(Database dba){
        ArrayList<String> cardsetList = new ArrayList<>();
        SQLiteDatabase db = dba.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                cardsetList.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }
        db.close();
        return cardsetList;
    }

    public boolean isCardSetEmpty(String tableName, Database dba){
        SQLiteDatabase db = dba.getWritableDatabase();
        ArrayList<Cards> tempList = new ArrayList();
        Cursor c = db.rawQuery("SELECT* FROM " + tableName,null);
        while (c.moveToNext()){
            Cards card = new Cards(c.getInt(c.getColumnIndex("word_id"))
                    ,c.getString(c.getColumnIndex("term"))
                    ,c.getString(c.getColumnIndex("description")));

            tempList.add(card);
        }

        int counter = tempList.size();


        db.close();


        if(counter==0)
            return true;
        else
            return false;


    }


    public void addTable(String tableName, Database dba){
        SQLiteDatabase db = dba.getWritableDatabase();

        db.execSQL("CREATE TABLE " + tableName + " (\n" +
                "\t\"word_id\"\tINTEGER,\n" +
                "\t\"term\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"word_id\")\n" +
                ");");
        db.close();

    }

    public void dropTable(Database dba,String tableName){
        SQLiteDatabase db = dba.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.close();

    }
    public void updateTableName(Database dba,String tableName,String newTableName){
        SQLiteDatabase db = dba.getWritableDatabase();
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + newTableName);
        db.close();

    }


    public void deleteCard(Database dba,int word_id,String tableName){
        SQLiteDatabase db=dba.getWritableDatabase();
        db.delete(tableName , "word_id=?",new String[] {String.valueOf(word_id)} );
        db.close();

    }


    public void addCard(Database dba,String term,String description,String tableName){

        SQLiteDatabase db=dba.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("term",term );
        values.put("description",description );


        db.insertOrThrow(tableName, null, values);
        db.close();

    }

    public void updateCard(Database dba,int word_id,String term,String description,String tableName){

        SQLiteDatabase db=dba.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("term",term );
        values.put("description",description );

        db.update(tableName, values,"word_id=?",new String[] {String.valueOf(word_id)});
        db.close();

    }

}
