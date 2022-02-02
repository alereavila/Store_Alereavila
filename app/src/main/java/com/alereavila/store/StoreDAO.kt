package com.alereavila.store

import androidx.room.*

//HAY QUE CONFIGUARAR QUE ES UN DAO
//DATA ACCESS OBJECTS
@Dao
interface StoreDAO {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStore() :MutableList<StoreEntity>

    @Insert
    fun addStore(storeEntity: StoreEntity): Long

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)
}