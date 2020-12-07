package edu.pucmm.isc581.applogin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.Singleton;
import edu.pucmm.isc581.applogin.dataModels.OsDataModel;
import edu.pucmm.isc581.applogin.dbDaos.ArticuloDAO;
import edu.pucmm.isc581.applogin.dbDaos.CategoriaDAO;
import edu.pucmm.isc581.applogin.dbEntities.Articulo;
import edu.pucmm.isc581.applogin.dbEntities.Categoria;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private List<Categoria> categorias;
    private Context mContext;



    @NonNull @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bindData(categorias.get(position), position, mContext, this);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagenCategoria;
        public ImageButton botonMenu;
        public TextView nombreCategoria;
        public CardView categoryCard;
        Singleton singleton = Singleton.getInstance();
        ArticuloDAO articuloDAO;
        CategoriaDAO categoriaDAO;

        public CategoryViewHolder(View itemView){
            super(itemView);
            imagenCategoria = itemView.findViewById(R.id.imagenCategoria);
            botonMenu       = itemView.findViewById(R.id.manageCategoriaButton);
            nombreCategoria = itemView.findViewById(R.id.nombreCategoria);
            categoryCard    = itemView.findViewById(R.id.categoryCard);
        }

        public void bindData(Categoria categoria, int position, Context context, CategoryListAdapter adapter){
            Glide.with(context).load(context.getString(R.string.BLOB_URL_BASE) + categoria.getFoto()).into(imagenCategoria);
            nombreCategoria.setText(categoria.getNombre());
            botonMenu.setOnClickListener( v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.category_tuerca);
                menu.setOnMenuItemClickListener( i -> {
                    switch (i.getItemId()){
                        case R.id.update_category:
                            Bundle bundle = new Bundle();
                            bundle.putLong("id", categoria.getIdCategoria());
                            bundle.putBoolean("modify", true);
                            Navigation.findNavController(v).navigate(R.id.action_nav_list_category_to_create_category2, bundle);
                            return true;
                        case R.id.delete_category:
                            articuloDAO = singleton.getDataBased(context).getArticuloDAO();
                            if (articuloDAO.cantArticulosConCategoria(categoria.getIdCategoria()) > 0)
                                new AlertDialog.Builder(context).setTitle("Error").setMessage("No puede borrar esta categoria, ya que tiene productos asociados a ella").show();
                            else
                                new AlertDialog.Builder(context).setTitle("Borrar categoria").setMessage("Está seguro de que quiere borrar esta categoria? Esta acción no es reversible")
                                        .setPositiveButton("Si", (dialogInterface, i1) -> {
                                            categoriaDAO = singleton.getDataBased(context).getCategoriaDAO();
                                            categoriaDAO.deleteCategoria(categoria);
                                            adapter.categorias.remove(position);
                                            adapter.notifyItemRemoved(position);
                                            adapter.notifyItemRangeChanged(position, adapter.categorias.size());
                                        }).show();
                    }
                    return false;
                });
                menu.show();
            });
        }
    }
}
