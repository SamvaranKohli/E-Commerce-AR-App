package com.example.att23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    Connection connect;
    EditText name, email, password;
    Button signUp;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signup);
        login = findViewById(R.id.login_text);

        login.setPaintFlags(login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                {
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                    return;
                }

                if(email.getText().toString().isEmpty())
                {
                    email.setError("Email is empty");
                    email.requestFocus();
                    return;
                }

                if(password.getText().toString().isEmpty())
                {
                    password.setError("Enter the password");
                    password.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    password.setError("Length of the password should be more than 6");
                    password.requestFocus();
                    return;
                }

                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();

                    connect = connectionHelper.connectionclass();

                    if(connect != null)
                    {

                        String query = "select email from users where email = '" + email.getText().toString() + "'";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        if(rs.next())
                        {
                            email.setError("Email is already registered");
                            email.requestFocus();
                            return;
                        }

                        query = "insert into users values('" + name.getText().toString() + "', '" + email.getText().toString() + "', '" + password.getText().toString() + "')";
                        st = connect.createStatement();
                        int i = st.executeUpdate(query);

                        if (i > 0)
                        {
                            Intent myIntent = new Intent(SignUp.this, SignIn.class);
                            SignUp.this.startActivity(myIntent);
                        }

                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Hello", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {

                }


            }
        });
    }

    public void goto_signin(View v)
    {
        Intent myIntent = new Intent(SignUp.this, SignIn.class);
        SignUp.this.startActivity(myIntent);
    }
}