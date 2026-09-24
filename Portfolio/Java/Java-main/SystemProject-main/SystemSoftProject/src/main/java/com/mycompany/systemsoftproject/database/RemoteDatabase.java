package com.mycompany.systemsoftproject.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// this code handles all the communications with docker and my sql
public class RemoteDatabase {

    
   private static final String URL = "jdbc:mysql://152.71.142.248:3306/system_soft_db?connectTimeout=3000";
    private static final String USER = "root";
    private static final String PASS = "ntu-user"; 

    // attempts to get ac onnection with docker
    public static void connect() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("remote database connected to docker on the windows device");
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    //user management
    // fetches a list of all the usernames 

    public static List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username FROM users")) {
            while (rs.next()) users.add(rs.getString("username"));
        } catch (SQLException e) { e.printStackTrace(); }
        return users;
    }
// allows users accounts to be removed from the database
    public static boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Delete User Error: " + e.getMessage());
            return false;
        }
}

    // FILE MEtadata

    /**
    records which nodes are storing the file chuncks
     */
    public static boolean saveFileMetadata(String fileName, String owner, String container1, String container2, long fileSize) {
        String sql = "INSERT INTO file_metadata (filename, owner, node_a, node_b, size) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fileName);
            pstmt.setString(2, owner);
            pstmt.setString(3, container1);
            pstmt.setString(4, container2);
            pstmt.setLong(5, fileSize);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Metadata Sync Error " + e.getMessage());
            return false;
        }
    }

    /**
     * Version with 1 container string
     */
    public static boolean saveFileMetadata(String fileName, String owner, String container, long fileSize) {
        return saveFileMetadata(fileName, owner, container, "NONE", fileSize);
    }

    

    public static void syncData() {
        System.out.println("Syncing..");
        // implementation logic goes here
    }
    
    public static boolean addUser(String username, String password, String role) {
        // SQL query to insert the new user into your Docker database
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // Returns true if the user was successfully added

        } catch (SQLException e) {
            System.err.println("Database error" + e.getMessage());
            return false;
        }
    }
    // validates login details with the ones in the docker
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a match is found
        } catch (SQLException e) {
            System.err.println("Login Validation Error: " + e.getMessage());
            return false;
        }
    }
    // gathers a list of all usernames for the admin
    public static List<String> getAllFiles() {
        List<String> files = new ArrayList<>();
        String sql = "SELECT filename FROM file_metadata";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                files.add(rs.getString("filename"));
            }
        } catch (SQLException e) {
            System.err.println("Fetch Files Error: " + e.getMessage());
        }
        return files;
    }
    //allsows user to update password using sql statements
    public static boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update Password Error " + e.getMessage());
            return false;
        }
    }
    // uses sql to delte file names
    public static boolean deleteFileMetadata(String fileName) {
        String sql = "DELETE FROM file_metadata WHERE filename = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fileName);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Delete File Error " + e.getMessage());
            return false;
        }
    }
    //another sql statement that allows file contents to be updated
    public static boolean updateFileContent(String fileName, String newContent) {
        String sql = "UPDATE file_metadata SET content = ? WHERE filename = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newContent);
            pstmt.setString(2, fileName);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // allows the file to be shared with multiple users
    public static boolean shareFile(String fileName, String targetUser, String permType) {
        String sql = "UPDATE file_metadata SET shared_with = ?, permissions = ? WHERE filename = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, targetUser);
            pstmt.setString(2, permType); // "READW RITE" or "READ ONLY"
            pstmt.setString(3, fileName);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        // 1. Fetches a full User object based on username (Used for session restore)
    public static com.mycompany.systemsoftproject.models.User getUserByUsername(String username) {
        String sql = "SELECT username, role FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new com.mycompany.systemsoftproject.models.User(
                    rs.getString("username"), "", 
                    com.mycompany.systemsoftproject.models.Role.valueOf(rs.getString("role").toUpperCase())
                );
            }
        } catch (Exception e) { System.err.println("DB Error: " + e.getMessage()); }
        return null;
    }

    // 2. Fetches full user details (Used for login logic)
    public static com.mycompany.systemsoftproject.models.User getUserDetails(String username, String password) {
        String sql = "SELECT username, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new com.mycompany.systemsoftproject.models.User(
                    rs.getString("username"), "", 
                    com.mycompany.systemsoftproject.models.Role.valueOf(rs.getString("role").toUpperCase())
                );
            }
        } catch (Exception e) { System.err.println("Login detail error: " + e.getMessage()); }
        return null;
    }

    // 3. Fetches only files belonging to a specific user
    public static List<String> getFilesByOwner(String username) {
        List<String> files = new ArrayList<>();
        String sql = "SELECT filename FROM file_metadata WHERE owner = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { files.add(rs.getString("filename")); }
        } catch (SQLException e) { System.err.println("Fetch User Files Error: " + e.getMessage()); }
        return files;
    }
    public static String getFileContent(String fileName) {
        String sql = "SELECT content FROM file_metadata WHERE filename = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("content");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching content: " + e.getMessage());
        }
        return ""; // Return empty if not found
    }
  
    
}
