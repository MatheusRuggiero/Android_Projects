package br.com.tecnomotor.thanos.ui.relatorio.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.repository.cliente.RelatorioAplRepository
import br.com.tecnomotor.thanos.repository.cliente.ResourceC
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ListaRelatoriosViewModel() : ViewModel() {

    private val repository = RelatorioAplRepository

    var gsonObjto = "nada aqui"

    fun buscaTodos(): LiveData<ResourceC<List<RelatorioAplicacaoEntity>?>> {
        return repository.buscaTodos()
    }


    fun createJson(relatorioJson: RelatorioAplicacaoEntity) {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonTut: String = gson.toJson(relatorioJson)
        println(jsonTut)

        val jsonTutPretty: String = gsonPretty.toJson(relatorioJson)
        println(jsonTutPretty)

        gsonObjto = jsonTutPretty

    }


}