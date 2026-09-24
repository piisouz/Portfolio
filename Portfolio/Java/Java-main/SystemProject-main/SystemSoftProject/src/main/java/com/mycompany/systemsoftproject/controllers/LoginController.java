package com.mycompany.systemsoftproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.mycompany.systemsoftproject.services.AuthService;
import com.mycompany.systemsoftproject.models.User;


// handels the initial user entry point
public class LoginController {
    // links the text files to my loginUI.fxml
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label errorLabel;

    
    // this activates when the user clicks the login button
    @FXML
    private void handleLogin() {
        if (AuthService.login(userField.getText(), passField.getText())) {
            try {
                // if the details are invalid it moves them to the main dashboard
                switchToMain();
            } catch (Exception e) {
                errorLabel.setText("Login Error FXML Load failed.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid Access Denied.");
        }
    }

    // transitions the scene and aplies a role base access control
    private void switchToMain() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/systemsoftproject/ui/MainUI.fxml")); // loads the main dashboard
        Parent root = loader.load();

        //we get the controller of the new screen to communicate with it
        Controller mainController = loader.getController();
        User user = AuthService.getCurrentUser();

        
        //if the controler and user exists it tells the controller what the users role is
        if (mainController != null && user != null) {
            mainController.setupPermissions(user.getRole());
        }
        // just allows window switching
        Stage stage = (Stage) userField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}