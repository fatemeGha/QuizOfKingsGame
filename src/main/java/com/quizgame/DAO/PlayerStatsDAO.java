package com.quizgame.DAO;
import java.util.*;
import java.sql.*;
import com.quizgame.ConnectionProvider;


class PlayerStatsDAO {
    public void insert(int playerId, int totalGames, int totalCorrect, int totalIncorrect, int totalTime) throws SQLException {
        String sql = "INSERT INTO PlayerStats (player_id, total_games_played, total_correct_answers, total_incorrect_answers, total_time_spent) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, totalGames);
            stmt.setInt(3, totalCorrect);
            stmt.setInt(4, totalIncorrect);
            stmt.setInt(5, totalTime);
            stmt.executeUpdate();
        }
    }

    public void update(int playerId, int totalGames, int totalCorrect, int totalIncorrect, int totalTime) throws SQLException {
        String sql = "UPDATE PlayerStats SET total_games_played = ?, total_correct_answers = ?, total_incorrect_answers = ?, total_time_spent = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalGames);
            stmt.setInt(2, totalCorrect);
            stmt.setInt(3, totalIncorrect);
            stmt.setInt(4, totalTime);
            stmt.setInt(5, playerId);
            stmt.executeUpdate();
        }
    }

    public Map<String, Object> get(int playerId) throws SQLException {
        String sql = "SELECT * FROM PlayerStats WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> stats = new HashMap<>();
                    stats.put("player_id", rs.getInt("player_id"));
                    stats.put("total_games_played", rs.getInt("total_games_played"));
                    stats.put("total_correct_answers", rs.getInt("total_correct_answers"));
                    stats.put("total_incorrect_answers", rs.getInt("total_incorrect_answers"));
                    stats.put("total_time_spent", rs.getInt("total_time_spent"));
                    return stats;
                }
                return null;
            }
        }
    }
}