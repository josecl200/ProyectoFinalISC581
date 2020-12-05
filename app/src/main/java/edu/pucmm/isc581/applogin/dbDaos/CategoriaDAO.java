package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.*;
import edu.pucmm.isc581.applogin.dbEntities.Categoria;

import java.util.List;

@Dao
public interface CategoriaDAO {
    @Insert
    public long insertCategoria(Categoria categoria);

    @Update
    public void updateCategoria(Categoria categoria);

    @Delete
    public void deleteCategoria(Categoria categoria);

    @Query("SELECT * FROM CATEGORIA")
    public List<Categoria> getCategorias();



}
