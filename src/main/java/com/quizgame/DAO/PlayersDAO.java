package com.quizgame.DAO;

import java.sql.*;
import java.sql.Date;
import com.quizgame.ConnectionProvider;
import com.quizgame.Player;

public class PlayersDAO {
    public static void insert(String username, String email, String passwordHash) {
        String sql = "INSERT INTO player (username, email, passwordstr) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete( int playerId) {
        String sql = "DELETE FROM player WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserName(int playerId, String username) {
        String sql = "UPDATE player SET username = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(int playerId, String pass) {
        String sql = "UPDATE player SET passwordstr = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pass);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmail(int playerId, String email) {
        String sql = "UPDATE player SET email = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatephoneNumber(int playerId, String phone_number) {
        String sql = "UPDATE player SET phone_number = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone_number);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBirthday(int playerId, Date birthday) {
        String sql = "UPDATE player SET birthday = ? WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, birthday);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Player get( int playerId) {
        String sql = "SELECT * FROM player WHERE player_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player();
                    player.setID(rs.getInt("player_id"));
                    player.setUserName(rs.getString("username"));
                    player.setEmail(rs.getString("email"));
                    player.setRegistrationDate(rs.getDate("registration_date"));
                    player.setPassword(rs.getString("passwordstr"));
                    player.setAvatarURL(rs.getString("avatar_url"));
                    player.setBirthday(rs.getDate("birthday"));
                    player.setPhoneNumber(rs.getString("phone_number"));
                    player.setLevel(rs.getInt("player_level"));
                    player.setScore(rs.getInt("score"));

                    return player;
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

    public static Player getByUserName(String userName)  {
        String sql = "SELECT * FROM player WHERE username = ?";
        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player();
                    player.setID(rs.getInt("player_id"));
                    player.setUserName(rs.getString("username"));
                    player.setEmail(rs.getString("email"));
                    player.setRegistrationDate(rs.getDate("registration_date"));
                    player.setPassword(rs.getString("passwordstr"));
                    player.setAvatarURL(rs.getString("avatar_url"));
                    player.setBirthday(rs.getDate("birthday"));
                    player.setPhoneNumber(rs.getString("phone_number"));
                    player.setLevel(rs.getInt("player_level"));
                    player.setScore(rs.getInt("score"));
                    return player;
                }
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}