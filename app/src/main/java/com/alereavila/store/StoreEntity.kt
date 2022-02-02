package com.alereavila.store

import androidx.room.Entity
import androidx.room.PrimaryKey

//para que sea una entidad en room
@Entity(tableName = "StoreEntity")
//llave primaria
//hay que migrar la base para agregar un nuevo campo
data class StoreEntity(@PrimaryKey(autoGenerate = true) var id: Long=0,
                       var name: String,
                       var phone: String,
                       var webSite: String="",
                       var photoUrl: String,// sera un campo requerido
                       var isFavorite: Boolean=false)

