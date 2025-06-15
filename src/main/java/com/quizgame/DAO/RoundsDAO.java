package com.quizgame.DAO;
import java.sql.*;
import java.util.*;
import com.quizgame.ConnectionProvider;
import com.quizgame.Round;

public class RoundsDAO {
    public static Round insert(int matchId, int category_id , int round_number , int q1 , int q2 , int q3) {
        String sql = "INSERT INTO Rounds (match_id, category_id , round_number , q1_id , q2_id , q3_id) VALUES (?, ? , ? , ? , ? , ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  // اصلاح این خط
    
            stmt.setInt(1, matchId);
            stmt.setInt(2, category_id);
            stmt.setInt(3, round_number);
            stmt.setInt(4, q1);
            stmt.setInt(5, q2);
            stmt.setInt(6, q3);
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Round round = new Round();
                    round.setID(generatedKeys.getInt(1));
                    round.setMatchID(matchId);
                    round.setCategory(category_id);
                    round.setQ1(q1);
                    round.setQ2(q2);
                    round.setQ3(q3);
                    round.setRound_number(round_number);
                    return round;
                } else {
                    System.out.println("No ID generated.");
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void delete(int roundId) throws SQLException {
        String sql = "DELETE FROM Rounds WHERE round_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roundId);
            stmt.executeUpdate();
        }
    }

    public static void updatePlayerAnswer(int playerX , int roundId,String Q1 , String Q2 , String Q3) {
        String sql;
        if(playerX  == 1){
             sql = "UPDATE Rounds SET player1_q1 = ? , player1_q2 = ? , player1_q3 = ?  WHERE round_id = ?";}
        else{
             sql = "UPDATE Rounds SET player2_q1 = ? , player2_q2 = ? , player2_q3 = ?  WHERE round_id = ?";
        }
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Q1);
            stmt.setString(2, Q2);
            stmt.setString(3, Q3);
            stmt.setInt(4, roundId);
            stmt.executeUpdate();
        } catch (SQLException e) {
                    e.printStackTrace();
                }
    }

    public static Map<String, Object> get(int roundId) throws SQLException {
        String sql = "SELECT * FROM Rounds WHERE round_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roundId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> round = new HashMap<>();
                    round.put("round_id", rs.getInt("round_id"));
                    round.put("match_id", rs.getInt("match_id"));
                    round.put("question_id", rs.getInt("question_id"));
                    round.put("is_correct", rs.getBoolean("is_correct"));
                    round.put("time_taken", rs.getInt("time_taken"));
                    return round;
                }
                return null;
            }
        }
    } 

    public static  ArrayList<Round> getAllByMatchID(int matchID) {
        final String sql = "SELECT * FROM Rounds WHERE match_id = ?";
        ArrayList<Round> rounds = new ArrayList<>();
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, matchID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rounds.add(mapRowToRound(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rounds;
    }

    private static Round mapRowToRound(ResultSet rs) {
        Round round = new Round();
        try {
            round.setMatchID(rs.getInt("match_id"));
            round.setPlayerQ1(1 , rs.getString("player1_Q1"));
            round.setPlayerQ2(1 ,rs.getString("player1_Q2"));
            round.setPlayerQ3(1 ,rs.getString("player1_Q3"));
            round.setPlayerQ1(2 , rs.getString("player2_Q1"));
            round.setPlayerQ2(2 ,rs.getString("player2_Q2"));
            round.setPlayerQ3(2 ,rs.getString("player2_Q3"));
            round.setCategory(rs.getInt("category_id"));
            round.setID(rs.getInt("round_id"));
            round.setQ1(rs.getInt("q1_id"));
            round.setQ2 (rs.getInt("q2_id"));
            round.setQ3(rs.getInt("q3_id"));
            round.setRound_number(rs.getInt("round_number"));
            round.setLevel(rs.getInt("level_number"));
            round.setPlayer1Done(rs.getBoolean("player1_done"));
            round.setPlayer2Done(rs.getBoolean("player2_done"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return round;
    }
}
