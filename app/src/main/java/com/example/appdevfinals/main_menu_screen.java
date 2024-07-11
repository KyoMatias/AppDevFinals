package com.example.appdevfinals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class main_menu_screen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    Button editProfileBtn;
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

        Intent openEdit = new Intent(main_menu_screen.this, edit_profile_screen.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = menuItem -> {

        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.nav_home) {
            main_menu_fragment fragment = new main_menu_fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, "");
            fragmentTransaction.commit();
            return true;
        } else if (menuItemId == R.id.nav_profile) {
            profile_fragment fragment1 = new profile_fragment();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.content, fragment1);
            fragmentTransaction1.commit();
            return true;
        } else if (menuItemId == R.id.nav_inbox) {
            inbox_fragment listFragment = new inbox_fragment();
            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction3.replace(R.id.content, listFragment, "");
            fragmentTransaction3.commit();
            return true;
        } else if (menuItemId == R.id.nav_post) {
            post_fragment fragment4 = new post_fragment();
            FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction4.replace(R.id.content, fragment4, "");
            fragmentTransaction4.commit();
            return true;
        }
        return false;
    };
}