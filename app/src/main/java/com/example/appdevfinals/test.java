package com.example.appdevfinals;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class test extends AppCompatActivity {

    private StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    private void listFilesInPath(String path) {
        StorageReference pathRef = storageRef.child(path);

        pathRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                // Iterate through each item
                for (StorageReference item : listResult.getItems()) {
                    // Get full path of each item
                    String fullPath = item.getPath();

                    // Display fullPath in a Toast message
                    Toast.makeText(test.this, "File path: " + fullPath, Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(test.this, "Failed to list files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndListFiles(String path) {
        StorageReference pathRef = storageRef.child(path);

        // Check if the path exists
        pathRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                // Path exists, list files
                listFilesInPath(path);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Path does not exist or some error occurred
                Toast.makeText(test.this, "Path does not exist: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}