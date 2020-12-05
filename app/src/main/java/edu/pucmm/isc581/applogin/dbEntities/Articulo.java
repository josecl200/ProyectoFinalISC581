package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Articulo {
    @PrimaryKey
    private Long idArticulo;
    private String nombre;
    private Double precio;
    private String descripcion;
    private Long idCategoria;
    private Date fechaCreacion;
}
