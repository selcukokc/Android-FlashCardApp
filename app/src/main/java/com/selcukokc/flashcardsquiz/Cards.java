package com.selcukokc.flashcardsquiz;

import java.io.Serializable;

public class Cards implements Serializable {
    private int word_id;
    private String term,description;

    public Cards(int word_id, String term, String description) {
        this.word_id = word_id;
        this.term = term;
        this.description = description;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
