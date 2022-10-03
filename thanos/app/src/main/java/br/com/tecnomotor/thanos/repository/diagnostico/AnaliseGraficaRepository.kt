package br.com.tecnomotor.thanos.repository.diagnostico

import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.cliente.dao.GraficosDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioGraficosEntity
import br.com.tecnomotor.thanos.database.messages.dao.UnitDao
import br.com.tecnomotor.thanos.database.messages.dao.ValueDao
import br.com.tecnomotor.thanos.database.messages.entity.UnitEntity
import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AnaliseGraficaRepository(
    private val analiseGrafica: ValueDao,
    private val unidade: UnitDao,
    private val graficoDao: GraficosDao
) {

    fun getAnaliseGrafica(analiseGraficaID: List<Int>?): LiveData<List<ValueEntity>> {
        return analiseGrafica.getLeituras(analiseGraficaID)
    }

    fun getUnidade(unidadeId: Int): LiveData<UnitEntity> {
        return unidade.getUnidade(unidadeId)
    }

    @DelicateCoroutinesApi
    fun insereGraficoRelatorio(grafico: RelatorioGraficosEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            graficoDao.add(grafico)
        }
    }

}