package com.example.actividadfragmentosdanielarroyo.database.Dao

import androidx.room.*
import com.example.actividadfragmentosdanielarroyo.database.RelacionAsignaturasProfesores
import com.example.actividadfragmentosdanielarroyo.database.RelacionAsignaturasProfesoresCrossRef

@Dao
interface RelacionAsignaturasProfesoresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: RelacionAsignaturasProfesoresCrossRef)
    @Transaction
    @Query("SELECT * FROM asignaturas WHERE asignaturasId = :asignaturasId")
    fun getProfesorOne(asignaturasId: Int): Array<RelacionAsignaturasProfesores>
}