package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Categoria {
    private Long idCategoria;
    private String nombre;
}
