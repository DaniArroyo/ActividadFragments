package com.example.actividadfragmentosdanielarroyo.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.adapters.CustomAlumnosAdapter
import com.example.actividadfragmentosdanielarroyo.database.DataRepository
import com.example.actividadfragmentosdanielarroyo.models.Alumno

class AlumnosFragment : Fragment() {

    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: Alumno? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_alumnos, container, false)

        val asignatura = arguments!!.getString("asignatura")
        val recyclerViewLista: RecyclerView = v.findViewById<View>(R.id.recyclerAlumnos) as RecyclerView
        thiscontext = container?.getContext();
        var dataRepository = DataRepository(thiscontext!!)
        var numeroAsignatura: Int
        if (asignatura.equals("BBDD")){
            numeroAsignatura = 2
        }else if(asignatura.equals("programacion")){
            numeroAsignatura = 1
        } else {
            numeroAsignatura = 1
        }

        var alumnosDatabase = dataRepository.getAlumnosRelacion(numeroAsignatura)
        var alumno = alumnosDatabase.component1().alumnos
        var listaAlumnos = ArrayList<Alumno>()

        for (i in 0..alumno.size-1) {
            listaAlumnos.add(Alumno(alumno.get(i).alumnosId,alumno.get(i).nombre.toString(), alumno.get(i).apellido.toString()))
        }

        val adapter = CustomAlumnosAdapter(listaAlumnos) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }

        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false))

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AlumnosFragment().apply {}
    }
}