package com.example.actividadfragmentosdanielarroyo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.models.Profesor

class CustomProfesoresAdapter (var listaProfesores: ArrayList<Profesor>, private val listener: (Profesor) -> Unit) : RecyclerView.Adapter<CustomProfesoresAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomProfesoresAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_profesores, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CustomProfesoresAdapter.ViewHolder, position: Int) {
        holder.bindItems(listaProfesores[position])
        holder.itemView.setOnClickListener {listener(listaProfesores[position]) }
    }

    override fun getItemCount(): Int {
        return listaProfesores.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cliente: Profesor) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            val textViewApellido = itemView.findViewById<TextView>(R.id.textViewApellido)
            textViewNombre.text = cliente.nombre
            textViewApellido.text = cliente.apellido
        }
    }
}