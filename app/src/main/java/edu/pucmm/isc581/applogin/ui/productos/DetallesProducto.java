package edu.pucmm.isc581.applogin.ui.productos;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import edu.pucmm.isc581.applogin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesProducto extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetallesProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detalles_Producto.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallesProducto newInstance(String param1, String param2) {
        DetallesProducto fragment = new DetallesProducto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalles_producto, container, false);


        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        return view;

    }

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.android, R.drawable.cupcake, R.drawable.donut, R.drawable.eclair, R.drawable.froyo};

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void Aggregate_img(View view) {
    }

    public void Carrusel(View view) {

        String url = "picsum ";

        ImageView imageDetal = view.findViewById(R.id.detalle_imagen);

        Glide.with(this)
                .load(getString(R.string.BLOB_URL_BASE))
                .centerCrop()
                .into(imageDetal);

    }



}