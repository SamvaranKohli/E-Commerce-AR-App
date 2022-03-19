package com.example.att23;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    Connection con;

    String uname, pass, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionclass(){

        ip = "192.168.0.149";
        port = "1433";
        database = "Furniture";
        uname = "test";
        pass = "test";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String ConnectionUrl = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionUrl= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+uname+";password="+pass+";";

            connection = DriverManager.getConnection(ConnectionUrl);

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        return connection;
    }
}
