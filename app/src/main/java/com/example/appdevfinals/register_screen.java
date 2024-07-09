package com.example.appdevfinals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
public class register_screen extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername;
    private Button btnRegister;
    private TextView existaccount;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Create Account");
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        etEmail = findViewById(R.id.register_email_edit_text);
        etPassword = findViewById(R.id.register_password_edit_text);
        etUsername = findViewById(R.id.register_username_edit_text);

        btnRegister = findViewById(R.id.register_button);
        existaccount = findViewById(R.id.register_login_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String uname = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Invalid Email");
                    etEmail.setFocusable(true);
                } else if (pass.length() < 6) {
                    etPassword.setError("Length Must be greater than 6 character");
                    etPassword.setFocusable(true);
                } else {
                    registerUser(email, pass, uname);
                }
            }
        });

        existaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_screen.this, login_screen.class));
            }
        });

    }
    private void registerUser(String email, final String pass, final String uname) {
//        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    progressDialog.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("uid", uid);
                    hashMap.put("name", uname);
                    hashMap.put("onlineStatus", "online");
                    hashMap.put("profilePicture", "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);
                    Toast.makeText(register_screen.this, "Registered User " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(register_screen.this, main_menu_screen.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
//                    progressDialog.dismiss();
                    Toast.makeText(register_screen.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
                Toast.makeText(register_screen.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


/*    private void saveUserData() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String profilePicture = null;

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || email.isEmpty()) {
            // Show error message if any field is empty
            // For simplicity, we use a Toast message here
            Toast.makeText(register_screen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userData = firstName + "," + lastName + "," + username + "," +
                password + "," + email + ","  + profilePicture + "\n";

        try {
            FileOutputStream fos = openFileOutput("user_data.txt", MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(userData);
            writer.close();
            Toast.makeText(register_screen.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(register_screen.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void readUserData() {
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();

            // Display or use the data
            String fileContent = stringBuilder.toString();
            Toast.makeText(this, fileContent, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to read user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUserData() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            if (file.delete()) {
                // File deleted successfully
                Toast.makeText(this, "user_data.txt deleted!", Toast.LENGTH_SHORT).show();
            } else {
                // Failed to delete file
                Toast.makeText(this, "Failed to delete user_data.txt!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // File does not exist
            Toast.makeText(this, "user_data.txt does not exist!", Toast.LENGTH_SHORT).show();
        }
    }*/
}