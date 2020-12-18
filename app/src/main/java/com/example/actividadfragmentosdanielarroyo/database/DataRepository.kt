package com.example.actividadfragmentosdanielarroyo.database

import android.content.Context
import android.os.AsyncTask
import com.example.actividadfragmentosdanielarroyo.database.Dao.*


class DataRepository(context: Context) {
    private val asignaturasDao: AsignaturasDao? = AppDatabase.getInstance(context)?.asignaturasDao()
    private val profesoresDao: ProfesoresDao? = AppDatabase.getInstance(context)?.profesoresDao()
    private val alumnosDao: AlumnosDao? = AppDatabase.getInstance(context)?.alumnosDao()
    private val relacionAsignaturasProfesoresDao: RelacionAsignaturasProfesoresDao? = AppDatabase.getInstance(context)?.profesoresAsignaturasDao()
    private val relacionAlumnosAsignaturasDao: RelacionAlumnosAsignaturasDao?= AppDatabase.getInstance(context)?.alumnosAsignaturasDao()

    fun insertAsignaturasProfesores(asignaturasProfesores: RelacionAsignaturasProfesores):Int{
        if (asignaturasDao != null && profesoresDao !=null && relacionAsignaturasProfesoresDao!= null) {
            return InsertAsignaturasProfesoresAsyncTask(asignaturasDao, profesoresDao, relacionAsignaturasProfesoresDao).execute(asignaturasProfesores).get()
        }
        return -1
    }

    fun insertAsignaturasAlumnos(asignaturasAlumnos: RelacionAlumnosAsignaturas):Int{
        if (asignaturasDao != null && alumnosDao !=null && relacionAlumnosAsignaturasDao!= null) {
            return InsertAsignaturasAlumnosAsyncTask(asignaturasDao, alumnosDao, relacionAlumnosAsignaturasDao).execute(asignaturasAlumnos).get()
        }
        return -1
    }

    fun getAsignaturas(): Array<Asignaturas>{
        return GetAsignaturas(asignaturasDao!!).execute().get()
    }

    fun getAlumnosRelacion(idAsignatura: Int): Array<RelacionAlumnosAsignaturas>{
        return GetAlumnosRelacion(relacionAlumnosAsignaturasDao!!, idAsignatura).execute().get()
    }

    fun getProfesores(idAsignatura: Int): Array<RelacionAsignaturasProfesores>{
        return GetProfesores(relacionAsignaturasProfesoresDao!!, idAsignatura).execute().get()
    }
    fun GetAlumnos(alumnoId: Int): Array<Alumnos>{
        return GetAlumnos(alumnosDao!!, alumnoId).execute().get()
    }

    private class InsertAsignaturasProfesoresAsyncTask(private val asignaturasDao: AsignaturasDao, private val profesoresDao: ProfesoresDao, private val asignaturasProfesoresDao: RelacionAsignaturasProfesoresDao): AsyncTask<RelacionAsignaturasProfesores, Void, Int>(){
        override fun doInBackground(vararg asignaturasProfesores: RelacionAsignaturasProfesores?): Int {
            try{
                for (asignaturasProfesores in asignaturasProfesores){
                    if (asignaturasProfesores !=null){
                        profesoresDao.insertAll(asignaturasProfesores.profesores)
                        for (profesores in asignaturasProfesores.profesores){
                            asignaturasProfesoresDao.insert(RelacionAsignaturasProfesoresCrossRef(asignaturasProfesores.asignaturas.asignaturasId, profesores.profesoresId))
                        }
                    }
                }

                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    private class InsertAsignaturasAlumnosAsyncTask(private val asignaturasDao: AsignaturasDao, private val alumnosDao: AlumnosDao, private val asignaturasAlumnosDao: RelacionAlumnosAsignaturasDao): AsyncTask<RelacionAlumnosAsignaturas, Void, Int>(){
        override fun doInBackground(vararg asignaturasAlumnos: RelacionAlumnosAsignaturas?): Int {
            try{
                for (asignaturasAlumnos in asignaturasAlumnos){
                    if (asignaturasAlumnos !=null){
                        asignaturasDao.insertAll(asignaturasAlumnos.asignaturas)
                        alumnosDao.insertAll(asignaturasAlumnos.alumnos)
                        for (alumnos in asignaturasAlumnos.alumnos){
                            asignaturasAlumnosDao.insert(RelacionAlumnosAsignaturasCrossRef(asignaturasAlumnos.asignaturas.asignaturasId, alumnos.alumnosId))
                        }
                    }
                }

                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    private class GetAsignaturas(private val asignaturasDao: AsignaturasDao) :AsyncTask<Void, Void, Array<Asignaturas>>(){
        override fun doInBackground(vararg params: Void?): Array<Asignaturas> {
            return asignaturasDao.getAsignaturas()
        }
    }

    private class GetProfesores(private val asignaturasProfesoresDao: RelacionAsignaturasProfesoresDao, private val idAsignatura: Int) :AsyncTask<Void, Void, Array<RelacionAsignaturasProfesores>>(){
        override fun doInBackground(vararg params: Void?): Array<RelacionAsignaturasProfesores> {
            return asignaturasProfesoresDao.getProfesorOne(idAsignatura)
        }
    }

    private class GetAlumnosRelacion(private val asignaturasAlumnosDao: RelacionAlumnosAsignaturasDao, private val idAsignatura: Int) :AsyncTask<Void, Void, Array<RelacionAlumnosAsignaturas>>(){
        override fun doInBackground(vararg params: Void?): Array<RelacionAlumnosAsignaturas> {
            return asignaturasAlumnosDao.getAlumnos(idAsignatura)
        }
    }
    private class GetAlumnos(private val alumnosDao: AlumnosDao, private val idAlumno: Int) :AsyncTask<Void, Void, Array<Alumnos>>(){
        override fun doInBackground(vararg params: Void?): Array<Alumnos> {
            return alumnosDao.getAlumnoOne(idAlumno)
        }
    }


}