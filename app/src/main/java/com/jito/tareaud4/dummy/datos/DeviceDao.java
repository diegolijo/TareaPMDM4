package com.jito.tareaud4.dummy.datos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM usuario")
    List<Usuario> selectUsusarios();


    @Query("SELECT * FROM usuario WHERE usuario LIKE :usuario  LIMIT 1")
    Usuario selectUsuario(String usuario);

    @Query("SELECT * FROM usuario WHERE email LIKE :email  LIMIT 1")
    Usuario selectEmail(String email);



    @Query("SELECT * FROM pedido")
    List<Pedido> selectPedido();

    @Query("SELECT * FROM pedido WHERE estado = :estado")
    List<Pedido> selectAceptados(String estado);

    @Query("SELECT * FROM pedido WHERE usuario = :usuario")
    List<Pedido> selectPedidoUsuario(String usuario);


    @Query("SELECT * FROM pedido WHERE numero = :numero")
    Pedido selectPedidoNumero(int numero);



    @Query("SELECT * FROM pedido WHERE usuario = :usuario AND estado = :estado")
    List<Pedido> selectPedidosEstado(String usuario, String estado);





    @Query("UPDATE pedido SET estado = :estado WHERE numero = :numero")
    void updatePedido(int numero, String estado);

    @Query("UPDATE usuario SET nombre = :nombre, apellidos = :apellidos, email = :email, foto = :foto,contraseña = :contraseña WHERE usuario = :usuario")
    void updateUsuario( String usuario,String nombre,String apellidos,String contraseña,String foto,String email);








    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insertUsuario(Usuario user);

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insertPedido(Pedido pedido);


    @Insert
    void insertBothUsers(Usuario user1, Usuario user2);

    @Insert
    void insertUsersAndFriends(Usuario user, List<Usuario> friends);










    @Delete
    void delete(Usuario user);

}