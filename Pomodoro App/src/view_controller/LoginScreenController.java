package view_controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Database;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnSubmit;


    @FXML
    private void handleSubmit(ActionEvent event) throws IOException {
        String userName = tfUsername.getText();
        String password = tfPassword.getText();

        if(Database.isUserValid(userName, password)){
            Parent mainScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/MainScreen.fxml"));
            Scene timerScene = new Scene(mainScreen);
            //next line is getting stage information
            Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            calendarStage.setScene((timerScene));
            calendarStage.show();
        }
        else{
            System.out.println("Invalid username/password");
        }

    }
}
