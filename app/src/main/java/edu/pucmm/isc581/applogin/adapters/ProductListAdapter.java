package edu.pucmm.isc581.applogin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbDaos.CarritoDeDAO;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    List<ArticulosConFotosYCategoria> listaProductos;
    private Context mContext;
    boolean latest;


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent ,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(listaProductos.get(position), position ,mContext, this);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView   imagenProducto;
        public ImageButton botonMenu;
        public TextView    nombreProducto;
        public TextView    precioProducto;
        public TextView    descripcionProducto;
        Singleton singleton = Singleton.getInstance();
        ArticuloDAO articuloDao;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenProducto = itemView.findViewById(R.id.productImageView);
            botonMenu = itemView.findViewById(R.id.manageProductoButton);
            nombreProducto = itemView.findViewById(R.id.nameTextView);
            precioProducto = itemView.findViewById(R.id.priceTextView);
            descripcionProducto = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bindData(ArticulosConFotosYCategoria articulosConFotosYCategoria, int position, Context context, ProductListAdapter productListAdapter) {
            Glide.with(context).load(context.getString(R.string.BLOB_URL_BASE) + articulosConFotosYCategoria.getFotos().get(0).getLinkImagen()).into(imagenProducto);
            nombreProducto.setText(articulosConFotosYCategoria.getArticulo().getNombre());
            imagenProducto.setOnClickListener(v -> {
                Bundle args = new Bundle();
                CarritoDeDAO carritoDeDAO = singleton.getDataBased(context).getCarritoDeDAO();
                args.putLong("idArticulo", articulosConFotosYCategoria.getArticulo().getIdArticulo());
                args.putInt("cantCarrito", carritoDeDAO.ArticleFromMyCarrito(articulosConFotosYCategoria.getArticulo().getIdArticulo(), singleton.getLoggedUser().getIdUsuario())!=null ? carritoDeDAO.ArticleFromMyCarrito(articulosConFotosYCategoria.getArticulo().getIdArticulo(), singleton.getLoggedUser().getIdUsuario()).getCantidad() : 1);
                if (productListAdapter.latest)
                    Navigation.findNavController(v).navigate(R.id.action_nav_latest_to_details, args);
                else
                    Navigation.findNavController(v).navigate(R.id.action_nav_list_article_to_details, args);
            });
            precioProducto.setText(articulosConFotosYCategoria.getArticulo().getPrecio().toString());
            descripcionProducto.setText(articulosConFotosYCategoria.getArticulo().getDescripcion());
            botonMenu.setOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.producto_tuerca);
                menu.setOnMenuItemClickListener( item -> {
                    switch (item.getItemId()){
                        case R.id.update_product:
                            Bundle bundle = new Bundle();
                            bundle.putLong("id", articulosConFotosYCategoria.getArticulo().getIdArticulo());
                            bundle.putBoolean("modify", true);
                            if (productListAdapter.latest) {
                                Navigation.findNavController(v).navigate(R.id.action_nav_latest_to_create_article, bundle);
                            } else {
                                Navigation.findNavController(v).navigate(R.id.action_nav_list_article_to_create_article, bundle);
                            }
                            return true;
                        case R.id.delete_product:
                            new android.app.AlertDialog.Builder(context).setTitle("Eliminar Producto").setMessage("Seguro que quiere eliminar este producto?")
                                    .setPositiveButton("Si", (dialogInterface, i1) -> {
                                       articuloDao = singleton.getDataBased(context).getArticuloDAO();
                                       articuloDao.deleteArticulo(articulosConFotosYCategoria.getArticulo());
                                       productListAdapter.listaProductos.remove(position);
                                       productListAdapter.notifyItemRemoved(position);
                                       productListAdapter.notifyItemRangeChanged(position, productListAdapter.listaProductos.size());
                                    }).show();
                    }
                    return false;
                });
                menu.show();
            });
        }
    }
}
