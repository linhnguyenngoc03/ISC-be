package com.example.demo.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBUtils {
    public static Connection makeConnection() throws Exception{
        Connection cn=null;
        String uid="sa";
        String pwd="12345";
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;databaseName=Hommie1;user="+uid+";password="+pwd;
        Class.forName(driver);
        cn=DriverManager.getConnection(url);
        return cn;
    }

    /*public static Connection makeConnection(){
        try {
            String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databasename=Hommie;instanceName=HIEU;";
            Class.forName(driver);
            Connection cn=DriverManager.getConnection(url, "sa", "12345");
            return cn;
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }*/

    public static String getCurrentDate(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = date.format(now);
        return currentDate;
    }
}
