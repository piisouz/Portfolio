package com.mycompany.systemsoftproject.controllers;

import com.mycompany.systemsoftproject.database.LocalDatabase;
import com.mycompany.systemsoftproject.services.AuthService;
import com.mycompany.systemsoftproject.services.Logs;
import java.io.File;

public class DeleteFiles {

    public String deleteFile(String username, String fileName) {
        // RACL Check
        // asks the database who owns the file
        String owner = LocalDatabase.getOwner(fileName);
        
        if (owner == null || !owner.equals(username)) { // if the current user doesnt equal the owner of the file then they cant delte
            System.out.println("You are not the owner.");
            return null;
        }

        File file = new File(fileName);
        
        // Concurrency Control 
        synchronized(file) {
            if (file.exists() && file.delete()) {
                Logs.log("File deleted " + fileName + " by " + username);
                System.out.println("File removed.");
                return fileName;
            }
        }
        
        System.out.println("Delete failed");
        return null;
    }
}