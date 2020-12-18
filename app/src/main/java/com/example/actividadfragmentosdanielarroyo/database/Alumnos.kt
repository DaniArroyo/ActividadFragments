package com.example.actividadfragmentosdanielarroyo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alumnos (
    @PrimaryKey val alumnosId: Int,
    @ColumnInfo(name = "Nombre")var nombre: String?,
    @ColumnInfo(name = "Apellido") var apellido: String?
)
