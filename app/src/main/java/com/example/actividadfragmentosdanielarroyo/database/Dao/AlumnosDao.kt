package com.example.actividadfragmentosdanielarroyo.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actividadfragmentosdanielarroyo.database.Alumnos

@Dao
interface AlumnosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg alumnos: Alumnos)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(alumnos: List<Alumnos>)
    @Query("SELECT * FROM alumnos WHERE alumnosId = :alumnoId")
    fun getAlumnoOne(alumnoId: Int): Array<Alumnos>
}