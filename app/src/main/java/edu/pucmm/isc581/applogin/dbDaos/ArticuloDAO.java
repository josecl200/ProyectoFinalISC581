package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.*;
import edu.pucmm.isc581.applogin.dbEntities.Articulo;
import edu.pucmm.isc581.applogin.dbEntities.ArticuloConFotos;
import edu.pucmm.isc581.applogin.dbEntities.ArticulosConFotosYCategoria;

import java.util.List;

@Dao
public interface ArticuloDAO {

    @Transaction @Query("SELECT * FROM Articulo")
    public List<ArticulosConFotosYCategoria> getArticulos();

    @Insert
    public long insertArticulo(Articulo articulo);

    @Transaction @Query("SELECT * FROM ARTICULO ORDER BY fechaCreacion DESC LIMIT 3")
    public List<ArticulosConFotosYCategoria> getLatestArticulos();

    @Update
    public void updateArticulo(Articulo articulo);

    @Delete
    public void deleteArticulo(Articulo articulo);
}
