package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Articulo {
    private Long idArticulo;
    private String nombre;
    private Double precio;
    private String descripcion;
    private Long idCategoria;
}
