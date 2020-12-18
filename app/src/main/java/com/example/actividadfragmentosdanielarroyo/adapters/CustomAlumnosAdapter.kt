package com.example.actividadfragmentosdanielarroyo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.models.Alumno

class CustomAlumnosAdapter (var listaAlumnos: ArrayList<Alumno>, private val listener: (Alumno) -> Unit) : RecyclerView.Adapter<CustomAlumnosAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAlumnosAdapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_alumnos, parent, false)
            val viewHolder = ViewHolder(v)
            return viewHolder
        }

        override fun onBindViewHolder(holder: CustomAlumnosAdapter.ViewHolder, position: Int) {
            holder.bindItems(listaAlumnos[position])
            holder.itemView.setOnClickListener { listener(listaAlumnos[position]) }
        }

        override fun getItemCount(): Int {
            return listaAlumnos.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindItems(cliente: Alumno) {
                val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
                val textViewApellido = itemView.findViewById<TextView>(R.id.textViewApellido)
                val textViewId = itemView.findViewById<TextView>(R.id.textViewId)
                textViewNombre.text = cliente.nombre
                textViewApellido.text = cliente.apellido
                textViewId.text = cliente.id.toString()
            }
        }
}
