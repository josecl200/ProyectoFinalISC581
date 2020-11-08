package edu.pucmm.isc581.applogin.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton;
    TextView forgotPasswordButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.mailInputEditText);
        password = findViewById(R.id.passInputEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordTextView);
        registerButton = findViewById(R.id.registerTextView);

        loginButton.setOnClickListener(v -> {
            if (!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()){
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(v.getContext(), "Bad credentials", Toast.LENGTH_LONG).show();
            }
        });
        registerButton.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RegisterActivity.class);
            startActivity(i);
        });

        forgotPasswordButton.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ForgotPasswordActivity.class);
            startActivity(i);
        });
    }
}