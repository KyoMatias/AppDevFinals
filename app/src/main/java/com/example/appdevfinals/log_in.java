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
    Button loginBtn;
    EditText usernameEditTxt, passwordEditTxt;
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

        loginBtn = findViewById(id.login_btn);
        usernameEditTxt = findViewById(id.username_edit_text);
        passwordEditTxt = findViewById(id.password_edit_text);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(log_in.this, main_menu.class);
                if (usernameEditTxt.getText().toString().equals("user") && passwordEditTxt.getText().toString().equals("1234")) {
                    //I could create a custom toast that has a shorter duration
//                    Toast.makeText(getApplicationContext(),"Redirecting",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Logging In...",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password!",Toast.LENGTH_SHORT).show();
                    triesCounter--;

                    if (triesCounter == 0){
                        loginBtn.setEnabled(false);
                    }
                }
            }
        });
    }
}
