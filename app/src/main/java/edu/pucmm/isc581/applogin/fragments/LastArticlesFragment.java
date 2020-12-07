package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.adapters.ProductListAdapter;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LastArticlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LastArticlesFragment extends Fragment {

    private Singleton singleton = Singleton.getInstance();
    private ArticuloDAO articuloDAO;

    public LastArticlesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LastArticlesFragment.
     */
    public static LastArticlesFragment newInstance(String param1, String param2) {
        LastArticlesFragment fragment = new LastArticlesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articuloDAO = singleton.getDataBased(getActivity().getApplicationContext()).getArticuloDAO();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_articles, container, false);
        RecyclerView productView = view.findViewById(R.id.latestArticlesRecyclerView);
        ProductListAdapter productListAdapter = new ProductListAdapter(articuloDAO.getLatestArticulos(), getContext(), true);
        productView.setAdapter(productListAdapter);
        productView.setLayoutManager(new GridLayoutManager(getContext(),1));
        return view;
    }
}