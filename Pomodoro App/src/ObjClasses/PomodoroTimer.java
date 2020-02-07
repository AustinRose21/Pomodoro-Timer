package ObjClasses;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class PomodoroTimer extends Timers {

    private String taskInWork;
    private String ultimateGoal;
    private int estPomoToday;


    //full
    public PomodoroTimer(int startMinute, int startSecond, LocalDate startDate, Timestamp endTimeStamp, String taskInWork, String ultimateGoal, int estPomoToday) {
        super(startMinute, startSecond, startDate);
        this.taskInWork = taskInWork;
        this.ultimateGoal = ultimateGoal;
        this.estPomoToday = estPomoToday;
    }

    //partial constructor
    public PomodoroTimer(String taskInWork, String ultimateGoal, int estPomoToday) {
        this.taskInWork = taskInWork;
        this.ultimateGoal = ultimateGoal;
        this.estPomoToday = estPomoToday;
    }
}
