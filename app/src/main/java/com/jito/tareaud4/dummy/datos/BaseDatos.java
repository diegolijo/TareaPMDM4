package com.jito.tareaud4.dummy.datos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, Pedido.class}, version = 1,exportSchema = false)
public abstract class BaseDatos extends RoomDatabase {
    public abstract DeviceDao Dao();
}