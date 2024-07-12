package com.example.appdevfinals;

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

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login_screen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;
    FirebaseUser currentUser;
    private ProgressDialog loadingBar;

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

        etEmail = findViewById(R.id.email_edit_text);
        etPassword = findViewById(R.id.password_edit_text);

        mAuth = FirebaseAuth.getInstance();

        Intent openRegistrationScreen = new Intent(login_screen.this, register_screen.class);

        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.login_register_button);
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
//        loadingBar.setMessage("Logging In....");
//        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

//                    loadingBar.dismiss();
                FirebaseUser user = mAuth.getCurrentUser();

                if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                    String email1 = user.getEmail();
                    String uid = user.getUid();

                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email", email1);
                    hashMap.put("uid", uid);
                    hashMap.put("name", "");
                    hashMap.put("onlineStatus", "online");
                    hashMap.put("typingTo", "noOne");
                    hashMap.put("phone", "");
                    hashMap.put("image", "");
                    hashMap.put("cover", "");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    // store the value in Database in "Users" Node
                    DatabaseReference reference = database.getReference("Users");

                    // storing the value in Firebase
                    reference.child(uid).setValue(hashMap);
                }

                Intent openMainMenu = new Intent(login_screen.this, main_menu_screen.class);
                openMainMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(openMainMenu);
                finish();
            } else {
//                    loadingBar.dismiss();
                Toast.makeText(login_screen.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                loadingBar.dismiss();
                Toast.makeText(login_screen.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}
