package com.jito.tareaud4.dummy.datos;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey
    @NonNull
    public String usuario;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "apellidos")
    public String apellidos;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "contraseña")
    public String contraseña;

    @ColumnInfo(name = "admin")
    public boolean admin;

    @ColumnInfo(name = "foto")
    public String foto;



/*(autoGenerate =true)
    public int id = 0;

    @ColumnInfo(name = "nombre")*/

}



