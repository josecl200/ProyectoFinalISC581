package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;

@Dao
public interface CarritoDeDAO {
    @Insert
    public long insertCarritoDeCompras(CarritoDeCompra carritoDeCompra);

    @Update
    public void updateCarritoDeCompras(CarritoDeCompra carritoDeCompra);

    @Query("SELECT COUNT(*) FROM CarritoDeCompra WHERE idArticulo = :idArt AND idUsuario = :idUser")
    public Integer ArticleExistsInMyCarrito(Long idArt, Long idUser);

    @Query("SELECT * FROM CarritoDeCompra WHERE idArticulo = :idArt AND idUsuario = :idUser")
    public CarritoDeCompra ArticleFromMyCarrito(Long idArt, Long idUser);
}
