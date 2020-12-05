package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.pucmm.isc581.applogin.dbEntities.Foto;

import java.util.List;

@Dao
public interface FotosDAO {

    @Insert
    public List<Long> insertFotos(List<Foto> fotos);

    @Insert
    public long insertFoto(Foto foto);

    @Query("SELECT * FROM Foto")
    public List<Foto> getAllFotos();

    @Query("SELECT * FROM Foto WHERE idFoto = :idFoto")
    public Foto getFoto(Long idFoto);

    @Query("SELECT * FROM Foto WHERE idArticulo = :idArt")
    public List<Foto> getFotosFromArticulo(Long idArt);


}
