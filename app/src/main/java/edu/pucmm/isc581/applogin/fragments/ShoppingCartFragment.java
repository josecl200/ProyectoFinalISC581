package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
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
import edu.pucmm.isc581.applogin.dbDaos.OrdenDAO;
import edu.pucmm.isc581.applogin.dbDaos.OrdenesYArticulosDAO;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;
import edu.pucmm.isc581.applogin.dbEntities.Orden;
import edu.pucmm.isc581.applogin.dbEntities.OrdenesYArticulos;

import java.util.Date;


public class ShoppingCartFragment extends Fragment {
    private Singleton singleton = Singleton.getInstance();
    private CarritoDeDAO carritoDeDAO;
    private OrdenDAO ordenDAO;
    private OrdenesYArticulosDAO ordenesYArticulosDAO;


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
        ordenDAO = singleton.getDataBased(getActivity().getApplicationContext()).getOrdenDAO();
        ordenesYArticulosDAO = singleton.getDataBased(getActivity().getApplicationContext()).getOrdenesYArticulosDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        RecyclerView shoppingCartList = view.findViewById(R.id.shoopingCartRecyclerView);

        Button ordenar = view.findViewById(R.id.buyButton);
        ordenar.setOnClickListener(v -> {
            Orden orden = new Orden();
            orden.setFechaCompra(new Date());
            orden.setIdUsuario(singleton.getLoggedUser().getIdUsuario());
            orden.setIdOrden(ordenDAO.insertOrden(orden));
            for (CarritoDeCompra carritoDeCompra: carritoDeDAO.MyCarritoDeCompras(singleton.getLoggedUser().getIdUsuario())) {
                OrdenesYArticulos ordenesYArticulos = new OrdenesYArticulos();
                ordenesYArticulos.setIdOrden(orden.getIdOrden());
                ordenesYArticulos.setIdArticulo(carritoDeCompra.getIdArticulo());
                ordenesYArticulos.setCantidad(carritoDeCompra.getCantidad());
                ordenesYArticulosDAO.insertOrdenYArticulo(ordenesYArticulos);
                carritoDeDAO.deleteCarritoDeCompras(carritoDeCompra);
            }
            ShoppingCartListAdapter shoppingCartListAdapter = new ShoppingCartListAdapter(carritoDeDAO.MyCarritoDeCompras(singleton.getLoggedUser().getIdUsuario()), getContext());
            shoppingCartList.setAdapter(shoppingCartListAdapter);
            Toast.makeText(getContext(), "Su orden ha sido procesada", Toast.LENGTH_SHORT).show();
        });
        ShoppingCartListAdapter shoppingCartListAdapter = new ShoppingCartListAdapter(carritoDeDAO.MyCarritoDeCompras(singleton.getLoggedUser().getIdUsuario()), getContext());
        shoppingCartList.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingCartList.setAdapter(shoppingCartListAdapter);

        return view;
    }
}