package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel

import ScreenShotView
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tecnomotor.rasther.module.DiagValueItem
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioGraficosEntity
import br.com.tecnomotor.thanos.model.diagnostico.AnaliseGrafica
import br.com.tecnomotor.thanos.model.diagnostico.TipoLeitura
import br.com.tecnomotor.thanos.model.diagnostico.Unidade
import br.com.tecnomotor.thanos.repository.diagnostico.AnaliseGraficaRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class AnaliseGraficaViewModel(
    private val analiseGraficaRepository: AnaliseGraficaRepository

) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var analiseGraficaList: ArrayList<AnaliseGrafica> = arrayListOf()
    val analiseGraficaLiveData: MutableLiveData<ArrayList<AnaliseGrafica>> =
        MutableLiveData(analiseGraficaList)

    init {
//        println("criando viewmodel")
    }

    override fun onCleared() {
        super.onCleared()
//        println("destruindo viewmodel")
    }

    fun getUnidade(unidade: Unidade) {
        analiseGraficaRepository.getUnidade(unidade.id).observeForever {
            if (it != null) {
                Log.d(this.javaClass.simpleName, unidade.toString())
                unidade.nome = it.valuePtLong ?: ""
            }
        }
    }


    fun getAnaliseGrafica(AnaliseGrafica: ArrayList<DiagValueItem>) {
        val leiturasAnaliseGrafica: List<DiagValueItem> =
            AnaliseGrafica.map { it }.filter { it.typeValue.analisisGraph.enabled }
        analiseGraficaRepository.getAnaliseGrafica(AnaliseGrafica.map { it.msg })
            .observeForever { analiseGraficaValueEntity ->
                analiseGraficaList.clear()
                analiseGraficaLiveData.postValue(analiseGraficaList)
                analiseGraficaValueEntity.forEach { valueEntity ->
                    try {
                        val analiseGrafica =
                            leiturasAnaliseGrafica.find { it.msg == valueEntity.id!!.toInt() }
                        val unidade = Unidade(analiseGrafica!!.typeValue.unit)
                        val valorHabilitado =
                            leiturasAnaliseGrafica.filter { it.typeValue.range.enabled }
                        getUnidade(unidade)
                        analiseGraficaList.add(
                            AnaliseGrafica(
                                valueEntity.id!!.toInt(),
                                valueEntity.valuePtLong ?: "",
                                TipoLeitura.valueOf(analiseGrafica.typeValue.typeName),
                                analiseGrafica.typeValue.analisisGraph.min +
                                        ((analiseGrafica.typeValue.analisisGraph.max - analiseGrafica.typeValue.analisisGraph.min) / 2),
                                analiseGrafica.typeValue.dp,
                                analiseGrafica.typeValue.analisisGraph.min,
                                analiseGrafica.typeValue.analisisGraph.max,
                                unidade
                            )
                        )
                    } catch (e: Exception) {
                        Log.e(this.javaClass.simpleName, e.toString())
                    }
                }
                analiseGraficaLiveData.postValue(analiseGraficaList)
            }
    }

    fun updateValor(analiseGrafica: AnaliseGrafica, valor: Number) {
        val leituraEncontrada = analiseGraficaList.find { it.id == analiseGrafica.id }
        if (leituraEncontrada?.tipo != TipoLeitura.Digital) {
            leituraEncontrada?.valor = valor
        } else { //Digital, procurar o texto no banco de dados em STATE_TAB

        }
        analiseGraficaLiveData.postValue(analiseGraficaList)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun enviaGraficoRelatorio(relatorioGrafico: RelatorioGraficosEntity) {
        analiseGraficaRepository.insereGraficoRelatorio(relatorioGrafico)
    }

    private fun pegaPrintDaView(graficView: View, context: Context): ByteArray? {
        return ScreenShotView(context).getToByteStream(graficView)
    }

    fun recebeGraficoParaRelatorio(view: View, context: Context) {
        val imgByteStream = pegaPrintDaView(view, context)
        if (imgByteStream != null) {
            val relatorioGraficosEntity = RelatorioGraficosEntity(
                0,
                1,
                "Relatório Grafico",
                imgByteStream,
                Calendar.getInstance()
            )
            enviaGraficoRelatorio(relatorioGraficosEntity)
        }
    }
}