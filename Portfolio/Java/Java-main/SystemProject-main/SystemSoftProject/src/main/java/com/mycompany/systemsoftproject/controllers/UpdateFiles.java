package com.mycompany.systemsoftproject.models;

import com.mycompany.systemsoftproject.SystemSoftProject;
import com.mycompany.systemsoftproject.services.EncryptionUtils;
import com.mycompany.systemsoftproject.services.Logs;


public class UpdateFiles {

    // passes in 'userInput' directly from the GUI 
    public void handleUpdate(String username, String fileName, String userInput) {
        
        // Permission Check
        ACL acl = SystemSoftProject.fileACLs.get(fileName); 
        if (acl == null || !acl.hasPermission(username, Permission.WRITE)) {
            System.out.println("You do not have permission to write to " + fileName);
            return; 
        }

        // Encryption 
        String encryptedData = EncryptionUtils.encrypt(userInput); 

        // Locking 
        
        Object lock = SystemSoftProject.fileLocks.getOrDefault(fileName, new Object());
        
        synchronized (lock) {
            System.out.println("Lock made for " + fileName);
            
            // Logging the action done by the user
            Logs.log("File updated: " + fileName + " by " + username);

            // Sends the task to the simulated containers
            SystemSoftProject.loadBalancer.submitRequest(fileName, "UPDATE");
            
            System.out.println("Update successful for " + fileName);
        }
    }
}