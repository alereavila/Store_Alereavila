package com.alereavila.store

import androidx.room.Database
import androidx.room.RoomDatabase

//notacion correspondiente
//se le pasan todas las tablas o entidades y la version
//se le indica en la version que se modificara la base de datos empezo en 1 y la cambiamos a 2
@Database(entities = arrayOf(StoreEntity::class), version = 2)
abstract class StoreDataBase : RoomDatabase(){
    abstract fun storeDao (): StoreDAO
}