package edu.pucmm.isc581.applogin.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import edu.pucmm.isc581.applogin.R;

import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    ImageView profileImage;
    TextView forgotPassword, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profileImage = findViewById(R.id.profileImage);
        forgotPassword = findViewById(R.id.forgotPasswordTextView);
        login = findViewById(R.id.loginTextView);

        profileImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==0)
                loadImage();
            else
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        });

        forgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ForgotPasswordActivity.class);
            startActivity(i);
        });

        login.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), LoginActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            loadImage();
        else
            Toast.makeText(RegisterActivity.this, "Necessary permissions not granted.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf("requestCode", Integer.toString(requestCode));
        Log.wtf("resultCode", Integer.toString(resultCode));
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                final Uri imagen = data.getData();
                profileImage = findViewById(R.id.profileImage);
                profileImage.setImageURI(imagen);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadImage() {
        Intent storageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        storageIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(storageIntent, "Select Picture"), RESULT_LOAD_IMAGE);

    }
}