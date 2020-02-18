package view_controller;

import ObjClasses.TimerList;
import ObjClasses.Timers;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import ObjClasses.PomodoroTimer;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Database;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable{

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
    private Label prodTimerLabel;

    @FXML
    private Label lblProdMins;

    @FXML
    private Label lblProdSeconds;

    @FXML
    private Label lblMins;

    @FXML
    private ComboBox<?> cbProdMins;

    @FXML
    private ComboBox<?> cbProdSecs;

    @FXML
    private TextField tfProdTask;

    @FXML
    private TextField tfProdGoal;

    @FXML
    private TextField tfProdEstPom;

    @FXML
    private Button btnProdStart;

    @FXML
    private Label breakTimerLabel;

    @FXML
    private Label lblBreakMins;

    @FXML
    private Label lblBreakSeconds;

    @FXML
    private ComboBox<Integer> cbBreakMins;

    @FXML
    private ComboBox<Integer> cbBreakSecs;

    @FXML
    private TextField tfBreak;

    @FXML
    private TextField tfSearchTask;

    @FXML
    private Button searchButtonTask;

    @FXML
    private TextField tfSearchGoal;

    @FXML
    private Button searchButtonGoal;

    @FXML
    private Button refreshButton;


    private IntegerProperty prodMinutes = new SimpleIntegerProperty(25);
    private IntegerProperty prodSeconds = new SimpleIntegerProperty(0);

    private IntegerProperty breakMinutes = new SimpleIntegerProperty(5);
    private IntegerProperty breakSeconds = new SimpleIntegerProperty(0);


    Timeline timeline;
    Timeline timelineBreak;

    private static int modifyTimerIndex;

    public static int getTimerIndex() {return modifyTimerIndex;}







    void resetProdLabels() {
        lblProdMins.textProperty().bind(prodMinutes.asString());
        lblProdSeconds.textProperty().bind(prodSeconds.asString());

        prodMinutes.set(25);
        prodSeconds.set(0);
    }

    void resetBreakLabels() {
        lblBreakMins.textProperty().bind(breakMinutes.asString());
        lblBreakSeconds.textProperty().bind(breakSeconds.asString());

        breakMinutes.set(5);
        breakSeconds.set(0);
    }

    private void subtractTimerSeconds(){
        int updatedSeconds = prodSeconds.get();
        prodSeconds.set(updatedSeconds - 1);

    }

    private void subtractBreakSeconds(){
        int updatedSecondsBreak = breakSeconds.get();
        breakSeconds.set(updatedSecondsBreak - 1);
    }

    private void subtractBreakMinutes(){
        int updatedMinutesBreak = breakMinutes.get();
        breakMinutes.set(updatedMinutesBreak - 1);
        breakSeconds.set(59);
    }

    private void subtractTimerMinutes(){
        int updatedMinutes = prodMinutes.get();
        prodMinutes.set(updatedMinutes -1);
        prodSeconds.set(59);

    }

    private void manageTimerUpdate(){
        if(prodSeconds.get() == 0 && prodMinutes.get() == 0){
            System.out.println("Pomodoro complete");
        }
    }

    @FXML
    void handleNewProdTimer(ActionEvent event)  {

        resetProdLabels();

    }

    @FXML
    void handleNewBreakTimer(ActionEvent event)  {
        resetBreakLabels();
    }

    @FXML
    void startNewBreakTimer(ActionEvent event){
        breakMinutes.set(4);
        breakSeconds.set(59);
        timelineBreak = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {


            subtractBreakSeconds();
            if(breakSeconds.get() == 0 && breakMinutes.get() == 0){
                timelineBreak.pause();
                resetBreakLabels();


                //play media object
                String masterString = new File("F:\\Pomodoro App\\master_trimmed.mp3").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(masterString));
                player.play();

            }
            else if(breakSeconds.get() == 0){
                subtractBreakMinutes();
            }

        }));
        timelineBreak.setCycleCount(Animation.INDEFINITE);
        timelineBreak.play();


    }

    public void startProdLabelUpdater() {

        prodSeconds.set(5);
        prodMinutes.set(0);

        //attributes for the new timer created
        int startMins = 25;
        int startSecs = 0;
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter.ofPattern("yy/MM/dd").format(LocalDate.now());
        String dateString = todaysDate.toString();
        String currentTask = tfProdTask.getText();
        String ultGoal = tfProdGoal.getText();
        int estPomodoro = Integer.parseInt(tfProdEstPom.getText());

       /* //create new productivity timer
        PomodoroTimer pomodoroTimer = new PomodoroTimer( ultGoal, estPomodoro);
        pomodoroTimer.setStartMinute(startMins);
        pomodoroTimer.setStartSecond(startSecs);
        pomodoroTimer.setStartDate(todaysDate.toString());*/

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

            subtractTimerSeconds();
            if(prodSeconds.get() == 0 && prodMinutes.get() == 0){
                timeline.pause();
                resetProdLabels();
                Database.insertPomodoro(todaysDate, currentTask, ultGoal);

                //refresh the tableview after data insert
                TimerList.getTimerList().clear();
                Database.updateTimerQuery();
                tvTimerDisplay.setItems(TimerList.getTimerList());

                //play media object
                String masterString = new File("F:\\Pomodoro App\\master_trimmed.mp3").toURI().toString();
                MediaPlayer player = new MediaPlayer(new Media(masterString));
                player.play();


                
            }
            else if(prodSeconds.get() == 0){
                subtractTimerMinutes();
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    public void handleDelete(ActionEvent event) throws IOException {
        //get selected timer from tv
        PomodoroTimer timerToRemove = tvTimerDisplay.getSelectionModel().getSelectedItem();

        // Check if no timer was selected
        if (timerToRemove == null) {
            // Create alert saying a customer must be selected to be removed
            ResourceBundle rb = ResourceBundle.getBundle("MainScreen", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("errorRemovingTimer"));
            alert.setContentText(rb.getString("errorRemovingTimerMessage"));
            alert.showAndWait();
            return;
        }

        Database.deletePomodoro(timerToRemove.getStartDate());

        //refresh the tableview after data insert
        TimerList.getTimerList().clear();
        Database.updateTimerQuery();
        tvTimerDisplay.setItems(TimerList.getTimerList());

    }

    @FXML
    public void handleUpdate(ActionEvent event) throws IOException{
        PomodoroTimer timerToUpdate = tvTimerDisplay.getSelectionModel().getSelectedItem();
        modifyTimerIndex = TimerList.getTimerList().indexOf(timerToUpdate);


        //go to update screen
        Parent updateScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/UpdateScreen.fxml"));
        Scene timerScene = new Scene(updateScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();

    }

    @FXML
    void handleSearchByTask(ActionEvent event){

        String searchTaskStr = tfSearchTask.getText();

        TimerList.getTimerList().clear();
        Database.searchByTask(searchTaskStr);
        tvTimerDisplay.setItems(TimerList.getTimerList());
    }

    @FXML
    void handleSearchByGoal(ActionEvent event){

        String searchGoalStr = tfSearchGoal.getText();

        TimerList.getTimerList().clear();
        Database.searchByGoal(searchGoalStr);
        tvTimerDisplay.setItems(TimerList.getTimerList());

    }

    @FXML
    void handleRefresh(ActionEvent event) throws IOException{

        //refresh home screen
        Parent mainScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/MainScreen.fxml"));
        Scene timerScene = new Scene(mainScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();

    }



    @FXML
    void handleGoToReports(ActionEvent event) throws IOException{
        //refresh home screen
        Parent mainScreen = FXMLLoader.load(getClass().getClassLoader().getResource("models/ReportScreen.fxml"));
        Scene timerScene = new Scene(mainScreen);
        //next line is getting stage information
        Stage calendarStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        calendarStage.setScene((timerScene));
        calendarStage.show();
    }

    @FXML
    void startNewProdTimer(ActionEvent event) {

        startProdLabelUpdater();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try{
            TimerList.getTimerList().clear();
            Database.updateTimerQuery();
            tvTimerDisplay.setItems(TimerList.getTimerList());
            tableColDate.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("startDate"));
            tvColPomoSpent.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, Integer>("pomodoroSpent"));
            tvColTask.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("taskInWork"));
            tvColGoal.setCellValueFactory(new PropertyValueFactory<PomodoroTimer, String>("ultimateGoal"));


        } catch (Exception e){
            e.printStackTrace();
        }

    }}

















