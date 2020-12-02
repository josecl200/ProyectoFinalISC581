package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(primaryKeys = {"idUsuario", "idArticulo"}) @Data @AllArgsConstructor @NoArgsConstructor
public class CarritoDeCompra {
    private Long idUsuario;
    private Long idArticulo;
    private Integer cantidad;
}
