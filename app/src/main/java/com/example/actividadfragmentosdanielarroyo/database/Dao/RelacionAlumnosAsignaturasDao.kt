package com.example.actividadfragmentosdanielarroyo.database.Dao

import androidx.room.*
import com.example.actividadfragmentosdanielarroyo.database.RelacionAlumnosAsignaturas
import com.example.actividadfragmentosdanielarroyo.database.RelacionAlumnosAsignaturasCrossRef

@Dao
interface RelacionAlumnosAsignaturasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: RelacionAlumnosAsignaturasCrossRef)
    @Transaction
    @Query("SELECT * FROM asignaturas WHERE asignaturasId = :asignaturasId")
    fun getAlumnos(asignaturasId: Int): Array<RelacionAlumnosAsignaturas>
}