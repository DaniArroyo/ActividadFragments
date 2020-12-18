package com.example.actividadfragmentosdanielarroyo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profesores(
    @PrimaryKey val profesoresId: Int,
    @ColumnInfo(name = "Nombre")var nombre: String?,
    @ColumnInfo(name = "Apellido")var apellido: String?
)