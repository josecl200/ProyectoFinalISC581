package edu.pucmm.isc581.applogin.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.activities.RegisterActivity;
import edu.pucmm.isc581.applogin.dbDaos.CategoriaDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {
    public CategoryListFragment() {
        // Required empty public constructor
    }


    public static CategoryListFragment newInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_list, container, false);
        //TODO: RecyclerView
        FloatingActionButton createButton = view.findViewById(R.id.createCategoryButton);
        createButton.setOnClickListener(v -> {
            Singleton singleton = Singleton.getInstance();
            CategoriaDAO categoriaDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCategoriaDAO();
            Log.wtf("Categorias: ", categoriaDAO.getCategorias().toString());
            Navigation.findNavController(v).navigate(R.id.action_nav_list_category_to_create_category2);
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}