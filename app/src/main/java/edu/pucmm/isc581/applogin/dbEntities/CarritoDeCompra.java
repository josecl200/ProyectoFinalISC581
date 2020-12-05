package edu.pucmm.isc581.applogin.dbEntities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(primaryKeys = {"idUsuario", "idArticulo"}) @Data @NoArgsConstructor
public class CarritoDeCompra {
    @NonNull
    private Long idUsuario;
    @NonNull
    private Long idArticulo;
    private Integer cantidad;
}
