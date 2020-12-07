package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class ArticulosConFotosYCategoria {
    @Embedded
    private Articulo articulo;
    @Relation(parentColumn = "idCategoria", entityColumn = "idCategoria")
    private Categoria categoria;
    @Relation(parentColumn = "idArticulo", entityColumn = "idArticulo")
    private List<Foto> fotos;



}
