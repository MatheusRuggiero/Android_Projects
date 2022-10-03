package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.rasther.module.DiagModule
import br.com.tecnomotor.thanos.model.diagnostico.Atuador

@SuppressLint("StaticFieldLeak")
class AtuadorViewModel : ViewModel() {

    private lateinit var context: Context
    private lateinit var diagModule: DiagModule



    fun openModulo(modulo: Long, context: Context) : List<Atuador>  {
        this.context = context
        diagModule = DiagModule(context)
        val formatModule = "M0DuL3 F0R %S 7eCn0m70r 3l37r0n1cA d0 BrA51L"
        val moduleSec = formatModule.format(String.format("%08d", modulo))
        diagModule.loadModule(modulo.toInt(), moduleSec)
        val idAtuadorList: List<Int> = diagModule.menu?.itens?.map { it.msg } ?: arrayListOf()

        val listaAtuadores = ArrayList<Atuador>()
        for (item in diagModule.menu?.itens!!) {
            val atuador =  Atuador(item.msg,"teste",item.man.toString())
            listaAtuadores.add(atuador)
        }
        Log.i("AtuadorViewModel", "openModulo: ${diagModule.menu?.itens}")
        println("imprimindo os ids dos atuadores $idAtuadorList")
        //val atuadores : List<Atuador> = (diagModule.menu?.itens?.map { it.msg } ?: arrayListOf("") as List<Atuador>) as List<Atuador>
        return listaAtuadores

    }


}