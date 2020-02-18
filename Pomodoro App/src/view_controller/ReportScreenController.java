package view_controller;

import ObjClasses.PomodoroTimer;
import ObjClasses.TimerList;
import ObjClasses.Timers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.Database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportScreenController implements Initializable {

    @FXML
    private TableView<PomodoroTimer> tvTimerDisplay;

    @FXML
    private TableColumn<PomodoroTimer, String> tableColDate;

    @FXML
    private TableColumn<PomodoroTimer, Integer> tvColPomoSpent;

    @FXML
    private TableColumn<PomodoroTimer, String> tvColTask;

    @FXML
    private TableColumn<PomodoroTimer, String> tvColGoal;
    @FXML
    private TextField tfReport;

    @FXML
    void handleGenerateReport(ActionEvent event) {
        int pomoSpent = Integer.parseInt(tfReport.getText());

        TimerList.getTimerList().clear();
        Database.updateReportQuery(pomoSpent);
        tvTimerDisplay.setItems(TimerList.getTimerList());

    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        //refresh home screen
        Parent mainScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/MainScreen.fxml"));
        Scene timerScene = new Scene(mainScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();
    }






    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try{
            TimerList.getTimerList().clear();

            tableColDate.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("startDate"));
            tvColPomoSpent.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, Integer>("pomodoroSpent"));
            tvColTask.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("taskInWork"));
            tvColGoal.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("ultimateGoal"));


        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
