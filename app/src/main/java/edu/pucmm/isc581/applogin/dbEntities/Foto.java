package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Foto {
    @PrimaryKey
    private Long idFoto;
    private String linkImagen;
    private Long idArticulo;
}
