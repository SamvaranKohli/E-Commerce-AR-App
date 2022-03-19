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

public class SignIn extends AppCompatActivity {

    Connection connect;
    EditText email, password;
    Button signin;
    TextView signup;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signup);
        signup = findViewById(R.id.login_text);

        signup.setPaintFlags(signup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        signin.setOnClickListener(new View.OnClickListener() {
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


                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();

                    connect = connectionHelper.connectionclass();

                    if(connect != null)
                    {

                        String query = "select * from users where email = '" + email.getText().toString() + "'";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        if (rs.next())
                        {
                            if(rs.getString("pass_word").equals(password.getText().toString()))
                            {
                                String id = rs.getString("id");
                                editor = preferences.edit();
                                editor.putString("Login", id);
                                editor.commit();

                                Intent myIntent = new Intent(SignIn.this, LandingPage.class);
                                SignIn.this.startActivity(myIntent);
                            }
                            else
                            {
                                password.getText().clear();
                                password.setError("Incorrect Password");
                                password.requestFocus();
                                return;
                            }
                        }

                        else
                        {
                            email.setError("Incorrect Email or not Registered");
                            email.requestFocus();
                            return;
                        }

                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Hello", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {

                }


            }
        });

    }

    public void goto_signup(View v)
    {
        Intent myIntent = new Intent(SignIn.this, SignUp.class);
        SignIn.this.startActivity(myIntent);
    }
}