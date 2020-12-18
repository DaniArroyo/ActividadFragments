package com.example.actividadfragmentosdanielarroyo.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class RelacionAlumnosAsignaturas(
    @Embedded var asignaturas: Asignaturas,

    @Relation(
        parentColumn = "asignaturasId",
        entity = Alumnos::class,
        entityColumn = "alumnosId",
        associateBy = Junction(value = RelacionAlumnosAsignaturasCrossRef::class,
            parentColumn = "asignaturasId",
            entityColumn = "alumnosId")
    )

    var alumnos: List<Alumnos>
)

@Entity(primaryKeys = ["asignaturasId", "alumnosId"])
data class RelacionAlumnosAsignaturasCrossRef(
    val asignaturasId: Int,
    val alumnosId: Int
)