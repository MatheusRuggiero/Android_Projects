package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.rasther.module.DiagValueItem
import br.com.tecnomotor.thanos.model.diagnostico.Leitura
import br.com.tecnomotor.thanos.model.diagnostico.TipoLeitura
import br.com.tecnomotor.thanos.model.diagnostico.Unidade
import br.com.tecnomotor.thanos.repository.diagnostico.LeiturasRepositry

class LeiturasViewModel (
    val leiturasRepository: LeiturasRepositry
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var leiturasList: ArrayList<Leitura> = arrayListOf()
    val leiturasLiveData: MutableLiveData<ArrayList<Leitura>> = MutableLiveData(leiturasList)

    fun getUnidade(unidade: Unidade) {
        leiturasRepository.getUnidade(unidade.id).observeForever {
            if (it != null) {
                Log.d(this.javaClass.simpleName, unidade.toString())
                unidade.nome = it.valuePtLong ?: ""
            }
        }
    }


    fun getLeituras(leituras: ArrayList<DiagValueItem>) {
        val leiturasNormais : List<DiagValueItem> = leituras.map{ it }.filter{ !it.typeValue.analisisGraph.enabled }
        leiturasRepository.getLeituras(leiturasNormais.map { it.msg }).observeForever { leiturasEntity ->
            leiturasList.clear()
            leiturasLiveData.postValue(leiturasList)
            leiturasEntity.forEach { valueEntity ->
                try {
                    val leitura = leiturasNormais.find { it.msg == valueEntity.id!!.toInt() }
                    val unidade = Unidade(leitura!!.typeValue.unit)
                    getUnidade(unidade)
                    leiturasList.add(
                        Leitura(
                            valueEntity.id!!.toInt(),
                            TipoLeitura.valueOf(leitura.typeValue.typeName),
                            valueEntity.valuePtLong ?: "",
                            Float.MIN_VALUE,
                            unidade
                        )
                    )
                } catch (e: Exception) {
                    Log.e(this.javaClass.simpleName, e.toString())
                }
            }
            println("Imprimindo a lista de analise grafica $leiturasList")
            leiturasLiveData.postValue(leiturasList)
        }
    }

    fun updateValor(leitura: Leitura, valor: Number) {
        val leituraEncontrada = leiturasList.find { it.id == leitura.id}
        if (leituraEncontrada?.tipo != TipoLeitura.Digital) {
            leituraEncontrada?.valor = valor
        } else { //Digital, procurar o texto no banco de dados em STATE_TAB

        }
        leiturasLiveData.postValue(leiturasList)
    }
}