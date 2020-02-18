package util;

import ObjClasses.PomodoroTimer;
import ObjClasses.TimerList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;


public class Database {

    // db parameters CHANGE THE DRIVE LETTER DEPENDING ON COMPUTER USED
    private static String url = "jdbc:sqlite:F:/Pomodoro App/db/pomodoro.db";



   //Connect to SQLITE database
    public static void connect() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }



    public static void createUserTable(){
        // SQL statement for creating user table
        String sqlUserTable = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    _id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    name text NOT NULL,\n"
                + "    password text NOT NULL\n"
                + ");";

        try ( Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()){
            //create the table
            stmt.execute(sqlUserTable);
        } catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("User table was created successfully");
    }


    public static void createTimerTable(){
        // SQL statement for creating user table
        String sqlTimerTable = "CREATE TABLE IF NOT EXISTS timers (\n"
                + "    spentDate text NOT NULL,\n"
                + "    pomodoroSpent integer NOT NULL,\n"
                + "    task text NOT NULL,\n"
                + "    goal text NOT NULL\n"
                + ");";

        try ( Connection conn = DriverManager.getConnection(url);
              Statement stmt = conn.createStatement()){
            //create the table
            stmt.execute(sqlTimerTable);


        } catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Timer table was created successfully");

    }

    public static void createAllTables(){
        createUserTable();
        createTimerTable();
    }






    //Operational DB actions

    public static boolean isUserValid(String name, String pass) {
        boolean valid = false;
        String sqlUserSel = "SELECT * FROM users WHERE name = '" + name + "'AND password = '" + pass + "'";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlUserSel);

            if(rs.next()) {
                valid = true;
                rs.close();
            }
            else{
               valid = false;
               rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
      return valid;
    }

    public static ResultSet updateTimerQuery() {
        ResultSet rsUpdateTimer = null;

        String timerUpdateStr = "SELECT * FROM timers";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            rsUpdateTimer = stmt.executeQuery(timerUpdateStr);
            while (rsUpdateTimer.next()) {
                String timerString = rsUpdateTimer.getString("spentDate");
                int timerInt = rsUpdateTimer.getInt("pomodoroSpent");
                String timerTask = rsUpdateTimer.getString("task");
                String timerGoal = rsUpdateTimer.getString("goal");

                System.out.println(timerString + timerInt + timerTask+ timerGoal);

                PomodoroTimer updatedTimer = new PomodoroTimer(timerInt, timerTask, timerGoal);
                updatedTimer.setStartDate(timerString);



                //add to TimerList singleton
                TimerList.getTimerList().add(updatedTimer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return rsUpdateTimer;
    }

    public static ResultSet updateReportQuery(int pomoSpent) {
        ResultSet rsUpdateTimer = null;

        String timerUpdateStr = "SELECT * FROM timers WHERE pomodoroSpent >='"+pomoSpent +"'";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            rsUpdateTimer = stmt.executeQuery(timerUpdateStr);
            while (rsUpdateTimer.next()) {
                String timerString = rsUpdateTimer.getString("spentDate");
                int timerInt = rsUpdateTimer.getInt("pomodoroSpent");
                String timerTask = rsUpdateTimer.getString("task");
                String timerGoal = rsUpdateTimer.getString("goal");



                PomodoroTimer updatedTimer = new PomodoroTimer(timerInt, timerTask, timerGoal);
                updatedTimer.setStartDate(timerString);



                //add to TimerList singleton
                TimerList.getTimerList().add(updatedTimer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return rsUpdateTimer;
    }

    public static void deletePomodoro(String deletedDate){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();

        //if the button was clicked delete timer entry by setting to inactive
        if (result.get() == ButtonType.OK) {

            String delTimerStr = "DELETE FROM timers WHERE spentDate = '" + deletedDate + "'";

            try {
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(delTimerStr);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePomodoro(String date, int pomodoroSpent, String task, String goal){
        try{
            String updateSQL = "UPDATE timers SET pomodoroSpent = '"+pomodoroSpent+"', task = '"+task+"', goal = '"+goal+"' WHERE spentDate = '" + date + "'";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateSQL);
            System.out.println("SQL was successfully updated");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void insertPomodoro (LocalDate date, String task, String ultimateGoal){
        boolean dateExists = false;
        String dateExistSQL = "SELECT * FROM timers WHERE spentDate = '" + date + "'";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(dateExistSQL);
            if(rs.next()) {
                rs.close();
                String updatePomodoro = "UPDATE timers SET pomodoroSpent = pomodoroSpent + 1 WHERE spentDate = '" + date + "'";
                stmt.executeUpdate(updatePomodoro);
                System.out.println("Date existed so pomodoro updated count");
                stmt.close();
            }
            else {
                rs.close();
                String insertPomodoroString = "INSERT INTO timers (spentDate, pomodoroSpent, task, goal) VALUES('"+date+"',1, '"+task+"', '"+ultimateGoal+"') ";
                stmt.executeUpdate(insertPomodoroString);
                System.out.println("Date didn't exist so a new row was inserted");
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static ResultSet searchByTask(String task){

        ResultSet rsSearchTask = null;
        String searchTaskStr = "SELECT * FROM timers  WHERE task = '"+task+"'";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            rsSearchTask = stmt.executeQuery(searchTaskStr);
            while (rsSearchTask.next()) {
                String timerString = rsSearchTask.getString("spentDate");
                int timerInt = rsSearchTask.getInt("pomodoroSpent");
                String timerTask = rsSearchTask.getString("task");
                String timerGoal = rsSearchTask.getString("goal");



                PomodoroTimer taskSearchTimer = new PomodoroTimer(timerInt, timerTask, timerGoal);
                taskSearchTimer.setStartDate(timerString);



                //add to TimerList singleton
                TimerList.getTimerList().add(taskSearchTimer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return rsSearchTask;
    }

    public static ResultSet searchByGoal(String goal){

        ResultSet rsSearchGoal = null;
        String searchTaskStr = "SELECT * FROM timers  WHERE goal = '"+goal+"'";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            rsSearchGoal = stmt.executeQuery(searchTaskStr);
            while (rsSearchGoal.next()) {
                String timerString = rsSearchGoal.getString("spentDate");
                int timerInt = rsSearchGoal.getInt("pomodoroSpent");
                String timerTask = rsSearchGoal.getString("task");
                String timerGoal = rsSearchGoal.getString("goal");



                PomodoroTimer taskSearchTimer = new PomodoroTimer(timerInt, timerTask, timerGoal);
                taskSearchTimer.setStartDate(timerString);



                //add to TimerList singleton
                TimerList.getTimerList().add(taskSearchTimer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return rsSearchGoal;
    }

    }












