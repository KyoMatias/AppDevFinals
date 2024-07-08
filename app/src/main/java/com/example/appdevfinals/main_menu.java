package com.example.appdevfinals;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_menu extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}