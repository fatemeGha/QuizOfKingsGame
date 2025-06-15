package com.quizgame.DAO;
import com.quizgame.Category;
import com.quizgame.ConnectionProvider;
import java.util.*;
import java.sql.*;

public class CategoriesDAO {
    public static void insert( String categoryName) throws SQLException {
        String sql = "INSERT INTO Categories (category_name) VALUES (?)";
            try (Connection conn = ConnectionProvider.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, categoryName);
            stmt.executeUpdate();
            }
        
    }

    public static void delete(int categoryId) throws SQLException {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        }
    }

    public static void update(int categoryId, String url) throws SQLException {
        String sql = "UPDATE Categories SET icon_image = ? WHERE category_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }

    public static Category get( int categoryId)  {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryID(rs.getInt("category_id"));
                    category.setCategoryName( rs.getString("category_name"));
                    category.setIconImage(rs.getString("icon_image"));
                    return category;
                }
                return null;
            }
        } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
    }
    
    public static Category getByName(String categoryName)  {
        String sql = "SELECT * FROM Categories WHERE category_name = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryID(rs.getInt("category_id"));
                    category.setCategoryName( rs.getString("category_name"));
                    return category;
                }
                return null;
            }
        } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
    }
    public static ArrayList<Category> getAll() {
        final String sql = "SELECT * FROM Categories";
        ArrayList<Category> categories = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapRowToCategory(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    private static Category mapRowToCategory(ResultSet rs) {
        Category category = new Category();
        try {
            category.setCategoryID(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

}
