package com.mycompany.systemsoftproject.services;

import com.mycompany.systemsoftproject.models.FileStorageContainer;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class LoadBalancer {
    private List<FileStorageContainer> containers;
    private int currentIndex = 0;
    
    // Priority Queue Shorter filenames get processed first
    private PriorityQueue<Request> requestQueue = new PriorityQueue<>(Comparator.comparingInt(r -> r.estimatedTime));
    
    // allows the system to scale threads based on traffic
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public LoadBalancer(List<FileStorageContainer> containers) {
        this.containers = containers;
    }

    // request class
    private static class Request {
        String fileName;
        String action;
        int estimatedTime;

        Request(String fileName, String action) {
            this.fileName = fileName;
            this.action = action;
            this.estimatedTime = fileName.length(); 
        }
    }

    // Entry point for requests
    public synchronized void submitRequest(String fileName, String action) {
        requestQueue.add(new Request(fileName, action));
        processNext();
    }

    // Thread Management
    private synchronized void processNext() {
        if (!requestQueue.isEmpty()) {
            Request req = requestQueue.poll();
            executor.submit(() -> handleRequest(req));
        }
    }

    // The actual distribution logic with Health Checks
    private void handleRequest(Request req) {
        // Refresh health status before picking
        for (FileStorageContainer c : containers) {
            c.simulateHealthCheck();
        }

        FileStorageContainer container = null;
        int attempts = 0;
        long startTime = Performances.startTimer(); 

        while (attempts < containers.size()) {
            synchronized (this) {
                container = containers.get(currentIndex);
                currentIndex = (currentIndex + 1) % containers.size();
            }

            if (container.isHealthy()) break;
            System.out.println("Skipping down container: " + container.getName());
            attempts++;
        }

        if (container != null && container.isHealthy()) {
            container.handleRequest(req.fileName, req.fileName, req.action);
            Performances.endTimer(container.getName(), startTime, true);
        } else {
            System.out.println("Error all nodes are offline for " + req.fileName);
        }
    }

    // chunking distribution

    public FileStorageContainer findBestNode() {
        // Return the container with the least current load or just a healthy one
        return containers.get(0); 
    }

    public FileStorageContainer findSecondBestNode() {
        return containers.get(1);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
