package com.mycompany.systemsoftproject.models;

import java.util.Random;
// this aks as a container to hold all the files communicates with project 
public class FileStorageContainer {
    private String name;
    private int requestsHandled = 0;
    private long totalProcessingTime = 0;
    private boolean healthy = true;
    private boolean locked = false;

    public FileStorageContainer(String name) {
        this.name = name;
    }

    public synchronized boolean lockFile() {
        if (locked) return false;
        locked = true;
        return true;
    }

    public synchronized void unlockFile() {
        locked = false;
    }

    
    public void handleRequest(String localPath, String fileName, String action) {
        if (!healthy) {
            System.out.println("Container " + name + " is unreachable.");
            return;
        }
        
        if (!lockFile()) {
            System.out.println("File is locked " + fileName + " is busy on " + name);
            return;
        }

        long startTime = System.currentTimeMillis();

        try {
            System.out.println("Connecting : " + name + "............");
            
            // Artificial Network Latency 
            
            Thread.sleep(500 + new Random().nextInt(1000)); 

            if (action.equalsIgnoreCase("UPLOAD") || action.equalsIgnoreCase("CREATE")) {
                System.out.println("Success Block stored in " + name + "  storage.");
            } else if (action.equalsIgnoreCase("DELETE")) {
                System.out.println("Success Block removed from " + name);
            }

            // Update Performance Metrics
            requestsHandled++;
            totalProcessingTime += (System.currentTimeMillis() - startTime);

        } catch (InterruptedException e) {
            System.out.println("Process interrupted on " + name);
            healthy = false;
        } finally {
            unlockFile();
        }
    }

    public void simulateHealthCheck() {
        // Randomly fail to show the Load Balancer working
        this.healthy = (new Random().nextInt(10) != 0); 
        if (!healthy) System.out.println("The System aint healthy");
    }

    public void printStats() {
        System.out.println(name + " Stats -> Handled: " + requestsHandled + " | Total Time: " + totalProcessingTime + "ms");
    }

    public boolean isHealthy() { return healthy; }
    public String getName() { return name; }
}
