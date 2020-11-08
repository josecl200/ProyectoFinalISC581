package edu.pucmm.isc581.applogin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton;
    TextView forgotPasswordButton, registerButton;
    ProgressBar loginBulto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.mailInputEditText);
        password = findViewById(R.id.passInputEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordTextView);
        registerButton = findViewById(R.id.registerTextView);
        loginBulto = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(v -> {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            if (!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()){
                loginBulto.setVisibility(View.VISIBLE);
                Intent i = new Intent(v.getContext(), MainActivity.class);
                v.postDelayed(() -> {
                    startActivity(i);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            loginBulto.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);
                },3500);

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