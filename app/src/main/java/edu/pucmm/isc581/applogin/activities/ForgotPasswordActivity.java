package edu.pucmm.isc581.applogin.activities;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        register = findViewById(R.id.registerTextView);
        login = findViewById(R.id.loginTextView);

        register.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RegisterActivity.class);
            startActivity(i);
        });

        login.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), LoginActivity.class);
            startActivity(i);
        });
    }
}