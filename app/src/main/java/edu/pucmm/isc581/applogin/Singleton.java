package edu.pucmm.isc581.applogin;

import edu.pucmm.isc581.applogin.dbEntities.Usuario;


public class Singleton {

    private static Singleton singleton;

    private Singleton(){};

    public static Singleton getInstance() {
        if (singleton==null)
            singleton = new Singleton();
        return singleton;
    }
    private static Usuario usuarioLogueado;

    protected static void logUser(Usuario usuario){
        usuarioLogueado = usuario;
    }

    protected static Usuario getLoggedUser(){
        return usuarioLogueado;
    }

}
