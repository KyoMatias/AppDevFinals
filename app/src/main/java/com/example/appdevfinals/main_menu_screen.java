package com.example.appdevfinals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_menu_screen extends AppCompatActivity {
/*
* TO DO
*
* Implement the ff functions:
* 1. On click of profile picture, REDIRECT to profile screen
* 2. Status Post
* 3. Status posts need to accept text, images, videos, and audio files
* 4. Status posts should display the user's profile picture and name beside the post
* 5. Like and comment posts
* 6. Previous status posts should be viewable by the user
* 7. A simulated feed (HardCoded)
*
* Need to use lists and list views for the posts and simulated feed
*
* */

    private ImageButton ibHeaderAccount, ibAppbarAccount, ibSettings, ibLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibHeaderAccount = findViewById(R.id.main_header_account_button);
        ibAppbarAccount = findViewById(R.id.main_appbar_account_button);

        ibSettings = findViewById(R.id.main_settings_button);
//        ibLogout = findViewById(R.id.main_logout_button);

        Intent openProfile = new Intent(main_menu_screen.this, profile_screen.class);
        Intent openSettings = new Intent(main_menu_screen.this, settings_screen.class);

        ibHeaderAccount.setOnClickListener(v -> startActivity(openProfile));
        ibAppbarAccount.setOnClickListener(v -> startActivity(openProfile));
        ibSettings.setOnClickListener(v -> startActivity(openSettings));


    }
}