package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import edu.pucmm.isc581.applogin.dbConverters.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity @Data @NoArgsConstructor @TypeConverters(DateConverter.class)
public class Orden {
    @PrimaryKey
    private Long idOrden;
    private Date fechaCompra;
    private Long idUsuario;
}
