package com.pes.pockles.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.map.MapViewModel
import com.pes.pockles.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_pockcategory.*


class CategorySelection : AppCompatActivity() {
    val categories = arrayOf("Anuncios", "Compra&Venta", "Deportes", "Entretenimiento", "Mascotas", "Salud", "Tecnología", "Tursimo", "+18", "Varios")
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
            obtain_pocklist()
        }
        builder.setNeutralButton("ATRÁS") { _, _ ->
            Toast.makeText(applicationContext, "No selecciona nada", Toast.LENGTH_SHORT).show()
        }
        dialog = builder.create()
        dialog.show()
    }

     private fun obtain_pocklist() {
        //obtener livedata lista de pocks
        //eliminar los que no coincidan con las categorias
        //devolverlo
         val viewModel: MapViewModel by lazy {
            ViewModelProviders.of(this, ViewModelFactory()).get(MapViewModel::class.java)
        }
        viewModel.pocks.observe(
            this,
            Observer { value: Resource<List<Pock>>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<List<Pock>> -> filtrar(value.data as List<Pock>)
                    }
                }
            })
    }

    private fun filtrar(listofPocks: List<Pock>) {
        //consulta el estado visible/ no visile de las categorias y filtra la lista de Pocks
        for (id in 0..9) {
            if (checkedItems[id]) {
                val pcat: String = categories[id]
                listofPocks.filter { pock -> pock.category == pcat}
                }
            }
        }
    }


}
