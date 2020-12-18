package com.example.actividadfragmentosdanielarroyo.database

import androidx.room.*

data class RelacionAsignaturasProfesores(
    @Embedded var asignaturas: Asignaturas,

    @Relation(
        parentColumn = "asignaturasId",
        entity = Profesores::class,
        entityColumn = "profesoresId",
        associateBy = Junction(value = RelacionAsignaturasProfesoresCrossRef::class,
            parentColumn = "asignaturasId",
            entityColumn = "profesoresId")
    )

    var profesores: List<Profesores>
)

@Entity(primaryKeys = ["asignaturasId", "profesoresId"])
data class RelacionAsignaturasProfesoresCrossRef(
    val asignaturasId: Int,
    val profesoresId: Int
)

