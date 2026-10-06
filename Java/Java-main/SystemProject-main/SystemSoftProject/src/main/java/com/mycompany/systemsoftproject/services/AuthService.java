package com.mycompany.systemsoftproject.services;

import com.mycompany.systemsoftproject.models.User;
import com.mycompany.systemsoftproject.models.Role;
import java.io.*; 
import java.util.Scanner;

public class AuthService {
    private static final String SESSION_FILE = "session.txt"; 
    private static User currentUser; 

    // MAVEN FIX: This is the method your Controller was looking for
    public static void setCurrentUser(User user) {
        currentUser = user;
        if (user == null) {
            // If we set user to null, we should also kill the session file
            new File(SESSION_FILE).delete();
        }
    }

    public static boolean login(String username, String password) {
        boolean isValid = com.mycompany.systemsoftproject.database.RemoteDatabase.validateUser(username, password);

        if (isValid) {
            currentUser = com.mycompany.systemsoftproject.database.RemoteDatabase.getUserByUsername(username);
            saveSessionToFile(username);
            System.out.println("Login Successful: " + username);
            return true;
        }
        return false;
    }

    public static void restoreSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextLine()) {
                    String savedUser = scanner.nextLine().trim();
                    User restoredUser = com.mycompany.systemsoftproject.database.RemoteDatabase.getUserByUsername(savedUser);
                    
                    if (restoredUser != null) {
                        currentUser = restoredUser;
                        System.out.println("Session restored for: " + currentUser.getUsername());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error restoring session.");
            }
        }
    }

    public static User getCurrentUser() { return currentUser; }
    
    public static void logout() { 
        setCurrentUser(null); // Use the logic above to clear everything
    }

    private static void saveSessionToFile(String username) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SESSION_FILE))) {
            writer.println(username);
        } catch (IOException e) {
            System.err.println("Could not save session file.");
        }
    }
}