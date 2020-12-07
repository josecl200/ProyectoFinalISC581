package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.UsuarioDAO;
import edu.pucmm.isc581.applogin.dbEntities.Articulo;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;

public class ProfileUpdateFragment extends Fragment {

    ImageView profilePicture;
    Singleton singleton = Singleton.getInstance();
    EditText username;
    EditText email;
    EditText contact;
    EditText address;
    EditText secondaryAddress;
    Button btnGuardar;
    UsuarioDAO usuarioDAO;

    public ProfileUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        username = view.findViewById(R.id.usernameEditText);
        email = view.findViewById(R.id.emailEditText);
        contact = view.findViewById(R.id.contactEditText);
        address = view.findViewById(R.id.addressEditText);
        secondaryAddress = view.findViewById(R.id.secondaryAddressEditText);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        usuarioDAO = singleton.getDataBased(getActivity().getApplicationContext()).getUsuarioDAO();
        //SETTING PREVIOUS INFORMATION
        username.setText(singleton.getLoggedUser().getUsername());
        email.setText(singleton.getLoggedUser().getEmail());
        contact.setText(singleton.getLoggedUser().getTelefono());
        address.setText(singleton.getLoggedUser().getDireccion());
        secondaryAddress.setText(singleton.getLoggedUser().getDireccion2());

        btnGuardar.setOnClickListener(v -> {

            boolean valid = true;

            if(username.getText().toString().isEmpty()){
                valid = false;
                username.setError("Debe de escribir su nombre de usuario.");
            }

            if(email.getText().toString().isEmpty()){
                valid = false;
                email.setError("Debe escribir su email.");
            }

            if(contact.getText().toString().isEmpty()){
                valid = false;
                contact.setError("Debe escribir su número de contacto.");
            }
            if(address.getText().toString().isEmpty()){
                valid = false;
                address.setError("Debe de escribir una dirección de shipping.");
            }
            if(valid){

                Usuario usuario = singleton.getLoggedUser();
                usuario.setUsername(username.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setTelefono(contact.getText().toString());
                usuario.setDireccion(address.getText().toString());
                usuario.setDireccion2(secondaryAddress.getText().toString());
                usuarioDAO.updateUser(usuario);
                Toast.makeText(getActivity(), "Usuario editado satisfactoriamente.", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigateUp();
            }


        });



        return view;
    }
}
