package com.example.actividadfragmentosdanielarroyo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.database.DataRepository
import com.example.actividadfragmentosdanielarroyo.models.Alumno

class AlumnoElegidoFragment : Fragment() {
    var textViewId: TextView? = null
    var textViewNombre: TextView? = null
    var textViewApellido: TextView? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_alumno_elegido, container, false)
        val idAlumno = arguments?.getString("idAlumno")?.toInt()

        thiscontext = container?.getContext();
        var dataRepository = thiscontext?.let { DataRepository(it) }
        var alumnosGuardados = idAlumno?.let { dataRepository?.GetAlumnos(it.toInt()) }

        textViewId = v.findViewById<View>(R.id.textViewId) as TextView
        textViewNombre = v.findViewById<View>(R.id.textViewNombre) as TextView
        textViewApellido = v.findViewById<View>(R.id.textViewApellido) as TextView

        if (alumnosGuardados != null) {
            textViewNombre!!.text = idAlumno.toString()
            textViewNombre!!.text = alumnosGuardados.get(0).nombre
            textViewApellido!!.text = alumnosGuardados.get(0).apellido
        }
        return v
    }

    fun updateData(item: Alumno?) {
        if (item!=null) {
            textViewNombre!!.text = item.nombre
            textViewApellido!!.text = item.apellido
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumnoElegidoFragment().apply {
            }
    }

}