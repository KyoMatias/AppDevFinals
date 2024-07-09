package com.example.appdevfinals;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profile_screen extends AppCompatActivity {

//    private ImageView ivProfilePicture;
//    private Button btnUploadPicture, btnBack;
//    private String profilePictureFileName = null;
//    private String username;
//    private ActivityResultLauncher<Intent> chooseImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_screen);

//        TextView textViewFileContent = findViewById(R.id.textViewFileContent);
//        btnUploadPicture = findViewById(R.id.profile_upload_button);
//        btnBack = findViewById(R.id.profile_back_button);
//        ivProfilePicture = findViewById(R.id.profile_pfp_image_view);
//
//        Intent getLoggedInUsername = getIntent();
//        Intent getProfilePictureFileName = getIntent();
//        Intent backToMainMenu = new Intent(profile_screen.this, main_menu_screen.class);

        //Comes from the Login Sreen
//        username = getLoggedInUsername.getStringExtra("username");
//        profilePictureFileName = getProfilePictureFileName.getStringExtra("profilePictureFileName");
//
//        ivProfilePicture.setOnClickListener(v-> chooseImage());
//        btnUploadPicture.setOnClickListener(v -> appendImageNameToUserData());
//        btnBack.setOnClickListener(v -> startActivity(backToMainMenu));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //For chooseImage()
//        chooseImageLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            Uri selectedImageUri = data.getData();
//                            try {
//                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                                ivProfilePicture.setImageBitmap(bitmap);
//                                String pfpFileName = getFileNameFromUri(selectedImageUri);
//
//                                // Save the image to internal storage and get the file name
////                                profilePictureFileName = saveImageToInternalStorage(bitmap);
//                                appendImageNameToUserData(pfpFileName);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//
//        try {
//            FileInputStream fis = openFileInput("user_data.txt");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line).append("\n");
//            }
//            bufferedReader.close();
//            isr.close();
//            fis.close();
//
//            // Set the file content to the TextView
//            textViewFileContent.setText(stringBuilder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            textViewFileContent.setText("Error reading file.");
//        }

//        loadProfilePicture(profilePictureFileName);
    }

    //Methods
//    private void chooseImage() {
//        Intent chooseImage = new Intent();
//        chooseImage.setType("image/*");
//        chooseImage.setAction(Intent.ACTION_GET_CONTENT);
//        chooseImageLauncher.launch(Intent.createChooser(chooseImage, "Select Picture"));
//    }

//    private String saveImageToInternalStorage(Bitmap bitmap) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "IMG_" + timeStamp + ".jpg";
//        try (FileOutputStream fos = openFileOutput(imageFileName, MODE_PRIVATE)) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Image not saved!", Toast.LENGTH_SHORT).show();
//        }
//        return imageFileName;
//    }
//
//    private void appendImageNameToUserData(String fileName){
//        try {
//            FileInputStream fis = openFileInput("user_data.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//            StringBuilder fileContent = new StringBuilder();
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                String[] userDetails = line.split(",");
//                if (userDetails.length == 6 && userDetails[2].equals(username)) {
//                    userDetails[5] = fileName;
//                    line = String.join(",", userDetails);
//                    }
//                fileContent.append(line).append("\n");
//                }
//
//
//            reader.close();
//
//            // Write the updated content back to the file
//            FileOutputStream fos = openFileOutput("user_data.txt", MODE_PRIVATE);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
//            writer.write(fileContent.toString());
//            writer.close();
//
//            Toast.makeText(profile_screen.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(profile_screen.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private String getFileNameFromUri(Uri uri) {
//        String fileName = null;
//        // The scheme determines the type of URI
//        if (uri.getScheme().equals("content")) {
//            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
//                if (cursor != null && cursor.moveToFirst()) {
//                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    if (displayNameIndex != -1) {
//                        fileName = cursor.getString(displayNameIndex);
//                    }
//                }
//            }
//        } else if (uri.getScheme().equals("file")) {
//            fileName = new File(uri.getPath()).getName();
//        }
//        return fileName;
//    }
//
//    private void loadProfilePicture(String fileName) {
//        try {
//            FileInputStream fis = openFileInput(fileName);
//            Bitmap bitmap = BitmapFactory.decodeStream(fis);
//            ivProfilePicture.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



}