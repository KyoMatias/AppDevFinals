package com.example.appdevfinals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class main_menu_screen extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String myUID;
    BottomNavigationView navigationView;

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

        firebaseAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        main_menu_fragment fragment = new main_menu_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = menuItem -> {

        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.nav_home) {
            main_menu_fragment menuFragment = new main_menu_fragment();
            FragmentTransaction menuFragmentTransaction = getSupportFragmentManager().beginTransaction();
            menuFragmentTransaction.replace(R.id.content, menuFragment, "");
            menuFragmentTransaction.commit();
            return true;
        } else if (menuItemId == R.id.nav_profile) {
            profile_fragment profileFragment = new profile_fragment();
            FragmentTransaction profileFragmentTransaction = getSupportFragmentManager().beginTransaction();
            profileFragmentTransaction.replace(R.id.content, profileFragment);
            profileFragmentTransaction.commit();
            return true;
        } else if (menuItemId == R.id.nav_post) {
            new_post_fragment newPostFragment = new new_post_fragment();
            FragmentTransaction newPostFragmentTransaction = getSupportFragmentManager().beginTransaction();
            newPostFragmentTransaction.replace(R.id.content, newPostFragment, "");
            newPostFragmentTransaction.commit();
            return true;
        }
        return false;
    };
}