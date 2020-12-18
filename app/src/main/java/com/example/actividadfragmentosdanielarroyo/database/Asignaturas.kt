package com.example.actividadfragmentosdanielarroyo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asignaturas(
    @PrimaryKey val asignaturasId: Int,
    @ColumnInfo(name = "IdAsignatura") var nombre: String?
)