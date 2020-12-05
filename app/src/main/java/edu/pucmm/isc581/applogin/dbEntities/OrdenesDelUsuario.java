package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class OrdenesDelUsuario {
    @Embedded
    private Usuario usuario;
    @Relation(entity = Orden.class, parentColumn = "idUsuario", entityColumn = "idUsuario")
    private List<OrdenConArticulos> ordenes;
}
