package com.example.appdevfinals;

import static com.example.appdevfinals.R.*;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class log_in extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername, etPassword;
    int triesCounter = 10;

        /*Login functionality
        input user credentials
        click login button
        check if credentials are correct in local text file which will be stored in the raw/assets folder
        if credentials are correct, go to main menu
        if credentials are incorrect, after 10 tries, disable login button
        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin = findViewById(id.login_btn);
        etUsername = findViewById(id.username_edit_text);
        etPassword = findViewById(id.password_edit_text);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                validateLogin();

            }
        });

    }

    private void validateLogin() {
        String loginUsername = etUsername.getText().toString().trim();
        String loginPassword = etPassword.getText().toString().trim();

        if (loginUsername.isEmpty() || loginPassword.isEmpty()) {
            Toast.makeText(log_in.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileInputStream fis = openFileInput("user_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            boolean isAuthenticated = false;

            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 5) {
                    String savedUsername = userDetails[2];
                    String savedPassword = userDetails[3];

                    if (savedUsername.equals(loginUsername) && savedPassword.equals(loginPassword)) {
                        isAuthenticated = true;
                        break;
                    }
                }
            }

            reader.close();

            Intent openMainMenu = new Intent(log_in.this, main_menu.class);
            if (isAuthenticated) {
                Toast.makeText(log_in.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(openMainMenu);
            } else {
                Toast.makeText(log_in.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(log_in.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
        }
    }

}
