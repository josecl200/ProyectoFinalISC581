package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class OrdenConArticulos {
    @Embedded
    private Orden orden;
    @Relation(parentColumn = "idOrden", entityColumn = "idArticulo", associateBy = @Junction(OrdenesYArticulos.class))
    private List<Articulo> articulos;
}
