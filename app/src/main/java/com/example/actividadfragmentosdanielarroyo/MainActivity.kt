package com.example.actividadfragmentosdanielarroyo

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.actividadfragmentosdanielarroyo.activities.AlumnoElegidoActivity
import com.example.actividadfragmentosdanielarroyo.database.*
import com.example.actividadfragmentosdanielarroyo.fragments.AlumnoElegidoFragment
import com.example.actividadfragmentosdanielarroyo.fragments.AlumnosFragment
import com.example.actividadfragmentosdanielarroyo.fragments.ProfesoresFragment
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var alumnosFragmentFrame: FrameLayout? = null
    var profesoresFragmentFrame: FrameLayout? = null
    var alumnoElegidoFragmentFrame: FrameLayout? = null
    var alumnosFragment: AlumnosFragment? = null
    var profesoresFragment: ProfesoresFragment? = null
    var alumnoElegidoFragment: AlumnoElegidoFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        importJSON()
        createMain()
    }

    private fun importJSON() {

        var dataRepository = DataRepository(this)
        var bufferedReaderRecurso = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReaderRecurso.readLine()
        }
        textoLeido = todo.toString()
        bufferedReaderRecurso.close()

        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("asignaturas")
        var listaAlumnos = ArrayList<Alumnos>()
        var listaProfesores = ArrayList<Profesores>()

        for (i in 0 until jsonArray.length()) {
            listaAlumnos.clear()
            listaProfesores.clear()

            var asignatura = Asignaturas(i + 1, jsonArray.get(i).toString())
            val asignaturaTex = jsonArray.get(i).toString()
            val jsonArrayProf = jsonObject.optJSONArray("profesores")

            for (i in 0 until jsonArrayProf.length()) {
                val objeto = jsonArrayProf.getJSONObject(i)

                if (objeto.optString("asignatura").equals(asignaturaTex)) {
                    var codigoProfesor = objeto.optString("codigo")
                    var nombreProfesor = objeto.optString("nombre")
                    var apellidoProfesor = objeto.optString("apellido")
                    var profesor = Profesores(codigoProfesor.toString().toInt(), nombreProfesor.toString(), apellidoProfesor.toString())
                    listaProfesores.add(profesor)
                }
            }
            val jsonArrayAlumno = jsonObject.optJSONArray("alumnos")

            for (i in 0 until jsonArrayAlumno.length()) {
                val objeto = jsonArrayAlumno.getJSONObject(i)
                val jsonArrayAsignaturas = objeto.optJSONArray("Asignaturas")

                for (i in 0 until jsonArrayAsignaturas.length()) {
                    val objetoAsignatura = jsonArrayAsignaturas[i].toString()

                    if (objetoAsignatura.equals(asignaturaTex)) {
                        var codigoAlumno = objeto.optString("codigo")
                        var nombreAlumno = objeto.optString("nombre")
                        var apellidoAlumno = objeto.optString("apellido")
                        var alumno = Alumnos(codigoAlumno.toString().toInt(), nombreAlumno.toString(), apellidoAlumno.toString())
                        listaAlumnos.add(alumno)
                    }
                }
            }
            var asignaturasAlumnos = RelacionAlumnosAsignaturas(asignatura, listaAlumnos)
            var asignaturasProfesores = RelacionAsignaturasProfesores(asignatura, listaProfesores)

            dataRepository.insertAsignaturasAlumnos(asignaturasAlumnos)
            dataRepository.insertAsignaturasProfesores(asignaturasProfesores)
        }
    }

    private fun verDatos(asignatura: String){
        var frameLayoutFragmentProfesor: FrameLayout? = null
        if (!asignatura.equals("Asignaturas:")) {

            val asignaturaNombre = Bundle()
            asignaturaNombre.putString("asignatura", asignatura)

            profesoresFragmentFrame = findViewById(R.id.profesoresFrame)
            alumnosFragmentFrame = findViewById(R.id.alumnosFrame)
            alumnoElegidoFragmentFrame = findViewById(R.id.alumnoElegidoFrame)

            profesoresFragmentFrame?.removeAllViewsInLayout()
            alumnosFragmentFrame?.removeAllViewsInLayout()
            alumnoElegidoFragmentFrame?.removeAllViewsInLayout()

            profesoresFragment = ProfesoresFragment.newInstance()
            alumnosFragment = AlumnosFragment.newInstance()
            alumnosFragment!!.activityListener = activityListener
            profesoresFragment!!.setArguments(asignaturaNombre)
            alumnosFragment!!.setArguments(asignaturaNombre)
            alumnoElegidoFragment = AlumnoElegidoFragment()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (alumnoElegidoFragmentFrame !=null){
                // HORIZONTAL
                fragmentTransaction.add(R.id.profesoresFrame, profesoresFragment!!)
                fragmentTransaction.add(R.id.alumnosFrame, alumnosFragment!!)
                fragmentTransaction.add(R.id.alumnoElegidoFrame, alumnoElegidoFragment!!)
            }
            else {
                fragmentTransaction.add(R.id.profesoresFrame, profesoresFragment!!)
                fragmentTransaction.add(R.id.alumnosFrame, alumnosFragment!!)
            }
            fragmentTransaction.commit()
        }
    }

    var activityListener = View.OnClickListener {
        if (alumnoElegidoFragmentFrame!=null) {
            alumnoElegidoFragment!!.updateData(alumnosFragment!!.itemSeleccionado)

        }else{
            val intent = Intent(this, AlumnoElegidoActivity::class.java).apply {
                putExtra("idAlumno", alumnosFragment!!.itemSeleccionado?.id.toString())
            }
            startActivity(intent)
        }

    }

    private fun createMain(){
        var dataRepository = DataRepository(this)
        val spinner = findViewById<Spinner>(R.id.spinnerAsignaturas)
        var asignaturasArray = dataRepository.getAsignaturas()
        var spinnerArray = ArrayList<String>()
        spinnerArray.add("Asignaturas:")
        for (items in asignaturasArray) {
            spinnerArray.add(items.nombre.toString())
        }
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerArray)
        if (spinner != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    verDatos(spinner.selectedItem.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mensaje!!")
        builder.setMessage("¿Está seguro que quiere salir?")
        builder.setPositiveButton("Si") { dialog, _ -> finish()}
        builder.setNegativeButton("No") { dialog, which -> }
        builder.show()
    }

}