package com.quizgame;
import java.sql.Connection;
import java.util.HashMap;

import com.quizgame.DAO.CategoriesDAO;
import com.quizgame.DAO.PlayersDAO;

public class Question {
    int question_id;
    String option_a;
    String option_b;
    String option_c;
    String option_d;
    String question_text;  
    String correct_option  ;
    int difficulty_level;
    Category category;
    Player author;
    String status ;
    public void setAuthor(int authorID) {
        this.author = PlayersDAO.get(authorID);
    }public void setCategory( int categoryID) {
        this.category = CategoriesDAO.get(categoryID);
    }public void setCorrect_option(String correct_option) {
        this.correct_option = correct_option;
    }public void setDifficulty_level(int difficulty_level) {
        this.difficulty_level = difficulty_level;
    }public void setOption_a(String option_a) {
        this.option_a = option_a;
    }public void setOption_b(String option_b) {
        this.option_b = option_b;
    }public void setOption_c(String option_c) {
        this.option_c = option_c;
    }public void setOption_d(String option_d) {
        this.option_d = option_d;
    }public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }public void setStatus(String status) {
        this.status = status;
    }public Player getAuthor() {
        return author;
    }public Category getCategory() {
        return category;
    }public String getCorrect_option() {
        return correct_option;
    }public int getDifficulty_level() {
        return difficulty_level;
    }public int getQuestion_id() {
        return question_id;
    }public String getOption_a() {
        return option_a;
    }public String getOption_b() {
        return option_b;
    }public String getOption_c() {
        return option_c;
    }public String getOption_d() {
        return option_d;
    }public String getQuestion_text() {
        return question_text;
    }public String getStatus() {
        return status;
    }
   

}
