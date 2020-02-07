package view_controller;

import ObjClasses.Timers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import ObjClasses.PomodoroTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainScreenController {

    @FXML
    private TableColumn<?, ?> tableColDate;

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

    private IntegerProperty prodMinutes = new SimpleIntegerProperty(25);
    private IntegerProperty prodSeconds = new SimpleIntegerProperty(0);


    Timeline timeline;

    /*//play the media object
    String masterString = new File("D:\\Pomodoro App\\master_trimmed").toURI().toString();
    MediaPlayer player = new MediaPlayer(new Media(masterString));
    player.play();*/





    void resetProdLabels() {
        lblProdMins.textProperty().bind(prodMinutes.asString());
        lblProdSeconds.textProperty().bind(prodSeconds.asString());

        prodMinutes.set(25);
        prodSeconds.set(0);
    }

    private void subtractTimerSeconds(){
        int updatedSeconds = prodSeconds.get();
        prodSeconds.set(updatedSeconds - 1);



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

    public void startProdLabelUpdater() {

        prodSeconds.set(5);
        prodMinutes.set(0);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

            subtractTimerSeconds();
            if(prodSeconds.get() == 0 && prodMinutes.get() == 0){
                timeline.pause();
                resetProdLabels();
                System.out.println("Timeline paused");


                //play media object
                String masterString = new File("D:\\Pomodoro App\\master_trimmed.mp3").toURI().toString();
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
    void startNewProdTimer(ActionEvent event) {

        //attributes for the new timer created
        int startMins = 25;
        int startSecs = 0;
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter.ofPattern("yy/MM/dd").format(LocalDate.now());
        String currentTask = tfProdTask.getText();
        String ultGoal = tfProdGoal.getText();
        int estPomodoro = Integer.parseInt(tfProdEstPom.getText());


        //create new productivity timer
        PomodoroTimer pomodoroTimer = new PomodoroTimer(currentTask, ultGoal, estPomodoro);
        pomodoroTimer.setStartMinute(startMins);
        pomodoroTimer.setStartSecond(startSecs);
        pomodoroTimer.setStartDate(todaysDate);


        startProdLabelUpdater();

    }

}

















