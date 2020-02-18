package ObjClasses;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class Timers {



    private int startMinute;
    private int startSecond;
    private String startDate;
    private Timestamp endTimeStamp;


    //full constructor
    public Timers(int startMinute, int startSecond, String startDate) {
        this.startMinute = startMinute;
        this.startSecond = startSecond;
        this.startDate = startDate;
    }

    //default constructor
    public Timers(){}



    //setters

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public void setStartSecond(int startSecond) {
        this.startSecond = startSecond;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }



    //getters


    public int getStartMinute() {
        return startMinute;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public String getStartDate() {
        return startDate;
    }


}
