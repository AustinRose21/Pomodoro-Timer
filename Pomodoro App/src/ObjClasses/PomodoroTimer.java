package ObjClasses;

import java.sql.Timestamp;


public class PomodoroTimer extends Timers {

    private String taskInWork;
    private String ultimateGoal;
    private int estPomoToday;
    private int pomodoroSpent;


    //full
    public PomodoroTimer(int startMinute, int startSecond, String startDate, Timestamp endTimeStamp, String taskInWork, String ultimateGoal, int estPomoToday) {
        super(startMinute, startSecond, startDate);
        this.taskInWork = taskInWork;
        this.ultimateGoal = ultimateGoal;
        this.estPomoToday = estPomoToday;
    }

    //partial constructor
    public PomodoroTimer(int pomodoroSpent, String taskInWork, String ultimateGoal) {
        this.pomodoroSpent = pomodoroSpent;
        this.taskInWork = taskInWork;
        this.ultimateGoal = ultimateGoal;
    }


    public String getTaskInWork() {
        return taskInWork;
    }

    public void setTaskInWork(String taskInWork) {
        this.taskInWork = taskInWork;
    }

    public String getUltimateGoal() {
        return ultimateGoal;
    }

    public void setUltimateGoal(String ultimateGoal) {
        this.ultimateGoal = ultimateGoal;
    }

    public int getEstPomoToday() {
        return estPomoToday;
    }

    public void setEstPomoToday(int estPomoToday) {
        this.estPomoToday = estPomoToday;
    }

    public int getPomodoroSpent() {
        return pomodoroSpent;
    }

    public void setPomodoroSpent(int pomodoroSpent) {
        this.pomodoroSpent = pomodoroSpent;
    }
}
