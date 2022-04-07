package com.example.att23;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionHelper {

    Connection con;

    String uname, pass, ip, port, database;

    FirebaseDatabase data;

    TextView txt;

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

    void setValues(String hello)
    {
        //Log.d("firebase", hello);
        ip = hello;
    }
}
