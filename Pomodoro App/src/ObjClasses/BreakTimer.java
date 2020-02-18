package ObjClasses;

public class BreakTimer extends Timers {

    private String breakTask;



    public BreakTimer(String breakTask, int startMinute, int startSecond, String startDate){
        super(startMinute, startSecond, startDate);
        this.breakTask = breakTask;
    }


    //setter
    public void setBreakTask(String breakTask) {
        this.breakTask = breakTask;
    }

    //getter
    public String getBreakTask() {
        return breakTask;
    }
}
