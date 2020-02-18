package view_controller;

import ObjClasses.PomodoroTimer;
import ObjClasses.TimerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UpdateScreenController implements Initializable {


    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfPomoSpent;

    @FXML
    private TextField tfTask;

    @FXML
    private TextField tfGoal;

    @FXML
    private Button submitBtn;

    @FXML
    private Button backBtn;

    PomodoroTimer timerToBeModified = TimerList.getTimerList().get(MainScreenController.getTimerIndex());





    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {

        //go to home screen
        Parent HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/MainScreen.fxml"));
        Scene timerScene = new Scene(HomeScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();

    }

    @FXML
    public void handleSubmit(ActionEvent event) throws IOException{
        String updateDate = tfDate.getText();
        int pomoSpent = Integer.parseInt(tfPomoSpent.getText());
        String updateTask = tfTask.getText();
        String updateGoal = tfGoal.getText();


        Database.updatePomodoro(updateDate, pomoSpent, updateTask, updateGoal);


        //go to home screen
        Parent HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/MainScreen.fxml"));
        Scene timerScene = new Scene(HomeScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize values passed from previous screen
        tfDate.setText(timerToBeModified.getStartDate());
        tfDate.setDisable(true);
        tfPomoSpent.setText(String.valueOf(timerToBeModified.getPomodoroSpent()));
        tfTask.setText(timerToBeModified.getTaskInWork());
        tfGoal.setText(timerToBeModified.getUltimateGoal());




    }
}
