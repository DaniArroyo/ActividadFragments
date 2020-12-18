package com.example.actividadfragmentosdanielarroyo.activities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadfragmentosdanielarroyo.R
import com.example.actividadfragmentosdanielarroyo.fragments.AlumnoElegidoFragment

class AlumnoElegidoActivity : AppCompatActivity() {
    var alumnoElegidoFrameActivity: FrameLayout? = null
    var alumnoElegidoFragmentFragment: AlumnoElegidoFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno_elegido)

        val messeage = intent.getStringExtra("idAlumno")
        val idAlumno = Bundle()
        idAlumno.putString("idAlumno", messeage)

        alumnoElegidoFrameActivity = findViewById(R.id.alumnoElegidoFrame)
        alumnoElegidoFragmentFragment = AlumnoElegidoFragment()
        alumnoElegidoFragmentFragment!!.setArguments(idAlumno)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.alumnoElegidoFrame, alumnoElegidoFragmentFragment!!)

        fragmentTransaction.commit()
    }

}