package edu.pucmm.isc581.applogin;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import edu.pucmm.isc581.applogin.dbDaos.*;
import edu.pucmm.isc581.applogin.dbEntities.*;

import java.util.Calendar;

@Database(entities = {Articulo.class, CarritoDeCompra.class, Categoria.class, Foto.class, Orden.class, Usuario.class, OrdenesYArticulos.class}, version = 1)
public abstract class DBWrapper extends RoomDatabase {
    public abstract ArticuloDAO getArticuloDAO();
    public abstract CategoriaDAO getCategoriaDAO();
    public abstract FotosDAO getFotosDAO();
    public abstract OrdenDAO getOrdenDAO();
    public abstract UsuarioDAO getUsuarioDAO();

}
