package com.mycompany.systemsoftproject;
// This file is how my application starts
// importing java tools for the UI
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Initialize System
        SystemSoftProject.initSystem(); 

        // Start with Login as default
        String fxmlFile = "/com/mycompany/systemsoftproject/ui/loginUI.fxml";
        boolean autoLoggedIn = false;

        // 2. Check Session
        if (com.mycompany.systemsoftproject.services.AuthService.getCurrentUser() != null) {
            System.out.println("Auto-login detected for: " + 
                com.mycompany.systemsoftproject.services.AuthService.getCurrentUser().getUsername());
            
            
            String dashboardPath = "/com/mycompany/systemsoftproject/ui/MainUI.fxml";
            
            if (getClass().getResource(dashboardPath) != null) {
                fxmlFile = dashboardPath;
                autoLoggedIn = true;
            } else {
                System.err.println("CRITICAL ERROR: Could not find " + dashboardPath + ". Falling back to Login screen.");
            }
        }

        // 3. Load the FXML
        java.net.URL location = getClass().getResource(fxmlFile);
        if (location == null) {
            throw new RuntimeException("Cannot find FXML file: " + fxmlFile);
        }

        FXMLLoader loader = new FXMLLoader(location);
        Parent root = loader.load();
        
        // 4. Permission Setup
        if (autoLoggedIn) {
            Object controller = loader.getController();
            if (controller instanceof com.mycompany.systemsoftproject.controllers.Controller) {
                ((com.mycompany.systemsoftproject.controllers.Controller)controller)
                    .setupPermissions(com.mycompany.systemsoftproject.services.AuthService.getCurrentUser().getRole());
            }
        }

        stage.setScene(new Scene(root));
        stage.setTitle("Cloud System - " + (autoLoggedIn ? "Dashboard" : "Login"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // starts the javaFx lifecycle and calls the start method
    }
}
