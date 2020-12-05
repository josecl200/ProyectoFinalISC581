package edu.pucmm.isc581.applogin.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView login, register;
    Button requestPasswordButton;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.mailInputEditText);
        register = findViewById(R.id.registerTextView);
        login = findViewById(R.id.loginTextView);
        requestPasswordButton = findViewById(R.id.requestPasswordResetButton);

        register.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), RegisterActivity.class);
            startActivity(i);
        });

        login.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), LoginActivity.class);
            startActivity(i);
        });

        requestPasswordButton.setOnClickListener(v -> {
            String emailVal = email.getText().toString().trim();
            if(emailVal.isEmpty() || !emailVal.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$")){
                email.setError("Debe proveer un email valido");
            } else {
                Singleton singleton = Singleton.getInstance();
                String password = singleton.getDataBased(getApplicationContext()).getUsuarioDAO().getPasswordByEmail(emailVal);
                AlertDialog.Builder forgotPasswordResponse = new AlertDialog.Builder(v.getContext());
                if (password != null)
                    forgotPasswordResponse.setTitle("Contraseña de " + emailVal).setMessage(password).show();
                else
                    forgotPasswordResponse.setTitle("No encontramos su usuario").setMessage("Asegurese que este correo sea el correcto para su cuenta.").show();

            }
        });
    }
}