package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;

public class ProfileUpdateFragment extends Fragment {

    ImageView profilePicture;
    Singleton singleton = Singleton.getInstance();
    TextView username;
    TextView email;
    TextView contact;
    TextView address;
    TextView secondaryAddress;
    Button btnEditar;

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

        return view;
    }
}
