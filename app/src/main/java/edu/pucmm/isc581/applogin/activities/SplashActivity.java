package edu.pucmm.isc581.applogin.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.UsuarioDAO;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Usuario usuario = new Usuario();
        usuario.setEmail("josecl200@hotmail.com");
        usuario.setDireccion("Allí");
        usuario.setImagen("1607191996.jpg");
        usuario.setPassword("password");
        usuario.setTelefono("8091234556");
        Singleton singleton = Singleton.getInstance();
        UsuarioDAO usuarioDAO = singleton.getDataBased(getApplicationContext()).getUsuarioDAO();
        //usuarioDAO.registerUser(usuario);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        },3500);


    }
}