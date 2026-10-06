package com.mycompany.systemsoftproject.controllers;

// imports classes used for file processing and database
import com.mycompany.systemsoftproject.database.LocalDatabase;
import com.mycompany.systemsoftproject.database.RemoteDatabase; 
import com.mycompany.systemsoftproject.services.EncryptionUtils;
import com.mycompany.systemsoftproject.services.Logs;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateNewFiles {
    
    private static final String STORAGE_PATH = "system_data/files/"; // directory where files are temporay stored before there transfered

    public String createFile(String username, String fileName, String initialContent) {
        // creates a delay between 30 and 60 randomly
        int delay = 30 + (int)(Math.random() * 61); 
        
        if (!fileName.endsWith(".txt")) { // when the user enters text name if it doesnt have .txt they have to enter a valid format
            System.out.println("Invalid format");
            return null;
        }
        // checks if a local directory exists if not creates one
        File dir = new File(STORAGE_PATH);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(STORAGE_PATH + fileName); // creates a file pointing to the storage path
        
        try {
            if (file.createNewFile()) {
                System.out.println("Simulating latency... Waiting " + delay + "s");
                Thread.sleep(2000); // 2 second delay just as a test

                // Encryption
                String encryptedContent = EncryptionUtils.encrypt(initialContent);

                // File Chunking
                String part1 = encryptedContent.substring(0, encryptedContent.length() / 2);
                String part2 = encryptedContent.substring(encryptedContent.length() / 2); // splits the files for chunchking

                try (FileWriter writer = new FileWriter(file)) { // writes chuncks to physcial file on the disk
                    writer.write(part1);
                    writer.write(part2);
                }

                // Metadata Updates
                LocalDatabase.saveFileMetadata(fileName, username, file.length());
                
                // syncs the file to the central database
                // records which containers hold primary backup chunks
                boolean remoteSaved = RemoteDatabase.saveFileMetadata(
                    fileName, 
                    username, 
                    "Container_1", 
                    "Container_2", 
                    file.length()
                );

                if (remoteSaved) { // records whether the sync is successful or not
                    Logs.log("SUCCESS File created and synced to Docker " + fileName);
                } else {
                    Logs.log("File created locally but Remote Sync failed.");
                }
                
                return file.getName();
            }
        } catch (IOException | InterruptedException e) {
            Logs.log("File system or connection failure.");
            e.printStackTrace();
        }
        return null;
    }
}