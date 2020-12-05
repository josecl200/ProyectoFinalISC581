package edu.pucmm.isc581.applogin.dbDaos;

import androidx.room.*;
import edu.pucmm.isc581.applogin.dbEntities.CarritoDeComprasPorUsuario;
import edu.pucmm.isc581.applogin.dbEntities.OrdenesDelUsuario;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;

@Dao
public interface UsuarioDAO {

    @Query("SELECT password from USUARIO where email = :email")
    public String getPasswordByEmail(String email);

    @Transaction @Query("SELECT * FROM USUARIO WHERE email = :email")
    public OrdenesDelUsuario getUserWithOrders(String email);

    @Transaction @Query("SELECT * FROM USUARIO WHERE email = :email")
    public CarritoDeComprasPorUsuario getShoppingCart(String email);

    @Transaction @Query("SELECT * FROM USUARIO WHERE email = :email AND password = :password")
    public Usuario getUserForLogin(String email, String password);

    @Insert
    public long registerUser(Usuario usuario);

    @Update
    public long updateUser(Usuario usuario);

    @Delete
    public long deleteUser(Usuario usuario);


}
