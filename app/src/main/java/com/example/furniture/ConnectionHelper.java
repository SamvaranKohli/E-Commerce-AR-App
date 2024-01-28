package com.example.furniture;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    String uname, pass, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionclass(){

        ip = "122.176.111.140";
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
