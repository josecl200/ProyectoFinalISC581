package edu.pucmm.isc581.applogin.dbEntities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import com.google.android.material.appbar.AppBarLayout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(primaryKeys = {"idOrden", "idArticulo"}) @Data @NoArgsConstructor
public class OrdenesYArticulos {
    @NonNull
    private Long idOrden;
    @NonNull
    private Long idArticulo;
    private Integer cantidad;
}
