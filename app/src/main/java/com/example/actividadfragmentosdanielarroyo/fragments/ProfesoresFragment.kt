package com.example.actividadfragmentosdanielarroyo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.adapters.CustomProfesoresAdapter
import com.example.actividadfragmentosdanielarroyo.database.DataRepository
import com.example.actividadfragmentosdanielarroyo.models.Profesor

class ProfesoresFragment : Fragment() {
    var itemSeleccionado: Profesor? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_profesores, container, false)
        var numeroAsignatura: Int
        thiscontext = container?.getContext();
        var dataRepository = DataRepository(thiscontext!!)
        val asignatura = arguments!!.getString("asignatura")
        val recyclerViewLista: RecyclerView = v.findViewById<View>(R.id.recyclerProfesores) as RecyclerView

        if (asignatura.equals("BBDD")){
            numeroAsignatura = 2
        }else if(asignatura.equals("programacion")){
            numeroAsignatura = 1
        } else {
            numeroAsignatura = 1
        }

        var profesoresDatabase = dataRepository.getProfesores(numeroAsignatura)
        var profesor = profesoresDatabase.get(0).profesores
        var listaProfesores = ArrayList<Profesor>()

        for (i in 0..profesor.size-1) {
            listaProfesores.add(Profesor(profesor.get(i).nombre.toString(), profesor.get(i).apellido.toString()))
        }

        val adapter = CustomProfesoresAdapter(listaProfesores) { item -> itemSeleccionado = item }
        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false))

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfesoresFragment().apply {}
    }
}