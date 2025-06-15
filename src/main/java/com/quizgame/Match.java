package com.quizgame;

import java.sql.Timestamp;

import com.quizgame.DAO.MatchesDAO;
import com.quizgame.DAO.PlayersDAO;

public class Match {
    int MatchID;
    Player player1;
    Player player2;
    String status; //////
    Player winner;
    Timestamp StartTime;
    Timestamp endTime;
    int player1Score;
    int player2Score;

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public void updatePlayer1Score(int player1Score) {
        this.player1Score = player1Score;
        MatchesDAO.updatePlayer1Score(this.MatchID, player1Score);
    }

    public void updatePlayer2Score(int player2Score) {
        this.player2Score = player2Score;
        MatchesDAO.updatePlayer2Score(this.MatchID, player2Score);
    }

    public void addPlayer1Score() {
        this.player1Score++;
    }

    public void addPlayer2Score() {
        this.player2Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setMatchID(int matchID) {
        MatchID = matchID;
    }

    public void setPlayer1(int player1ID) {
        Player player1 = PlayersDAO.get(player1ID);
        this.player1 = player1;
    }

    public void setPlayer2(int player2ID) {
        Player player2 = PlayersDAO.get(player2ID);
        this.player2 = player2;
    }

    public void setStartTime(Timestamp startTime) {
        StartTime = startTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWinner(int winnerID) {
        this.winner = PlayersDAO.get(winnerID);
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getMatchID() {
        return MatchID;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Timestamp getStartTime() {
        return StartTime;
    }

    public String getStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getAnotherPlayer(int playerID) {
        if (playerID == this.player1.getID()) {
            if (player2 != null) {
                return player2;
            }
            else return new Player();
        } else {
            return player1;

        }
    }

    public int getPlayerNumber(int playerID) {
        if (playerID == this.player1.getID()) {
            return 1;
        } else {
            return 2;

        }
    }

}
