package edu.pucmm.isc581.applogin.dbEntities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(indices = {@Index(value = "email", unique = true)}) @Data @NoArgsConstructor
public class Usuario {
    @PrimaryKey
    private Long idUsuario;
    private String name;
    private String username;
    private String email;
    private String password;
    private String imagen;
    private String telefono;
    private String direccion;
    private String direccion2;
    
}
