package com.example.laundrybusiness;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;


public class Signup extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


    textInputEditTextFullname = findViewById(R.id.fullname);
    textInputEditTextUsername = findViewById(R.id.username);
    textInputEditTextPassword = findViewById(R.id.password);
    textInputEditTextEmail = findViewById(R.id.email);
    buttonSignUp = findViewById(R.id.buttonSignUp);
    textViewLogin = findViewById(R.id.loginText);
    progressBar = findViewById(R.id.progress);


    buttonSignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String fullname, username, password, email;
            fullname = String.valueOf(textInputEditTextFullname.getText());
            username = String.valueOf(textInputEditTextUsername.getText());
            password = String.valueOf(textInputEditTextPassword.getText());
            email = String.valueOf(textInputEditTextEmail.getText());

            if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[4];
                        field[0] = "fullname";
                        field[1] = "username";
                        field[2] = "password";
                        field[3] = "email";

                        String[] data = new String[4];
                        data[0] = fullname;
                        data[1] = username;
                        data[2] = password;
                        data[3] = email;
                        PutData putData = new PutData("http://192.168.0.103/LaundryServiceLogInRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if(result.equals("Sign Up Success")) {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                 Intent intent  = new  Intent(getApplicationContext(), Login.class);
                                 startActivity(intent);
                                 finish();
                                }
                                    else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
            }
        }
    });

        });
    }
}