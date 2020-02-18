package ObjClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimerList {

    private static ObservableList<PomodoroTimer> timerList = FXCollections.observableArrayList();

    public static ObservableList<PomodoroTimer> getTimerList(){
        return timerList;
    }
}
