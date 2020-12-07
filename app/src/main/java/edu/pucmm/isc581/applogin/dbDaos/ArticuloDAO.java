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

    @Transaction @Query("SELECT * FROM Articulo WHERE idArticulo = :id")
    public ArticulosConFotosYCategoria getArticulo(Long id);

    @Insert
    public long insertArticulo(Articulo articulo);

    @Transaction @Query("SELECT * FROM ARTICULO ORDER BY fechaCreacion DESC LIMIT 3")
    public List<ArticulosConFotosYCategoria> getLatestArticulos();

    @Update
    public void updateArticulo(Articulo articulo);

    @Query("SELECT COUNT(*) FROM ARTICULO WHERE idCategoria = :idCategoria")
    public Integer cantArticulosConCategoria(Long idCategoria);

    @Delete
    public void deleteArticulo(Articulo articulo);
}
