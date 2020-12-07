package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.*;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeCompra;

import java.util.List;

@Dao
public interface CarritoDeDAO {
    @Insert
    public long insertCarritoDeCompras(CarritoDeCompra carritoDeCompra);

    @Update
    public void updateCarritoDeCompras(CarritoDeCompra carritoDeCompra);

    @Delete
    public void deleteCarritoDeCompras(CarritoDeCompra carritoDeCompra);

    @Query("SELECT COUNT(*) FROM CarritoDeCompra WHERE idArticulo = :idArt AND idUsuario = :idUser")
    public Integer ArticleExistsInMyCarrito(Long idArt, Long idUser);

    @Query("SELECT * FROM CarritoDeCompra WHERE idArticulo = :idArt AND idUsuario = :idUser")
    public CarritoDeCompra ArticleFromMyCarrito(Long idArt, Long idUser);

    @Query("SELECT * FROM CarritoDeCompra WHERE idUsuario = :idUser")
    public List<CarritoDeCompra> MyCarritoDeCompras(Long idUser);
}
