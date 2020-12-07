package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;


public class ProfileFragment extends Fragment {
    ImageView profilePicture;
    Singleton singleton = Singleton.getInstance();
    TextView username;
    TextView email;
    TextView contact;
    TextView address;
    TextView secondaryAddress;
    Button btnEditar;

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profilePicture);
        username = view.findViewById(R.id.usernameTextView);
        email = view.findViewById(R.id.emailTextView);
        contact = view.findViewById(R.id.contactTextView);
        address = view.findViewById(R.id.addressTextView);
        secondaryAddress = view.findViewById(R.id.secondaryAddressTextView);
        btnEditar = view.findViewById(R.id.btnUpdate);

        Glide.with(this).load(getString(R.string.BLOB_URL_BASE) + singleton.getLoggedUser().getImagen()).centerCrop().into(profilePicture);
        username.setText(singleton.getLoggedUser().getUsername());
        email.setText(singleton.getLoggedUser().getEmail());
        contact.setText(singleton.getLoggedUser().getTelefono());
        address.setText(singleton.getLoggedUser().getDireccion());
        secondaryAddress.setText(singleton.getLoggedUser().getDireccion2());

        btnEditar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_nav_to_editProfile);
        });

        return view;
    }
}