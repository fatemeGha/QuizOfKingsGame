package com.quizgame.DAO;
import java.util.*;
import java.sql.*;
import com.quizgame.ConnectionProvider;
class LeaderboardsDAO {
    public void insert(int playerId, int score, Timestamp timestamp) throws SQLException {
        String sql = "INSERT INTO Leaderboards (player_id, score, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, score);
            stmt.setTimestamp(3, timestamp);
            stmt.executeUpdate();
        }
    }

    public void update(int leaderboardId, int playerId, int score, Timestamp timestamp) throws SQLException {
        String sql = "UPDATE Leaderboards SET player_id = ?, score = ?, timestamp = ? WHERE leaderboard_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, score);
            stmt.setTimestamp(3, timestamp);
            stmt.setInt(4, leaderboardId);
            stmt.executeUpdate();
        }
    }

    public void delete(int leaderboardId) throws SQLException {
        String sql = "DELETE FROM Leaderboards WHERE leaderboard_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leaderboardId);
            stmt.executeUpdate();
        }
    }

    public Map<String, Object> get(int leaderboardId) throws SQLException {
        String sql = "SELECT * FROM Leaderboards WHERE leaderboard_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leaderboardId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> lb = new HashMap<>();
                    lb.put("leaderboard_id", rs.getInt("leaderboard_id"));
                    lb.put("player_id", rs.getInt("player_id"));
                    lb.put("score", rs.getInt("score"));
                    lb.put("timestamp", rs.getTimestamp("timestamp"));
                    return lb;
                }
                return null;
            }
        }
    }
}
