package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.adapters.OsListAdapter;
import edu.pucmm.isc581.applogin.dataModels.OsDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OsFragment extends Fragment {


    public OsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OsFragment newInstance(String param1, String param2) {
        OsFragment fragment = new OsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_os, container, false);
        RecyclerView osRecycler = view.findViewById(R.id.osList);
        List<OsDataModel> dataModelList = new ArrayList<>();
        fillOsList(dataModelList);
        RecyclerView.LayoutManager osLayoutManager = new LinearLayoutManager(this.getContext());
        osRecycler.setLayoutManager(osLayoutManager);
        OsListAdapter osAdapter = new OsListAdapter(dataModelList, this.getContext());
        osRecycler.setAdapter(osAdapter);

        return view;
    }

    public void fillOsList(List<OsDataModel> list){
        list.add(new OsDataModel(Integer.valueOf(R.drawable.android), "NO NAME", "1-2"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.cupcake), "Cupcake", "3"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.donut), "Donut", "4"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.eclair), "Eclair", "5-7"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.froyo), "Froyo", "8"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.gingerbread), "Gingerbread", "9-10"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.honeycomb), "HoneyComb", "11-13"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.ics), "Ice Cream Sandwich", "14-15"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.jellybean), "Jelly Bean", "16-18"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.kitkat), "KitKat", "19-20"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.lollipop), "Lollipop", "21-22"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.marshmallow), "Marshmallow", "23"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.nougat), "Nougat", "24-25"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.oreo), "Oreo", "26-27"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.pie), "Pie", "28"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.q), "Android 10 (Q)", "29"));
        list.add(new OsDataModel(Integer.valueOf(R.drawable.android11), "Android 11", "30"));
    }
}

