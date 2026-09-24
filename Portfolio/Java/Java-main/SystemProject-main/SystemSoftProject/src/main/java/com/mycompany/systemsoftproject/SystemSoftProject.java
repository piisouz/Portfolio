package com.mycompany.systemsoftproject;

// This file is like the glue that allows the loadbalances to communicate with the containers


// imports classes for databases,models,services and controllers
import com.mycompany.systemsoftproject.database.LocalDatabase;
import com.mycompany.systemsoftproject.database.RemoteDatabase;
import com.mycompany.systemsoftproject.models.*;
import com.mycompany.systemsoftproject.services.*;
import com.mycompany.systemsoftproject.controllers.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SystemSoftProject {

    // 1. GLOBAL STATE 
    public static Map<String, ACL> fileACLs = new HashMap<>(); // the map that stores acls for file perms
    public static Map<String, Object> fileLocks = new ConcurrentHashMap<>(); // the map that stores file locks
    public static List<FileStorageContainer> containers = new ArrayList<>(); // a list that manages multiple file storage containers
    public static LoadBalancer loadBalancer;// distributes requests across the containers

    // 
    // We make these static so your UI Controllers can access tham easily
    public static CreateNewFiles createNewFiles = new CreateNewFiles();
    public static DeleteFiles deleteFiles = new DeleteFiles();

    public static void main(String[] args) {
        initSystem(); // calls the initilize logic
        // Here is where i launch your JavaFX Stage
        System.out.println("--- SYSTEM READY ---");
    }

    public static void initSystem() {
        //  Connect Databases 
        LocalDatabase.initialize();
        RemoteDatabase.connect();

        // creates 4 staorage containers
        containers.clear();
        containers.add(new FileStorageContainer("Container1"));
        containers.add(new FileStorageContainer("Container2"));
        containers.add(new FileStorageContainer("Container3"));
        containers.add(new FileStorageContainer("Container4"));
        
        //  Start Load Balancer 
        loadBalancer = new LoadBalancer(containers);
        
        // Restore Session
        
        AuthService.restoreSession();
        RemoteDatabase.getAllUsers();
        
        Logs.log("Databases connected and 4 Containers online."); // logs the system start up
    }

   
    
    public static void handleCreateFile(String fileName, String content) {
        User user = AuthService.getCurrentUser(); // checks if the user is logged in
        if (user == null) {
            System.out.println("Please login first");
            return;
        }

        // Create the file this handles all the  encryption and chunking 
        String result = createNewFiles.createFile(user.getUsername(), fileName, content);
        
        if (result != null) {
            //Load Balancer handles the request assigns it to a healthy container
            loadBalancer.submitRequest(result, "CREATE");
            
            // Add a lock object for the file
            fileLocks.put(result, new Object());
        }
    }

    public static void handleDeleteFile(String fileName) {
        User user = AuthService.getCurrentUser();
        if (user == null) return; // checks the users r loggin in at the moment

        String result = deleteFiles.deleteFile(user.getUsername(), fileName); // creates a string that gets the users name and file and deltes it basically
        
        if (result != null) {
            loadBalancer.submitRequest(result, "DELETE"); // load balances does the delte command
            fileLocks.remove(result); // Remove lock once file is gone
        }
    }

    // 4. SYSTEM SYNC (
    public static void syncSystem() {
        RemoteDatabase.syncData(); // syncroses databa between the local and remote sql
        Logs.log("Data has been verified between Local and Remote.");
    }
    public static String getContainerStatus(int index) { // health check to monitor container status
        if (index >= containers.size()) return "UNKNOWN";
    
        FileStorageContainer c = containers.get(index);
        return c.isHealthy() ? "ONLINE " : "OFFLINE"; // returns status for the ui to display if a node is healthy or not
    }
}