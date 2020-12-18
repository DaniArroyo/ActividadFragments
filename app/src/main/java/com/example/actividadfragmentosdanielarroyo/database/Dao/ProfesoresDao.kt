package com.example.actividadfragmentosdanielarroyo.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.actividadfragmentosdanielarroyo.database.Profesores

@Dao
interface ProfesoresDao {
    @Insert
    fun insertAll(vararg profesores: Profesores)
    @Insert
    fun insertAll(profesores: List<Profesores>)
}