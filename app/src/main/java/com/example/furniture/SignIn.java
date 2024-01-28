package com.example.furniture;

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
    EditText emailEditText, passwordEditText;
    Button signInButton;
    TextView signUpButton;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // set xml id
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signInButton = findViewById(R.id.signup);
        signUpButton = findViewById(R.id.login_text);

        // underline the sign up button
        signUpButton.setPaintFlags(signUpButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // store local values
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if email entered does not match the email pattern
                if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches())
                {
                    emailEditText.setError("Enter a valid email address");
                    emailEditText.requestFocus();
                    return;
                }

                // if email is not entered
                if(emailEditText.getText().toString().isEmpty())
                {
                    emailEditText.setError("Email is empty");
                    emailEditText.requestFocus();
                    return;
                }

                // if password is not entered
                if(passwordEditText.getText().toString().isEmpty())
                {
                    passwordEditText.setError("Enter the password");
                    passwordEditText.requestFocus();
                    return;
                }

                // check credentials from the database
                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();

                    connect = connectionHelper.connectionclass();

                    if(connect != null)
                    {

                        // select the details of user from the given email
                        String query = "select * from users where email = '" + emailEditText.getText().toString() + "'";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        // if email exists
                        if (rs.next()) {
                            if (rs.getString("pass_word").equals(passwordEditText.getText().toString())) {
                                String id = rs.getString("id");
                                editor = preferences.edit();
                                editor.putString("Login", id);
                                editor.commit();

                                Intent myIntent = new Intent(SignIn.this, LandingPage.class);
                                SignIn.this.startActivity(myIntent);
                            } else {
                                passwordEditText.getText().clear();
                                passwordEditText.setError("Incorrect Email or Password");
                                passwordEditText.requestFocus();
                                return;
                            }
                        }
                        // if email does not exist
                        else
                        {
                            emailEditText.setError("Incorrect Email or not Registered");
                            emailEditText.requestFocus();
                            return;
                        }

                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "No Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {

                }


            }
        });

    }

    // function to go to signup page on textview (setup in xml [onClick])
    public void MoveToSignUp(View v)
    {
        Intent myIntent = new Intent(SignIn.this, SignUp.class);
        SignIn.this.startActivity(myIntent);
    }
}