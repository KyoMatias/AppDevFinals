package com.example.appdevfinals;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash_screen extends AppCompatActivity {
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        if (mAuth != null) {
            currentUser = mAuth.getCurrentUser();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openLoginScreen = new Intent(splash_screen.this, login_screen.class);
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    mAuth.signOut();
                }
                startActivity(openLoginScreen);
                finish();
            }
        }, 1000);


/*        Button btnRegister = findViewById(R.id.splash_register_button);
        TextView btnLogin = findViewById(R.id.login_text_label);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRegistrationScreen = new Intent(splash_screen.this, register_screen.class);
                startActivity(openRegistrationScreen);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLoginScreen = new Intent(splash_screen.this, login_screen.class);
                startActivity(openLoginScreen);
            }
        });*/

    }
}