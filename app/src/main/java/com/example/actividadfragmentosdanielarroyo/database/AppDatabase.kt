package com.example.actividadfragmentosdanielarroyo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.actividadfragmentosdanielarroyo.database.Dao.*

@Database(entities = [Asignaturas::class, Profesores::class,Alumnos::class, RelacionAsignaturasProfesoresCrossRef::class, RelacionAlumnosAsignaturasCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asignaturasDao(): AsignaturasDao
    abstract fun profesoresDao(): ProfesoresDao
    abstract fun alumnosDao(): AlumnosDao
    abstract fun profesoresAsignaturasDao(): RelacionAsignaturasProfesoresDao
    abstract fun alumnosAsignaturasDao(): RelacionAlumnosAsignaturasDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "datosClase.db"

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE ?: synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
