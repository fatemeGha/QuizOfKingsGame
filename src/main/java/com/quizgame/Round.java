package com.quizgame;

import java.sql.Connection;

import com.quizgame.DAO.CategoriesDAO;
import com.quizgame.DAO.MatchesDAO;
import com.quizgame.DAO.PlayersDAO;
import com.quizgame.DAO.QuestionsDAO;

public class Round {
    int ID;
    Question q1;
    Question q2;
    Question q3;
    Player player1;
    Player player2;
    Player winner;
    Player loser;
    Match match;
    int level;
    Category category;
    int round_number;
    String player1Q1;
    String player1Q2;
    String player1Q3;
    String player2Q1;
    String player2Q2;
    String player2Q3;
    boolean player1Done;
    boolean player2Done;

    public void setPlayer1Done(boolean player1Done) {
        this.player1Done = player1Done;
    }

    public void setPlayer2Done(boolean player2Done) {
        this.player2Done = player2Done;
    }

    public boolean getPlayer1Done() {
        return player1Done;
    }

    public boolean getPlayer2Done() {
        return player2Done;
    }

    public void setCategory(int categoryID) {
        this.category = CategoriesDAO.get(categoryID);
    }

    public void setMatchID(int matchID) {
        this.match = MatchesDAO.get(matchID);
    }

    public void setID(int iD) {
        ID = iD;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRound_number(int round_number) {
        this.round_number = round_number;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public void setPlayer1(int player1ID) {
        this.player1 = PlayersDAO.get(player1ID);
    }

    public void setPlayer2(int player2ID) {
        this.player2 = PlayersDAO.get(player2ID);
    }

    public void setWinner(int winnerID) {
        this.winner = PlayersDAO.get(winnerID);
    }

    public void setQ1(int q1ID) {
        this.q1 = QuestionsDAO.get(q1ID);
    }

    public void setQ2(int q2ID) {
        this.q2 = QuestionsDAO.get(q2ID);
    }

    public void setQ3(int q3ID) {
        this.q3 = QuestionsDAO.get(q3ID);
    }

    public Category getCategory() {
        return category;
    }

    public int getID() {
        return ID;
    }

    public int getLevel() {
        return level;
    }

    public Player getLoser() {
        return loser;
    }

    public Match getMatch() {
        return match;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Question getQ1() {
        return q1;
    }

    public Question getQ2() {
        return q2;
    }

    public Question getQ3() {
        return q3;
    }

    public int getRound_number() {
        return round_number;
    }

    public Player getWinner() {
        return winner;
    }

    public void setPlayerQ1(int playernumber, String playerq1) {
        if (playernumber == 1) {
            player1Q1 = playerq1;
        } else if (playernumber == 2) {
            player2Q1 = playerq1;
        }
    }

    public void setPlayerQ2(int playernumber, String playerq2) {
        if (playernumber == 1) {
            player1Q2 = playerq2;
        } else if (playernumber == 2) {
            player2Q2 = playerq2;
        }
    }

    public void setPlayerQ3(int playernumber, String playerq3) {
        if (playernumber == 1) {
            player1Q3 = playerq3;
        } else if (playernumber == 2) {
            player2Q3 = playerq3;
        }
    }

    public String getPlayerQ1(int playernumber) {
        if (playernumber == 1) {
            return player1Q1;
        } else {
            return player2Q1;
        }
    }

    public String getPlayerQ2(int playernumber) {
        if (playernumber == 1) {
            return player1Q2;
        } else {
            return player2Q2;
        }
    }

    public String getPlayerQ3(int playernumber) {
        if (playernumber == 1) {
            return player1Q3;
        } else {
            return player2Q3;
        }
    }

}
