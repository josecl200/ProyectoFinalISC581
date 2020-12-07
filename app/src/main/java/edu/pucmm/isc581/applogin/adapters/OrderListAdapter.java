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
import lombok.AllArgsConstructor;
import edu.pucmm.isc581.applogin.dbEntities.Orden;

import java.util.List;

@AllArgsConstructor
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    List<Orden> listaOrdenes;
    private Context mContext;


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_item, parent ,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bindData(listaOrdenes.get(position) ,mContext);

    }

    @Override
    public int getItemCount() {
        return listaOrdenes.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView numeroOrden;
        public TextView fechaOrdenada;
        public Button   detalles;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroOrden = itemView.findViewById(R.id.orderNumberText);
            fechaOrdenada = itemView.findViewById(R.id.dateOrderedText);
            detalles = itemView.findViewById(R.id.orderDetailsButton);
        }

        public void bindData(Orden orden, Context context) {
            numeroOrden.setText("Orden #" + orden.getIdOrden().toString());
            fechaOrdenada.setText(orden.getFechaCompra().toString());
        }
    }
}
