package com.example.appdevfinals;

import static com.example.appdevfinals.R.*;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button loginBtn;

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


        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent = new Intent(log_in.this, main_menu.class);
                startActivity(intent);

                }
        });
    }

/*        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong
                            Credentials",Toast.LENGTH_SHORT).show();

                            tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        }
    }*/
}