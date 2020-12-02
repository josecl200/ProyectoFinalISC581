package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ArticuloConCategoria {
    @Embedded
    private Articulo articulo;
    @Relation(parentColumn = "idCategoria", entityColumn = "idArticulo")
    private Categoria categoria;
}
