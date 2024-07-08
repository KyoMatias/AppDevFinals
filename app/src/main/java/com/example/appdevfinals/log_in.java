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
public class log_in extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername, etPassword;
    int triesCounter = 10;

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

        /*Login functionality
        input user credentials
        click login button
        check if credentials are correct in local text file which will be stored in the raw/assets folder
        if credentials are correct, go to main menu
        if credentials are incorrect, after 10 tries, disable login button
        */

        btnLogin = findViewById(id.login_btn);
        etUsername = findViewById(id.username_edit_text);
        etPassword = findViewById(id.password_edit_text);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(log_in.this, main_menu.class);
                if (etUsername.getText().toString().equals("user") && etPassword.getText().toString().equals("1234")) {
                    //I could create a custom toast that has a shorter duration
//                    Toast.makeText(getApplicationContext(),"Redirecting",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Logging In...",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password!",Toast.LENGTH_SHORT).show();
                    triesCounter--;

                    if (triesCounter == 0){
                        btnLogin.setEnabled(false);
                    }
                }
            }
        });
    }
}
