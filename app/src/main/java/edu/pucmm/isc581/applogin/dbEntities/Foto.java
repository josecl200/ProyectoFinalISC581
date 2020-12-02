package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Foto {
    private Long idFoto;
    private String linkImagen;
    private Long idArticulo;
}
