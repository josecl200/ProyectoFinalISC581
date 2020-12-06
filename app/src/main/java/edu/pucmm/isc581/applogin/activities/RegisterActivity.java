package edu.pucmm.isc581.applogin.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.asyncTasks.UploadImages;
import edu.pucmm.isc581.applogin.dbDaos.UsuarioDAO;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    EditText name, username,email,password,confirmpass,contact;
    Bitmap bitmap;
    ImageView profileImage;
    TextView forgotPassword, login;
    Button btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profileImage = findViewById(R.id.profileImage);
        forgotPassword = findViewById(R.id.forgotPasswordTextView);
        login = findViewById(R.id.loginTextView);
        btnRegister = findViewById(R.id.btnRegister);
        name = findViewById(R.id.nameInputEditText);
        username = findViewById(R.id.usernameInputEditText);
        email =  findViewById(R.id.mailInputEditText);
        password = findViewById(R.id.passInputEditText);
        confirmpass = findViewById(R.id.passConfirmInputEditText);
        contact = findViewById(R.id.phoneInputEditText);


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

        btnRegister.setOnClickListener(v -> {

            Usuario usuario = new Usuario();

        if( passwordValidation(password, confirmpass) ){

            usuario.setName(name.getText().toString());
            usuario.setUsername(username.getText().toString());
            usuario.setEmail(email.getText().toString());
            usuario.setPassword(password.getText().toString());
            usuario.setTelefono(contact.getText().toString());

            //insert into db
            Singleton singleton = Singleton.getInstance();
            UsuarioDAO usuarioDAO = singleton.getDataBased(getApplicationContext()).getUsuarioDAO();
            usuario.setIdUsuario(usuarioDAO.registerUser(usuario));
            Map<String, Bitmap> fotoToUpload = new HashMap<>();
            Bitmap bitmap = getImageBitmap(profileImage.getDrawable());
            fotoToUpload.put(usuario.getIdUsuario().toString().concat(".profilePicture.jpg"), bitmap);
            usuario.setImagen(usuario.getIdUsuario().toString().concat(".profilePicture.jpg"));
            usuarioDAO.updateUser(usuario);
            UploadImages uploadImages = new UploadImages();
            uploadImages.execute(fotoToUpload, getApplicationContext());

            finish();
            Toast.makeText(RegisterActivity.this, "Account created.\n Please Log in.", Toast.LENGTH_LONG).show();
        }

        else{

            Toast.makeText(RegisterActivity.this, "Passwords are not the same.", Toast.LENGTH_LONG).show();
        }

    });

    }

    private boolean passwordValidation(EditText password, EditText confirmpass) {
        if(password.getText().toString().equals(confirmpass.getText().toString())){

            return true;
        }
        else{
            return false;
        }
    }

    private Bitmap getImageBitmap(Drawable drawable){
        Bitmap bitmap;
        if (drawable instanceof VectorDrawable){
            try {
                bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            } catch (OutOfMemoryError e) {
                Log.wtf("OUTOFMEMORYERROR: ", e.getMessage());
                return null;
            }
        }
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        return bitmap;

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