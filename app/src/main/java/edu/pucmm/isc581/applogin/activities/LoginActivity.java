package edu.pucmm.isc581.applogin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.UsuarioDAO;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;

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
            boolean valid = true;
            String emailVal = email.getText().toString().trim();
            String passwordVal = password.getText().toString().trim();
            loginBulto.setVisibility(View.VISIBLE);
            if(emailVal.isEmpty() || !emailVal.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$")){
                email.setError("Debe proveer un email valido");
                valid = false;
            }
            if(passwordVal.isEmpty()){
                password.setError("Debe escribir una contraseña");
                valid = false;
            }

            if(!valid)
                loginBulto.setVisibility(View.INVISIBLE);
            else{
                Singleton singleton = Singleton.getInstance();
                UsuarioDAO usuarioDAO = singleton.getDataBased(getApplicationContext()).getUsuarioDAO();
                Usuario usuario = usuarioDAO.getUserForLogin(emailVal, passwordVal);
                if (usuario != null){
                    singleton.logUser(usuario);
                    Intent i = new Intent(v.getContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    loginBulto.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder.setTitle("Credenciales incorrectas");
                    alertDialogBuilder.setMessage("Sus credenciales no fueron encontradas, revise si el correo y la contraseña escrita son correctos");
                    alertDialogBuilder.show();
                }

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