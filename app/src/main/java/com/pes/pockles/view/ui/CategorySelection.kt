package com.pes.pockles.view.ui

import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pes.pockles.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pockcategory.*


class CategorySelection : AppCompatActivity() {
    val checkedItems = booleanArrayOf(true, true, true, true, true, true, true, true, true, true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pockcategory)

        outlinedButton.setOnClickListener {

            showFiltrarOptions()
        }
    }

    private fun showFiltrarOptions() {
        lateinit var dialog:AlertDialog

        val categories = arrayOf("Anuncios", "Compra&Venta", "Deportes", "Entretenimiento", "Mascotas", "Salud", "Tecnología", "Tursimo", "+18", "Varios")
        //val checkedItems = booleanArrayOf(true, true, true, true, true, true, true, true, true, true)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Marca las categorías de Pock que desees ver en tu mapa...")


        builder.setMultiChoiceItems(categories, checkedItems, {dialog,which,isChecked->
            // Update the clicked item checked status
            categories[which] = isChecked.toString()
            !checkedItems[which] //Keep new checked status

            //envio de información [INCOMPLETO]
            Toast.makeText(applicationContext, "Selected", Toast.LENGTH_SHORT).show()
        })

        builder.setPositiveButton("FILTRAR") { dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(applicationContext, "Enviando seleción", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("ATRÁS") { _, _ ->
            Toast.makeText(applicationContext, "No selecciona nada", Toast.LENGTH_SHORT).show()
        }
        dialog = builder.create()
        dialog.show()

    }

}
