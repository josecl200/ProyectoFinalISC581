package edu.pucmm.isc581.applogin.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.activities.RegisterActivity;
import edu.pucmm.isc581.applogin.asyncTasks.UploadImages;
import edu.pucmm.isc581.applogin.dbDaos.CategoriaDAO;
import edu.pucmm.isc581.applogin.dbEntities.Categoria;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryCreateUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCreateUpdateFragment extends Fragment {


    private static final String ARG_PARAM1 = "modify";
    private static final String ARG_PARAM2 = "id";
    private static int RESULT_LOAD_IMAGE = 1;
    private boolean modifiedImage = false;

    ImageView categoryImage;
    EditText nombre;
    Button btnSave;

    private Boolean modify;
    private Long id;

    public CategoryCreateUpdateFragment() {}


    public static CategoryCreateUpdateFragment newInstance(Boolean modify, Integer id) {
        CategoryCreateUpdateFragment fragment = new CategoryCreateUpdateFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, modify);
        args.putLong(ARG_PARAM2, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modify = getArguments().getBoolean(ARG_PARAM1, false);
        id = getArguments().getLong(ARG_PARAM2, -1L);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_create_update, container, false);
        nombre = view.findViewById(R.id.categoryNameTextInput);
        categoryImage = view.findViewById(R.id.categoryImage);
        btnSave = view.findViewById(R.id.btnSave);
        Categoria categoria = new Categoria();
        Singleton singleton = Singleton.getInstance();
        CategoriaDAO categoriaDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCategoriaDAO();
        if (modify) {
            categoria = categoriaDAO.getCategoria(id);
            nombre.setText(categoria.getNombre());
            btnSave.setText("Actualizar");
            Glide.with(this).load(getString(R.string.BLOB_URL_BASE) + categoria.getFoto()).centerCrop().into(categoryImage);
        }

        Categoria finalCategoria = categoria;
        categoryImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)==0)
                loadImage();
            else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        });
        btnSave.setOnClickListener(v -> {
            if(nombre.getText().toString().isEmpty()){
                nombre.setError("Debe escribir un nombre para la categoria");
            } else {
                finalCategoria.setNombre(nombre.getText().toString().trim());
                if (modify) {
                    if (modifiedImage){
                        Map<String, Bitmap> fotoToUpload = new HashMap<>();
                        Bitmap bitmap = getImageBitmap(categoryImage.getDrawable());
                        fotoToUpload.put(finalCategoria.getIdCategoria().toString().concat(".category.jpg"), bitmap);
                        finalCategoria.setFoto(finalCategoria.getIdCategoria().toString().concat(".category.jpg"));
                        UploadImages uploadImages = new UploadImages();
                        uploadImages.execute(fotoToUpload, getActivity().getApplicationContext());
                    }
                } else {
                    finalCategoria.setIdCategoria(categoriaDAO.insertCategoria(finalCategoria));
                    Map<String, Bitmap> fotoToUpload = new HashMap<>();
                    Bitmap bitmap = getImageBitmap(categoryImage.getDrawable());
                    fotoToUpload.put(finalCategoria.getIdCategoria().toString().concat(".category.jpg"), bitmap);
                    finalCategoria.setFoto(finalCategoria.getIdCategoria().toString().concat(".category.jpg"));
                    UploadImages uploadImages = new UploadImages();
                    uploadImages.execute(fotoToUpload, getActivity().getApplicationContext());
                }
                categoriaDAO.updateCategoria(finalCategoria);
                Navigation.findNavController(v).navigateUp();
            }
        });
        return view;
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
            Toast.makeText(getActivity(), "Necessary permissions not granted.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf("requestCode", Integer.toString(requestCode));
        Log.wtf("resultCode", Integer.toString(resultCode));
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                final Uri imagen = data.getData();
                categoryImage.setImageURI(imagen);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadImage() {
        Intent storageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        storageIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(storageIntent, "Select Picture"), RESULT_LOAD_IMAGE);
    }
}