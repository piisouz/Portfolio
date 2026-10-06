package com.mycompany.systemsoftproject.services;

import java.util.HashMap;
import java.util.Map;

public class Performances {

    private static int totalRequests = 0;
    private static int failedRequests = 0;
    private static long totalResponseTime = 0;

    private static Map<String, Integer> containerRequests = new HashMap<>();

    // Starts the timer
    public static long startTimer() {
        return System.currentTimeMillis();
    }

    //Record metrics
    public static synchronized void endTimer(String containerId, long startTime, boolean success) {
        long duration = System.currentTimeMillis() - startTime;

        totalRequests++;
        totalResponseTime += duration;

        // Track which container did the work
        containerRequests.put(
                containerId,
                containerRequests.getOrDefault(containerId, 0) + 1
        );

        if (!success) {
            failedRequests++;
        }
    }

    public static void printMetrics() {
        System.out.println("-------- System Performance Report -----");
        System.out.println("Total Requests Processed: " + totalRequests);
        System.out.println("Operations completed:   " + (totalRequests - failedRequests));
        System.out.println("Failed Operations:  " + failedRequests);

        if (totalRequests > 0) {
            // Using a simple average calculation
            long average = totalResponseTime / totalRequests;
            System.out.println("Response Time:    " + average + " ms");
        }

        System.out.println("\nDistribution per Container:");
        for (Map.Entry<String, Integer> entry : containerRequests.entrySet()) {
            System.out.println(" -> " + entry.getKey() + ": " + entry.getValue() + " requests");
        }
        System.out.println("-------------------\n");
    }
}
