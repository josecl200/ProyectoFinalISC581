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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.activities.RegisterActivity;
import edu.pucmm.isc581.applogin.adapters.CategoryListAdapter;
import edu.pucmm.isc581.applogin.dbDaos.CategoriaDAO;

public class CategoryListFragment extends Fragment {
    Singleton singleton = Singleton.getInstance();
    CategoriaDAO categoriaDAO;


    public CategoryListFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_category_list, container, false);
        categoriaDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCategoriaDAO();
        RecyclerView categoryView = view.findViewById(R.id.categoryRecyclerView);
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(categoriaDAO.getCategorias(), getContext());
        categoryView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryView.setAdapter(categoryListAdapter);
        FloatingActionButton createButton = view.findViewById(R.id.createCategoryButton);
        createButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_list_category_to_create_category2);
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}