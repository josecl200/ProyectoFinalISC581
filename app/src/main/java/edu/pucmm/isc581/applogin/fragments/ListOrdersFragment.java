package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.FtsOptions;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.adapters.OrderListAdapter;
import edu.pucmm.isc581.applogin.dbDaos.OrdenDAO;

import java.util.List;


public class ListOrdersFragment extends Fragment {
    Singleton singleton = Singleton.getInstance();
    OrdenDAO ordenDAO;

    public ListOrdersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListOrdersFragment newInstance(String param1, String param2) {
        ListOrdersFragment fragment = new ListOrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordenDAO = singleton.getDataBased(getActivity().getApplicationContext()).getOrdenDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_orders, container, false);
        RecyclerView orderList = view.findViewById(R.id.ordenesRecyclerView);
        List<edu.pucmm.isc581.applogin.dbEntities.Orden> ordenList = ordenDAO.getOrdenesFromUsuario(singleton.getLoggedUser().getIdUsuario());
        OrderListAdapter orderListAdapter = new OrderListAdapter(ordenList, getContext());
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList.setAdapter(orderListAdapter);
        return view;
    }
}