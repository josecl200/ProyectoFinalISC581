package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import com.google.android.material.appbar.AppBarLayout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(primaryKeys = {"idOrden", "idArticulo"}) @Data @NoArgsConstructor @AllArgsConstructor
public class OrdenesYArticulos {
    private Long idOrden;
    private Long idArticulo;
    private Integer cantidad;
}
