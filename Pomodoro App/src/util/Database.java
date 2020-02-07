package util;

import java.sql.*;


public class Database {

    // db parameters CHANGE THE DRIVE LETTER DEPENDING ON COMPUTER USED
    private static String url = "jdbc:sqlite:D:/Pomodoro App/db/pomodoro.db";



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

    }

    public static void createAllTables(){
        createUserTable();
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
            }
            else{
               valid = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
      return valid;
    }
}











