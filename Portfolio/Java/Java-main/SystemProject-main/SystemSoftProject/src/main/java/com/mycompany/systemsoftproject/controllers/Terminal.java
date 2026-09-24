package com.mycompany.systemsoftproject.controllers;

import com.mycompany.systemsoftproject.SystemSoftProject;
import com.mycompany.systemsoftproject.services.AuthService;
import com.mycompany.systemsoftproject.services.Performances;
import com.mycompany.systemsoftproject.database.LocalDatabase;
import java.util.List;

// this class basically proccess inputs and maps them into system functions
public class Terminal {
    
    private String currentDir = "/root";

    public String executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) return "";

        // Splits the inputs into words
        String[] parts = input.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        // Security check to see if the user is logged in as its required
        if (!command.equals("help") && AuthService.getCurrentUser() == null) {
            return "Error You aernt logged in ";
        }
        // this switch statement basically allows the user to enter any of these commands and it opperates them
        switch (command) {
            case "help":
                return "Commands: ls, cd, pwd, mkdir, tree, upload, rm, status, whoami, sync";

            case "pwd":
                return currentDir;

            case "whoami":
                return "User: " + AuthService.getCurrentUser().getUsername() + " (" + AuthService.getCurrentUser().getRole() + ")";

            case "cd":
                if (parts.length < 2) return currentDir;
                currentDir = parts[1].startsWith("/") ? parts[1] : currentDir + "/" + parts[1];
                return "Moved to " + currentDir;

            case "ls":
               List<String> files = com.mycompany.systemsoftproject.database.RemoteDatabase.getAllFiles();
                return files.isEmpty() ? "Directory is empty." : String.join("\n", files);

            case "mkdir":
                if (parts.length < 2) return "Usage: mkdir <name>";
                return "Directory '" + parts[1] + "' created successfully.";

            case "tree":
                List<String> allFiles = LocalDatabase.getAllFiles();
                StringBuilder sb = new StringBuilder(currentDir + "\n");
                for (String f : allFiles) sb.append("├── ").append(f).append("\n");
                return sb.toString();

            case "upload": // Emulates 'cp' or adding a file
                if (parts.length < 3) return "Usage: upload <filename> <content>";
                SystemSoftProject.handleCreateFile(parts[1], parts[2]);
                return "Processing upload of " + parts[1] + "...";

            case "status":
                Performances.printMetrics();
                return "Metrics printed to system log/console.";

            case "sync":
                SystemSoftProject.syncSystem();
                return "Database synchronization initiated.";

            default:
                return "Unknown command: " + command;
        }
    }
}