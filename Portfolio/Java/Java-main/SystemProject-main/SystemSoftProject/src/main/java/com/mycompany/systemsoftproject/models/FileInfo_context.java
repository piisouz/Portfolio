package com.mycompany.systemsoftproject.models;

import com.mycompany.systemsoftproject.SystemSoftProject;
import com.mycompany.systemsoftproject.services.EncryptionUtils;
import com.mycompany.systemsoftproject.services.Logs;


public class FileInfo_context {

    public String fileInfo(String username, String fileName) {
        // 1. Validation
        if (!fileName.endsWith(".txt")) {
            System.out.println("Error Enter a valid .txt file");
            return null;
        }

        // Permission Check 
    
        ACL acl = SystemSoftProject.fileACLs.get(fileName);
        if (acl != null && !acl.hasPermission(username, Permission.READ)) {
            System.out.println("Access Denied you have no permission to read " + fileName);
            return null;
        }

        //  File Retrieval
        // Since the 'real' file is in a Docker container  
        // we log the request through the Load Balancer
        System.out.println("Fetching File Metadata ");
        System.out.println("File name: " + fileName);
        
        // Route the READ request through the Load Balancer
        SystemSoftProject.loadBalancer.submitRequest(fileName, "READ");

        try {
            // Decryption Logic
            // We simulate reading the encrypted content from the 'system'
            String simulatedEncryptedContent = "M7Y230NTU820Ilko9Eij73"; // string
            
            
            
            String decryptedContent = EncryptionUtils.decrypt(simulatedEncryptedContent);
            
            System.out.println("Contents: " + decryptedContent);
            
            Logs.log("User [" + username + "] accessed file: " + fileName);
            return fileName;

        } catch (Exception e) {
            System.out.println("Error Could not decrypt file content.");
            return null;
        }
    }
}
