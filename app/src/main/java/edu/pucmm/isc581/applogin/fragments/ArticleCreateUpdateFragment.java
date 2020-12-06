package edu.pucmm.isc581.applogin.fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.asyncTasks.UploadImages;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbDaos.CategoriaDAO;
import edu.pucmm.isc581.applogin.dbDaos.FotosDAO;
import edu.pucmm.isc581.applogin.dbEntities.Articulo;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;
import edu.pucmm.isc581.applogin.dbEntities.Categoria;
import edu.pucmm.isc581.applogin.dbEntities.Foto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ArticleCreateUpdateFragment extends Fragment {

    private static final String ARG_PARAM1 = "modify";
    private static final String ARG_PARAM2 = "id";
    private static int RESULT_LOAD_IMAGES = 3;
    private static int RESULT_LOAD_IMAGE = 1;
    private boolean modifiedImage = false;
    private List<Uri> imageURIS;
    private Boolean modify;
    private Long id;

    EditText nombre, precio, descripcion;
    Spinner categoria;
    CarouselView images;
    Button btnSave, btnAddImage, btnReplaceImages;
    Singleton singleton = Singleton.getInstance();
    ArticuloDAO articuloDAO;
    CategoriaDAO categoriaDAO;
    FotosDAO fotosDAO;
    ArticulosConFotosYCategoria articuloCompleto;

    public ArticleCreateUpdateFragment() {}


    public static ArticleCreateUpdateFragment newInstance(Boolean modify, Integer id) {
        ArticleCreateUpdateFragment fragment = new ArticleCreateUpdateFragment();
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
        articuloDAO = singleton.getDataBased(getActivity().getApplicationContext()).getArticuloDAO();
        categoriaDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCategoriaDAO();
        fotosDAO = singleton.getDataBased(getActivity().getApplicationContext()).getFotosDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_article_create_update, container, false);

        Log.wtf("Articulos", articuloDAO.getArticulos().toString());
        nombre = view.findViewById(R.id.nombreArtEditText);
        precio = view.findViewById(R.id.precioArtEditText);
        descripcion = view.findViewById(R.id.descArtEditText);
        categoria = view.findViewById(R.id.categorySpinner);
        images = view.findViewById(R.id.articleImages);
        btnSave = view.findViewById(R.id.btnSave);
        btnReplaceImages = view.findViewById(R.id.btnReplaceImages);

        ArrayList<Categoria> categoriaArrayList = (ArrayList<Categoria>) categoriaDAO.getCategorias();
        ArrayList<String> categoriaNameArrayList = (ArrayList<String>) categoriaDAO.getNombreCategorias();
        ArrayAdapter categoriaArrayAdapter = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, categoriaNameArrayList);
        categoria.setAdapter(categoriaArrayAdapter);

        btnReplaceImages.setOnClickListener( v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == 0)
                loadImages();
            else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        });

        btnSave.setOnClickListener( v -> {
            boolean valid = true;

            if(nombre.getText().toString().isEmpty()){
                valid = false;
                nombre.setError("Debe escribir un nombre");
            }

            if(precio.getText().toString().isEmpty()){
                valid = false;
                nombre.setError("Debe ponerle un precio");
            }

            if(descripcion.getText().toString().isEmpty()){
                valid = false;
                nombre.setError("Debe escribir una descripcion");
            }

            if(valid){
                if (modify) {
                    Articulo art = articuloCompleto.getArticulo();
                    art.setNombre(nombre.getText().toString());
                    art.setDescripcion(descripcion.getText().toString());
                    art.setPrecio(Double.parseDouble(precio.getText().toString()));
                    art.setIdCategoria(categoriaArrayList.get(categoria.getSelectedItemPosition()).getIdCategoria());
                    articuloDAO.updateArticulo(art);
                    if (modifiedImage){
                        fotosDAO.deleteFotos(articuloCompleto.getFotos());
                        HashMap<String, Bitmap> fotoToUpload = new HashMap<>();
                        for (int i = 0; i < imageURIS.size(); i++) {
                            try {
                                String nombreImagen = art.getIdArticulo().toString() + "." + Integer.toString(i) + ".articulo.jpg";
                                fotoToUpload.put(nombreImagen, MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageURIS.get(i)));
                                Foto foto = new Foto();
                                foto.setIdArticulo(art.getIdArticulo());
                                foto.setLinkImagen(nombreImagen);
                                fotosDAO.insertFoto(foto);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        UploadImages uploadImages = new UploadImages();
                        uploadImages.execute(fotoToUpload, getActivity().getApplicationContext());
                    }
                } else {
                    Articulo art = new Articulo();
                    art.setNombre(nombre.getText().toString());
                    art.setDescripcion(descripcion.getText().toString());
                    art.setPrecio(Double.parseDouble(precio.getText().toString()));
                    art.setIdCategoria(categoriaArrayList.get(categoria.getSelectedItemPosition()).getIdCategoria());
                    art.setFechaCreacion(new Date());
                    art.setIdArticulo(articuloDAO.insertArticulo(art));
                    HashMap<String, Bitmap> fotoToUpload = new HashMap<>();
                    for (int i = 0; i < imageURIS.size(); i++) {
                        try {
                            String nombreImagen = art.getIdArticulo().toString() + "." + Integer.toString(i) + ".articulo.jpg";
                            fotoToUpload.put(nombreImagen, MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageURIS.get(i)));
                            Foto foto = new Foto();
                            foto.setIdArticulo(art.getIdArticulo());
                            foto.setLinkImagen(nombreImagen);
                            fotosDAO.insertFoto(foto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    UploadImages uploadImages = new UploadImages();
                    uploadImages.execute(fotoToUpload, getActivity().getApplicationContext());
                }
            }

        });

        if (modify) {
            articuloCompleto = articuloDAO.getArticulo(id);
            images.setPageCount(articuloCompleto.getFotos().size());
            images.setImageListener((position, imageView) ->
                    Glide.with(view).load( getString(R.string.BLOB_URL_BASE) + articuloCompleto.getFotos().get(position).getLinkImagen()).placeholder(R.drawable.product_image_thumbnail_placeholder).centerCrop().error(R.drawable.product_image_thumbnail_placeholder).into(imageView));
            images.setPageCount(articuloCompleto.getFotos().size());
            nombre.setText(articuloCompleto.getArticulo().getNombre());
            precio.setText(articuloCompleto.getArticulo().getPrecio().toString());
            descripcion.setText(articuloCompleto.getArticulo().getDescripcion());
            categoria.setSelection(categoriaArrayList.indexOf(articuloCompleto.getCategoria()));
        } else {
            images.setPageCount(1);
            images.setImageListener((position, imageView) ->
                    imageView.setImageDrawable(getContext().getDrawable(R.drawable.product_image_thumbnail_placeholder)));
            images.setPageCount(1);
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            loadImages();
        else
            Toast.makeText(getActivity(), "Necessary permissions not granted.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGES && resultCode == RESULT_OK && data != null){
            try {
                if( data.getClipData() != null) {
                    imageURIS = new ArrayList<>();
                    modifiedImage = true;
                    images.setPageCount(data.getClipData().getItemCount());
                    images.setImageListener((position, imageView) -> {
                        imageView.setImageURI(data.getClipData().getItemAt(position).getUri());
                        imageURIS.add(data.getClipData().getItemAt(position).getUri());
                    });
                    images.setPageCount(data.getClipData().getItemCount());

                }else {
                    modifiedImage = true;
                    images.setPageCount(1);
                    imageURIS = new ArrayList<>();
                    images.setImageListener((position, imageView) -> {
                        imageView.setImageURI(data.getData());
                        imageURIS.add(data.getData());
                    });
                    images.setPageCount(1);
                }
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadImages() {
        Intent storageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        storageIntent.setType("image/*");
        storageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(storageIntent, "Select Pictures"), RESULT_LOAD_IMAGES);
    }

}