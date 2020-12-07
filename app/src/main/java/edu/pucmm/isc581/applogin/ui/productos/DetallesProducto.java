package edu.pucmm.isc581.applogin.ui.productos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbDaos.CarritoDeDAO;
import edu.pucmm.isc581.applogin.dbEntities.Articulo;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesProducto extends Fragment {
    private static final String ARG_PARAM1 = "idArticulo";
    private static final String ARG_PARAM2 = "cantCarrito";


    // TODO: Rename and change types of parameters
    private Long idArticulo;
    private Singleton singleton = Singleton.getInstance();
    private ArticuloDAO articuloDAO;
    private CarritoDeDAO carritoDeDAO;
    private Integer cantCarrito;
    TextView nombre, desc, precio, cantidad;
    CarouselView carrusel;
    Button addCant, remCant, mandarAlCarrito;

    public DetallesProducto() {}

    public static DetallesProducto newInstance(Long idArticulo, Integer cantCarrito) {
        DetallesProducto fragment = new DetallesProducto();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, idArticulo);
        args.putInt(ARG_PARAM2, cantCarrito);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idArticulo = getArguments().getLong(ARG_PARAM1);
            cantCarrito = getArguments().getInt(ARG_PARAM2, 1);
        }
        articuloDAO = singleton.getDataBased(getActivity().getApplicationContext()).getArticuloDAO();
        carritoDeDAO = singleton.getDataBased(getActivity().getApplicationContext()).getCarritoDeDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_producto, container, false);
        ArticulosConFotosYCategoria articuloCompleto = articuloDAO.getArticulo(idArticulo);

        carrusel = view.findViewById(R.id.carouselView);
        carrusel.setPageCount(articuloCompleto.getFotos().size());
        carrusel.setImageListener((pos, imageView) ->
            Glide.with(getContext()).load(getString(R.string.BLOB_URL_BASE) + articuloCompleto.getFotos().get(pos).getLinkImagen()).into(imageView)
        );

        nombre = view.findViewById(R.id.prod_title);
        nombre.setText(articuloCompleto.getArticulo().getNombre());
        desc = view.findViewById(R.id.Prod_desc);
        desc.setText(articuloCompleto.getArticulo().getDescripcion());
        precio = view.findViewById(R.id.Precio_objeto);
        precio.setText(articuloCompleto.getArticulo().getPrecio().toString());
        cantidad = view.findViewById(R.id.textQuantity);
        cantidad.setText(cantCarrito.toString());
        addCant = view.findViewById(R.id.buttonPlusArt);
        remCant = view.findViewById(R.id.buttonMinusArt);
        mandarAlCarrito = view.findViewById(R.id.buttonAddToCart);
        if (cantCarrito == 1)
            remCant.setEnabled(false);

        addCant.setOnClickListener(v -> {
            cantCarrito++;
            cantidad.setText(cantCarrito.toString());
            if (!remCant.isEnabled())
                remCant.setEnabled(true);
        });

        remCant.setOnClickListener(v -> {
            cantCarrito--;
            cantidad.setText(cantCarrito.toString());
            if (cantCarrito == 1)
                remCant.setEnabled(false);
        });

        mandarAlCarrito.setOnClickListener(v -> {
            Long user = singleton.getLoggedUser().getIdUsuario();
            if (carritoDeDAO.ArticleExistsInMyCarrito(idArticulo, user) > 0){
                CarritoDeCompra articuloEnMiCarrito = carritoDeDAO.ArticleFromMyCarrito(idArticulo, user);
                articuloEnMiCarrito.setCantidad(cantCarrito);
                carritoDeDAO.updateCarritoDeCompras(articuloEnMiCarrito);
                Toast.makeText(getContext(), "Articulo actualizado en su carrito", Toast.LENGTH_SHORT).show();
            } else {
                CarritoDeCompra nuevoArticuloEnCarrito = new CarritoDeCompra();
                nuevoArticuloEnCarrito.setIdArticulo(idArticulo);
                nuevoArticuloEnCarrito.setIdUsuario(user);
                nuevoArticuloEnCarrito.setCantidad(cantCarrito);
                carritoDeDAO.insertCarritoDeCompras(nuevoArticuloEnCarrito);
                Toast.makeText(getContext(), "Articulo agregado a su carrito", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }



}