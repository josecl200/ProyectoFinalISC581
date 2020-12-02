package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CategoriaConArticulos {
    @Embedded
    private Categoria categoria;
    @Relation(parentColumn = "idCategoria", entityColumn = "idCategoria")
    private List<Articulo> articulos;
}
