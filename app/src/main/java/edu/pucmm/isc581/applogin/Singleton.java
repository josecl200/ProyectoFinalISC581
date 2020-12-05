package edu.pucmm.isc581.applogin;

import android.content.Context;
import androidx.room.Room;
import edu.pucmm.isc581.applogin.dbEntities.Usuario;


public class Singleton {

    private static Singleton instance;

    private Singleton(){};

    public static Singleton getInstance() {
        if (instance==null)
            instance = new Singleton();
        return instance;
    }
    private static Usuario usuarioLogueado;
    private static DBWrapper dataBased;

    public void logUser(Usuario usuario){
        usuarioLogueado = usuario;
    }

    public Usuario getLoggedUser(){
        return usuarioLogueado;
    }

    public DBWrapper getDataBased(Context context){
        if (dataBased == null){
            dataBased= Room.databaseBuilder(context.getApplicationContext(),
                    DBWrapper.class, "instance.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return dataBased;
    }

}
