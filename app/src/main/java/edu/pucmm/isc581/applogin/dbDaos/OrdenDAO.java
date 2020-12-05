package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.*;
import edu.pucmm.isc581.applogin.dbEntities.Orden;
@Dao
public interface OrdenDAO {
    @Insert
    public long insertOrden(Orden orden);

    @Update
    public void updateOrden(Orden orden);

    @Delete
    public void deleteOrden(Orden orden);



}
