package com.mycompany.systemsoftproject.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logs {

    private static final String LOG_FILE = "system.log";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(
                    "[" + LocalDateTime.now() + "] " + message + "\n"
            );
        } catch (IOException e) {
            System.out.println("Logging failed");
        }
    }
}
