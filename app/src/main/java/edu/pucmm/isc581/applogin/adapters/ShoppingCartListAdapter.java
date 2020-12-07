package edu.pucmm.isc581.applogin.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbDaos.CarritoDeDAO;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ShoppingCartListAdapter extends RecyclerView.Adapter<ShoppingCartListAdapter.ShoppingCartViewHolder> {

    List<CarritoDeCompra> listaProductos;
    private Context mContext;


    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_list, parent ,false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        holder.bindData(listaProductos.get(position), position ,mContext, this);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        public ImageView   imagenProducto;
        public TextView    nombreProducto;
        public TextView    precioProducto;
        public TextView    cantidadProducto;
        public Button add, subs, delete;
        Singleton singleton = Singleton.getInstance();


        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenProducto      = itemView.findViewById(R.id.productImage);
            nombreProducto      = itemView.findViewById(R.id.productName);
            precioProducto      = itemView.findViewById(R.id.productPrice);
            cantidadProducto    = itemView.findViewById(R.id.cantProd);
            add                 = itemView.findViewById(R.id.btnAddCant);
            subs                = itemView.findViewById(R.id.btnSubsCant);
            delete              = itemView.findViewById(R.id.deleteButton);
        }

        public void bindData(CarritoDeCompra carritoDeCompra, int position, Context context, ShoppingCartListAdapter productListAdapter) {
            ArticuloDAO articuloDao = singleton.getDataBased(context).getArticuloDAO();
            CarritoDeDAO carritoDeDAO = singleton.getDataBased(context).getCarritoDeDAO();
            ArticulosConFotosYCategoria articulosConFotosYCategoria = articuloDao.getArticulo(carritoDeCompra.getIdArticulo());
            Glide.with(context).load(context.getString(R.string.BLOB_URL_BASE) + articulosConFotosYCategoria.getFotos().get(0).getLinkImagen()).into(imagenProducto);
            nombreProducto.setText(articulosConFotosYCategoria.getArticulo().getNombre());
            cantidadProducto.setText(carritoDeCompra.getCantidad().toString());
            imagenProducto.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putLong("idArticulo", carritoDeCompra.getIdArticulo());
                args.putInt("cantCarrito", carritoDeCompra.getCantidad());
                Navigation.findNavController(v).navigate(R.id.action_nav_shopping_cart_to_details, args);
            });
            precioProducto.setText(articulosConFotosYCategoria.getArticulo().getPrecio().toString());
            if(carritoDeCompra.getCantidad() == 1)
                subs.setEnabled(false);
            add.setOnClickListener(v -> {
                carritoDeCompra.setCantidad(carritoDeCompra.getCantidad() + 1);
                if (!subs.isEnabled())
                    subs.setEnabled(true);
                carritoDeDAO.updateCarritoDeCompras(carritoDeCompra);
                cantidadProducto.setText(carritoDeCompra.getCantidad().toString());
            });
            subs.setOnClickListener(v -> {
                carritoDeCompra.setCantidad(carritoDeCompra.getCantidad() -1);
                if (carritoDeCompra.getCantidad() == 1)
                    subs.setEnabled(false);
                carritoDeDAO.updateCarritoDeCompras(carritoDeCompra);
                cantidadProducto.setText(carritoDeCompra.getCantidad().toString());
            });
            delete.setOnClickListener(v -> {
                productListAdapter.listaProductos.remove(position);
                productListAdapter.notifyItemRemoved(position);
                productListAdapter.notifyItemRangeChanged(position, productListAdapter.listaProductos.size());
                carritoDeDAO.deleteCarritoDeCompras(carritoDeCompra);
            });
        }
    }
}
