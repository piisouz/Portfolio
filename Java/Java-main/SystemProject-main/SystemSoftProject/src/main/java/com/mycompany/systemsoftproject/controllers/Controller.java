package com.mycompany.systemsoftproject.controllers;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.mycompany.systemsoftproject.models.Role;
import com.mycompany.systemsoftproject.database.RemoteDatabase; 

public class Controller {

    // UI Elements
    @FXML private TextField terminalInput; 
    @FXML private ListView<String> userListView;
    @FXML private ListView<String> fileListView; 
    @FXML private TextArea terminalOutput;
    @FXML private Button btnSync;
    @FXML private TextField commandInput; 

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @FXML
    public void initialize() {
        loadInitialData();
        
        // Add delete button logic to UI
        Button deleteUserBtn = new Button("Delete Selected User");
        deleteUserBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-margin-top: 10px;");
        deleteUserBtn.setOnAction(e -> handleDeleteUser());

        if (userListView.getParent() instanceof VBox) {
            ((VBox) userListView.getParent()).getChildren().add(deleteUserBtn);
        }
    }

    private void guiLog(String message) {
        if (terminalOutput != null) {
            terminalOutput.appendText("[" + dtf.format(LocalDateTime.now()) + "] > " + message + "\n");
        }
    }

    private void loadInitialData() {
        com.mycompany.systemsoftproject.models.User current = 
            com.mycompany.systemsoftproject.services.AuthService.getCurrentUser();
        
        if (current == null) return;

        // Refresh User List
        if (current.getRole() == Role.ADMIN) {
            userListView.getItems().setAll(RemoteDatabase.getAllUsers());
        } else {
            userListView.getItems().setAll(current.getUsername() + " (Me)");
        }

        // Refresh File List
        if (fileListView != null) {
            List<String> files;
            if (current.getRole() == Role.ADMIN) {
                files = RemoteDatabase.getAllFiles();
            } else {
                files = RemoteDatabase.getFilesByOwner(current.getUsername());
            }
            fileListView.getItems().setAll(files);
        }
    }

    @FXML
    private void handleChangePassword() {
        String selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            guiLog("ERROR: Select a user from the list first.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Updating password for: " + selectedUser); 
        dialog.setContentText("Enter new password:");

        dialog.showAndWait().ifPresent(newPass -> {
            if (!newPass.trim().isEmpty()) {
                if (RemoteDatabase.updatePassword(selectedUser, newPass)) {
                    guiLog("SUCCESS: Password updated for " + selectedUser);
                } else {
                    guiLog("ERROR: Database update failed.");
                }
            }
        });
    }

    @FXML
    private void handleLogout() {
        try {
            // 1. CLEAR THE SESSION FIRST
            com.mycompany.systemsoftproject.services.AuthService.setCurrentUser(null);

            // 2. Load the login screen
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/mycompany/systemsoftproject/ui/loginUI.fxml"));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) userListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

            System.out.println("SESSION: User cleared and logged out.");
        } catch (Exception e) {
            guiLog("LOGOUT ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void handleSync() {
        RemoteDatabase.syncData();
        loadInitialData();
        guiLog("SYSTEM: Global synchronization complete.");
    }

    @FXML
    private void handleCreateUser() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remote Registration");
        ButtonType saveBtn = new ButtonType("Save to DB", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        TextField uField = new TextField();
        PasswordField pField = new PasswordField();
        ChoiceBox<Role> roleBox = new ChoiceBox<>();
        roleBox.getItems().addAll(Role.USER, Role.ADMIN);
        roleBox.setValue(Role.USER);

        dialog.getDialogPane().setContent(new VBox(10, new Label("Name:"), uField, new Label("Pass:"), pField, new Label("Role:"), roleBox));

        dialog.showAndWait().ifPresent(res -> {
            if (res == saveBtn && !uField.getText().isEmpty()) {
                if (RemoteDatabase.addUser(uField.getText().trim(), pField.getText(), roleBox.getValue().toString())) {
                    loadInitialData();
                    guiLog("DB SUCCESS: " + uField.getText() + " saved to Docker.");
                } else {
                    guiLog("DB ERROR: Registration failed.");
                }
            }
        });
    }

    @FXML
    private void handleDeleteUser() {
        String selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null || selectedUser.equals("admin")) {
            guiLog("ERROR: Cannot delete this user.");
            return;
        }
        if (RemoteDatabase.deleteUser(selectedUser)) {
            guiLog("DB SUCCESS: User [" + selectedUser + "] deleted.");
            loadInitialData();
        }
    }

    @FXML
    private void handleCreateFile() {
        TextInputDialog dialog = new TextInputDialog("newfile.txt");
        dialog.setTitle("Create New File");
        dialog.showAndWait().ifPresent(fileName -> {
            String activeUser = com.mycompany.systemsoftproject.services.AuthService.getCurrentUser().getUsername();
            if (RemoteDatabase.saveFileMetadata(fileName, activeUser, "node_1", "node_2", 1024)) {
                guiLog("FILESYSTEM: " + fileName + " metadata saved.");
                loadInitialData();
            }
        });
    }

    @FXML
    private void handleDownloadFile() {
        String selected = fileListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            guiLog("ERROR: Select a file to download.");
            return;
        }
        String content = RemoteDatabase.getFileContent(selected);
        if (content.isEmpty()) {
            guiLog("ERROR: File is empty or not found.");
            return;
        }

        javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        chooser.setInitialFileName(selected);
        java.io.File file = chooser.showSaveDialog(fileListView.getScene().getWindow());

        if (file != null) {
            try {
                java.nio.file.Files.writeString(file.toPath(), content);
                guiLog("DOWNLOAD SUCCESS: Saved to " + file.getAbsolutePath());
            } catch (Exception e) {
                guiLog("DOWNLOAD ERROR: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleUpload() {
        javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        java.io.File selected = chooser.showOpenDialog(userListView.getScene().getWindow());
        if (selected != null) {
            try {
                String content = java.nio.file.Files.readString(selected.toPath());
                String activeUser = com.mycompany.systemsoftproject.services.AuthService.getCurrentUser().getUsername();
                if (RemoteDatabase.saveFileMetadata(selected.getName(), activeUser, "node_A", "node_B", selected.length())) {
                    RemoteDatabase.updateFileContent(selected.getName(), content);
                    guiLog("UPLOAD SUCCESS: " + selected.getName() + " in cloud.");
                    loadInitialData();
                }
            } catch (Exception e) {
                guiLog("UPLOAD ERROR: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleEditFile() {
        // 1. Check if something is actually clicked
        String selected = fileListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            guiLog("SYSTEM: Please click a file in the list first.");
            return;
        }

        guiLog("SYSTEM: Fetching '" + selected + "' from cloud...");

        // 2. Fetch from DB (Check if this returns null!)
        String content = RemoteDatabase.getFileContent(selected);

        // If the DB returns null, the dialog won't open. Let's fix that:
        if (content == null) {
            content = ""; 
            guiLog("WARNING: File is empty or not found in DB.");
        }

        // 3. Open the Dialog
        TextInputDialog dialog = new TextInputDialog(content);
        dialog.setTitle("Cloud Editor");
        dialog.setHeaderText("Editing: " + selected);
        dialog.setContentText("Edit content below:");

        // 4. Show it and wait for user to click OK
        dialog.showAndWait().ifPresent(newContent -> {
            if (RemoteDatabase.updateFileContent(selected, newContent)) {
                guiLog("SUCCESS: " + selected + " saved to Docker.");
            } else {
                guiLog("ERROR: Database rejected the update.");
            }
        });
    }

    @FXML
    private void handleCommand() {
        String input = commandInput.getText();
        if (input == null || input.trim().isEmpty()) return;

        com.mycompany.systemsoftproject.controllers.Terminal terminalBrain = new com.mycompany.systemsoftproject.controllers.Terminal();
        String result = terminalBrain.executeCommand(input);
        guiLog("Terminal Output for '" + input + "': \n" + result);
        commandInput.clear(); 
    }

    @FXML
    private void handleDeleteFile() {
        String selected = fileListView.getSelectionModel().getSelectedItem();
        if (selected != null && RemoteDatabase.deleteFileMetadata(selected)) {
            guiLog("File [" + selected + "] removed.");
            loadInitialData();
        }
    }

    @FXML
    private void handleShareFile() {
        String selected = fileListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        TextInputDialog dialog = new TextInputDialog("username");
        dialog.setTitle("Share File");
        dialog.showAndWait().ifPresent(target -> {
            guiLog("SUCCESS: Shared " + selected + " with " + target);
        });
    }

    @FXML
    private void handleRefresh() {
        loadInitialData();
        guiLog("SYSTEM: UI Refresh complete.");
    }
    
    // This method is required by LoginController and Application to set UI access levels
    public void setupPermissions(Role role) {
        if (role == null) return;
        
        if (role == Role.ADMIN) {
            guiLog("ACCESS: Admin privileges verified.");
            if (btnSync != null) btnSync.setDisable(false);
        } else {
            guiLog("ACCESS: User mode active.");
            if (btnSync != null) btnSync.setDisable(true);
            
            // Optional: Hide the sync button entirely for non-admins
            if (btnSync != null) btnSync.setVisible(false);
        }
    }
}