package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Orden {
    @PrimaryKey
    private Long idOrden;
    private Date fechaCompra;
    private Long idUsuario;
}
