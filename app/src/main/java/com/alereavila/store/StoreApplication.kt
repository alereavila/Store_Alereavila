package com.alereavila.store

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class StoreApplication : Application(){
    //Implemnetar el patrón singleton
    //nos permitira acceder a la base de datos desde cualquier parte de la aplicacion
    //hay que declarar o instanciar en Manifest android:name=".StoreApplication"
    companion object{
        lateinit var dataBase : StoreDataBase
    }

    override fun onCreate() {
        super.onCreate()

        //cuando se migra la BD
        val MIGRATION_1_2=object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE StoreEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT ''")
            }
        }

        //recibe un contexto
        dataBase= Room.databaseBuilder(this,
            StoreDataBase::class.java,
            "StoreDataBase")
            .addMigrations(MIGRATION_1_2)
            .build()

    }
}