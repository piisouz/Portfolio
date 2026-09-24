package com.mycompany.systemsoftproject.ui;
// gui controller dont really use it that much only for the terminal and some functions
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.mycompany.systemsoftproject.controllers.Terminal;
import com.mycompany.systemsoftproject.SystemSoftProject;

public class MainGUIController {

    @FXML private TextArea terminalOutput;
    @FXML private TextField commandInput;
    
    
    private Terminal terminal = new Terminal();

    @FXML
    private void handleCommand() {
        String input = commandInput.getText();
        String response = terminal.executeCommand(input);
        
        terminalOutput.appendText("> " + input + "\n" + response + "\n");
        commandInput.clear();
    }

    @FXML
    private void showHealth() {
        terminalOutput.appendText("Status:   " + SystemSoftProject.getContainerStatus(0) + "\n");
    }

    @FXML
    private void triggerSync() {
        SystemSoftProject.syncSystem();
        terminalOutput.appendText("System synchronized\n");
    }
}
