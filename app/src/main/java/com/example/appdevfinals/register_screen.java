package com.example.appdevfinals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class register_screen extends AppCompatActivity {

/*
* TO DO
*
* Implement a user registration function
*
* Needs to do be able to save the user data locally in their phone
* Needs to be able to save the ff:
* First name
* Last name
* User name
* Password
* Email
*
* Might need validation for password and email
* */

    private EditText etFirstName, etLastName, etUsername, etPassword, etEmail;
    private Button btnRegister, btnBack, btnReadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFirstName = findViewById(R.id.register_first_name_edit_text);
        etLastName = findViewById(R.id.register_last_name_edit_text);
        etUsername = findViewById(R.id.register_username_edit_text);
        etPassword = findViewById(R.id.register_password_edit_text);
        etEmail = findViewById(R.id.register_email_edit_text);
        btnRegister = findViewById(R.id.register_button);
        btnBack = findViewById(R.id.back_button);
        btnReadData = findViewById(R.id.read_data_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLogin = new Intent(register_screen.this, login_screen.class);
                startActivity(openLogin);
            }
        });

        btnReadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUserData();
            }
        });


    }


    private void saveUserData() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || email.isEmpty()) {
            // Show error message if any field is empty
            // For simplicity, we use a Toast message here
            Toast.makeText(register_screen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userData = firstName + "," + lastName + "," + username + "," +
                password + "," + email + "\n";

        try {
            FileOutputStream fos = openFileOutput("user_data.txt", MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(userData);
            writer.close();
            Toast.makeText(register_screen.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(register_screen.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void readUserData() {
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();

            // Display or use the data
            String fileContent = stringBuilder.toString();
            Toast.makeText(this, fileContent, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to read user data", Toast.LENGTH_SHORT).show();
        }
    }
}