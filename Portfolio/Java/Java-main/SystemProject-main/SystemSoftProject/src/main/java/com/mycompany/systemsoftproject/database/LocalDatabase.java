package com.mycompany.systemsoftproject.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ensures fast access to session data and tempory files

public class LocalDatabase {

    private static final String DB_URL = "jdbc:sqlite:localdb.db";

    // FIX: This is the method your code was looking for!
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        // Use the new getConnection() method to keep things clean
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessions (" +
                    "username TEXT PRIMARY KEY," +
                    "loggedin INTEGER)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS files (" +
                    "filename TEXT PRIMARY KEY," +
                    "owner TEXT," +
                    "size INTEGER)");

        } catch (SQLException e) {
            System.out.println("Error initializing LocalDB. Note: SQLite driver might be missing.");
            e.printStackTrace();
        }
    }

    public static void saveSession(String username, boolean loggedIn) {
        String sql = "INSERT INTO sessions(username, loggedin) VALUES(?, ?) " +
                     "ON CONFLICT(username) DO UPDATE SET loggedin=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setInt(2, loggedIn ? 1 : 0);
            pstmt.setInt(3, loggedIn ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getActiveUser() {
        String sql = "SELECT username FROM sessions WHERE loggedin = 1 LIMIT 1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) return rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveFileMetadata(String filename, String owner, long size) {
        String sql = "INSERT INTO files(filename, owner, size) VALUES(?, ?, ?) " +
                     "ON CONFLICT(filename) DO UPDATE SET owner=?, size=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, filename);
            pstmt.setString(2, owner);
            pstmt.setLong(3, size);
            pstmt.setString(4, owner);
            pstmt.setLong(5, size);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllFiles() {
        List<String> files = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT filename FROM files")) {

            while (rs.next()) {
                files.add(rs.getString("filename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static String getOwner(String fileName) {
        String sql = "SELECT owner FROM files WHERE filename = ?";
        try (Connection conn = getConnection(); // This line is now fixed!
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fileName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getFileSize(String fileName) {
        String sql = "SELECT size FROM files WHERE filename = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fileName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong("size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getLastSessionUser() {
        return getActiveUser();
    }
}