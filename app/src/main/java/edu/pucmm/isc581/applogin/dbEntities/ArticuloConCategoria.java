package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ArticuloConCategoria {
    @Embedded
    private Articulo articulo;
    @Relation(parentColumn = "idCategoria", entityColumn = "idArticulo")
    private Categoria categoria;
}
