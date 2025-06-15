package com.quizgame.DAO;
import java.sql.*;
import java.util.*;
import com.quizgame.ConnectionProvider;
import com.quizgame.Question;
import com.quizgame.Round;

public class QuestionsDAO {
    public static void insert(String questionText, String a, String b, String c, String d,
                       String correctOption, int difficulty, int categoryId, int authorId, String status) {
        String sql = "INSERT INTO Questions (question_text, option_a, option_b, option_c, option_d, correct_option, difficulty_level, category_id, author_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, questionText);
            stmt.setString(2, a);
            stmt.setString(3, b);
            stmt.setString(4, c);
            stmt.setString(5, d);
            stmt.setString(6, correctOption);
            stmt.setInt(7, difficulty);
            stmt.setInt(8, categoryId);
            stmt.setInt(9, authorId);
            stmt.setString(10, status);
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void delete(int questionId) throws SQLException {
        String sql = "DELETE FROM Questions WHERE question_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            stmt.executeUpdate();
        }
    }

    public static void update(int questionId, String text, String a, String b, String c, String d,
                       String correctOption, int difficulty, int categoryId, int authorId, String status) throws SQLException {
        String sql = "UPDATE Questions SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_option = ?, difficulty_level = ?, category_id = ?, author_id = ?, status = ? WHERE question_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, text);
            stmt.setString(2, a);
            stmt.setString(3, b);
            stmt.setString(4, c);
            stmt.setString(5, d);
            stmt.setString(6, correctOption);
            stmt.setInt(7, difficulty);
            stmt.setInt(8, categoryId);
            stmt.setInt(9, authorId);
            stmt.setString(10, status);
            stmt.setInt(11, questionId);
            stmt.executeUpdate();
        }
    }

    public static Question get(int questionId)  {
        String sql = "SELECT * FROM Questions WHERE question_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Question q = new Question();
                    q.setAuthor(rs.getInt("author_id"));
                    q.setQuestion_id(rs.getInt("question_id"));
                    q.setCategory( rs.getInt("category_id"));
                    q.setCorrect_option(rs.getString("correct_option"));
                    q.setDifficulty_level( rs.getInt("difficulty_level"));
                    q.setQuestion_text( rs.getString("question_text"));
                    q.setStatus( rs.getString("status"));
                    q.setOption_a(rs.getString("option_a"));
                    q.setOption_b(rs.getString("option_b"));
                    q.setOption_c(rs.getString("option_c"));
                    q.setOption_d(rs.getString("option_d"));
                    return q;
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
                }
    }

    public static  ArrayList<Question> getAllByCategoryID(int categoryID) {
        final String sql = "SELECT * FROM Questions WHERE category_id = ? AND status = 'approved'";
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, categoryID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapRowToQuestions(conn , rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
    public static  ArrayList<Question> getAllUnAcceptedByAuthorId(int author_id) {
        final String sql = "SELECT * FROM Questions WHERE author_id = ? AND status = 'rejected'";
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, author_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapRowToQuestions(conn , rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }public static  ArrayList<Question> getAllPendingByAuthorId(int author_id) {
        final String sql = "SELECT * FROM Questions WHERE author_id = ? AND status = 'pending'";
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, author_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapRowToQuestions(conn , rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
    public static  ArrayList<Question> getAllApprovedByAuthorId(int author_id) {
        final String sql = "SELECT * FROM Questions WHERE author_id = ? AND status = 'approved'";
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, author_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapRowToQuestions(conn , rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private static Question mapRowToQuestions(Connection conn , ResultSet rs) {
        Question q = new Question();
        try {
            q.setAuthor(rs.getInt("author_id"));
            q.setQuestion_id(rs.getInt("question_id"));
            q.setCategory( rs.getInt("category_id"));
            q.setCorrect_option(rs.getString("correct_option"));
            q.setDifficulty_level( rs.getInt("difficulty_level"));
            q.setQuestion_text( rs.getString("question_text"));
            q.setStatus( rs.getString("status"));
            q.setOption_a(rs.getString("option_a"));
            q.setOption_b(rs.getString("option_b"));
            q.setOption_c(rs.getString("option_c"));
            q.setOption_d(rs.getString("option_d"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return q;
    }
}