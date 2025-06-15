package com.quizgame.DAO;

import java.sql.*;
import java.util.*;
import com.quizgame.ConnectionProvider;
import com.quizgame.Match;

public class MatchesDAO { 
    public static Match insert(int playerId, String status) {
        String sql = "INSERT INTO Matches (player1_id, status) VALUES (?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setInt(1, playerId);
            stmt.setString(2, status);
            stmt.executeUpdate();
    
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                Match match = new Match();
                if (generatedKeys.next()) {
                    match.setMatchID(generatedKeys.getInt(1));
                    match.setPlayer1(playerId);
                    match.setStatus(status);
                    return match; 
                } else {
                    throw new SQLException("Creating match failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }

    public static void delete(int matchId) {
        String sql = "DELETE FROM Matches WHERE match_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateToActive(int matchId, int playerId) {
        String sql = "UPDATE Matches SET player2_id = ? , status = ? WHERE match_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, "active");
            stmt.setInt(3, matchId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePlayer1Score(int matchId, int playerScore) {
        String sql = "UPDATE Matches SET player1_score = ?  WHERE match_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerScore);
            stmt.setInt(2, matchId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePlayer2Score(int matchId, int playerScore) {
        String sql = "UPDATE Matches SET player2_score = ?  WHERE match_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerScore);
            stmt.setInt(2, matchId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Match get(int matchId) {
        String sql = "SELECT * FROM Matches WHERE match_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Match match = new Match();
                    match.setMatchID(rs.getInt("match_id"));
                    match.setPlayer1(rs.getInt("player1_id"));
                    match.setPlayer2(rs.getInt("player2_id"));
                    match.setEndTime(rs.getTimestamp("end_time"));
                    match.setStartTime(rs.getTimestamp("start_time"));
                    match.setStatus(rs.getString("status"));
                    match.setWinner(rs.getInt("winner_id"));
                    match.setPlayer1Score(rs.getInt("player1_score"));
                    match.setPlayer2Score(rs.getInt("player2_score"));
                    return match;
                }
                return null;
            } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
        } catch (SQLException e1) {
                    e1.printStackTrace();
                    return null;
                }
    }

    public static ArrayList<Match> getAllByPlayerID(int playerID) {
        final String sql = "SELECT * FROM matches where player1_id = ? union SELECT * FROM matches where player2_id = ?";
        ArrayList<Match> matches = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, playerID);
                    stmt.setInt(2, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    matches.add(mapRowToMatch(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }



    public static ArrayList<Match> getAll() {
        final String sql = "SELECT * FROM matches";
        ArrayList<Match> matches = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    matches.add(mapRowToMatch(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private static Match mapRowToMatch(ResultSet rs) {
        Match match = new Match();
        try {
            match.setMatchID(rs.getInt("match_id"));
            match.setStatus(rs.getString("status"));
            match.setPlayer1(rs.getInt("player1_id"));
            match.setPlayer2(rs.getInt("player2_id"));
            match.setStartTime(rs.getTimestamp("start_time"));
            match.setEndTime(rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time") : null);
            match.setWinner(rs.getInt("winner_id"));
            match.setPlayer1Score(rs.getInt("player1_score"));
            match.setPlayer2Score(rs.getInt("player2_score"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return match;
    }
}
