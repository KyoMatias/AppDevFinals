package com.example.appdevfinals;

import static com.example.appdevfinals.R.*;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class login_screen extends AppCompatActivity {

    FirebaseUser currentUser;
    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    int triesCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent openRegistrationScreen = new Intent(login_screen.this, register_screen.class);

        btnLogin = findViewById(id.login_btn);
        etEmail = findViewById(id.email_edit_text);
        etPassword = findViewById(id.password_edit_text);

        //On Click, Validate
        //btnLogin.setOnClickListener(v -> validateLogin());
        btnRegister.setOnClickListener(v -> startActivity(openRegistrationScreen));

        if (mAuth != null) {
            currentUser = mAuth.getCurrentUser();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                // if format of email doesn't matches return null
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Invalid Email");
                    etEmail.setFocusable(true);

                } else {
                    loginUser(email, pass);
                }
            }
        });

    }

    private void loginUser(String email, String pass) {
        loadingBar.setMessage("Logging In....");
        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    loadingBar.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                        String email = user.getEmail();
                        String uid = user.getUid();
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", email);
                        hashMap.put("uid", uid);
                        hashMap.put("name", "");
                        hashMap.put("onlineStatus", "online");
                        hashMap.put("profilePicture", "");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        // store the value in Database in "Users" Node
                        DatabaseReference reference = database.getReference("Users");

                        // storing the value in Firebase
                        reference.child(uid).setValue(hashMap);
                    }
                    Toast.makeText(login_screen.this, "Registered User " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(login_screen.this, main_menu_screen.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(login_screen.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(login_screen.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

/*    private void validateLogin() {
        String loginUsername = etEmail.getText().toString().trim();
        String loginPassword = etPassword.getText().toString().trim();
        Intent openMainMenu = new Intent(login_screen.this, main_menu_screen.class);
        Intent assignUsername = new Intent();
        Intent assignProfilePicture = new Intent();

        //Error Handling for empty fields
        if (loginUsername.isEmpty() || loginPassword.isEmpty()) {
            Toast.makeText(login_screen.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validating login credentials
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            boolean isAuthenticated = false;
            String profilePictureFileName = null;

            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 6) {
                    String savedUsername = userDetails[2];
                    String savedPassword = userDetails[3];

                    if (savedUsername.equals(loginUsername) && savedPassword.equals(loginPassword)) {
                        isAuthenticated = true;
                        profilePictureFileName = userDetails[5];
                        break;
                    }
                }
            }

            reader.close();

            if (isAuthenticated) {
                Toast.makeText(login_screen.this, "Login successful", Toast.LENGTH_SHORT).show();
                assignUsername.putExtra("username", loginUsername);
                assignProfilePicture.putExtra("profilePictureFileName", profilePictureFileName);
                startActivity(openMainMenu);
            } else {
                Toast.makeText(login_screen.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                triesCounter--;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(login_screen.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
        }
    }*/

}
