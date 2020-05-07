package com.example.appgrafica

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_main_grafica.*


class MainActivityGrafica : AppCompatActivity() {

    val entries = ArrayList<BarEntry>() // Arreglo para cargar las ventas de los empleados eje de las Y
    val labels = ArrayList<String>() // Arreglo para cargar los nombres de empleados en el eje de las X
    var cursor : Cursor?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_grafica)
        cargarDatos()
        setBarChart()
    }

    fun cargarDatos(){
        val admin = AdminBD(this)
        cursor = admin.Consulta("Select * from Empleado Order by Ventas")
    }

    fun setBarChart() {
        var i = 0
        if (cursor!!.moveToFirst()){
            do {
                val nom = cursor!!.getString(1)
                val vtas = cursor!!.getFloat(2)
                entries.add(BarEntry(vtas, i)) // Agregamos Ventas
                labels.add(nom)                // Agregamos nomEmp
                i++
            } while (cursor!!.moveToNext())
            val barDataSet = BarDataSet(entries, "Datos")
            val data = BarData(labels, barDataSet)
            barChart.data = data
            barChart.setDescription("Graficas Ventas X Empleado")
            barDataSet.color = resources.getColor(R.color.colorAccent)
            barChart.animateY(5000)
        }
    }

}

