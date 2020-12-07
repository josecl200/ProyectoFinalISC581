package edu.pucmm.isc581.applogin.adapters;

import android.content.Context;
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

import java.util.ArrayList;

import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    ArrayList<ArticulosConFotosYCategoria> listaProductos;
    private Context mContext;

    public ProductListAdapter(ArrayList<ArticulosConFotosYCategoria> listaProductos) {
        this.listaProductos = listaProductos;
    }

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
            Glide.with(context).load("https://temaepeciale.blob.core.windows.net/temaepeciale/" + articulosConFotosYCategoria.getFotos()).into(imagenProducto);
            nombreProducto.setText(articulosConFotosYCategoria.getArticulo().getNombre());
            botonMenu.setOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.producto_tuerca);
                menu.setOnMenuItemClickListener( item -> {
                    switch (item.getItemId()){
                        case R.id.update_product:
                            Bundle bundle = new Bundle();
                            bundle.putLong("id", articulosConFotosYCategoria.getArticulo().getIdArticulo());
                            bundle.putBoolean("modify", true);
                            Navigation.findNavController(v).navigate(R.id.action_nav_list_article_to_create_article, bundle);
                            return true;
                        case R.id.delete_product:
                           new AlertDialog.Builder(context).setTitle("Eliminar Producto").setMessage("Seguro que quiere eliminar este producto?");
                            new android.app.AlertDialog.Builder(context).setTitle("Borrar categoria").setMessage("Está seguro de que quiere borrar esta categoria? Esta acción no es reversible")
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
