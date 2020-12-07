package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.adapters.ShoppingCartListAdapter;
import edu.pucmm.isc581.applogin.dbDaos.CarritoDeDAO;


public class ShoppingCartFragment extends Fragment {
    private Singleton singleton = Singleton.getInstance();
    private CarritoDeDAO carritoDeDAO;


    public ShoppingCartFragment() {
    }

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carritoDeDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCarritoDeDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        RecyclerView shoppingCartList = view.findViewById(R.id.shoopingCartRecyclerView);
        ShoppingCartListAdapter shoppingCartListAdapter = new ShoppingCartListAdapter(carritoDeDAO.MyCarritoDeCompras(singleton.getLoggedUser().getIdUsuario()), getContext());
        shoppingCartList.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingCartList.setAdapter(shoppingCartListAdapter);

        return view;
    }
}