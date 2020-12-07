package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.pucmm.isc581.applogin.dbEntities.OrdenesYArticulos;

import java.util.List;

@Dao
public interface OrdenesYArticulosDAO {
    @Insert
    public void insertOrdenYArticulo(OrdenesYArticulos ordenesYArticulos);

    @Query("SELECT * FROM OrdenesYArticulos WHERE idOrden = :idOrden")
    public List<OrdenesYArticulos> ordenesYArticulosByOrden(long idOrden);
}
