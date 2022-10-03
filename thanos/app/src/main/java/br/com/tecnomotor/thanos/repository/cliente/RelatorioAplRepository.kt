package br.com.tecnomotor.thanos.repository.cliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import br.com.tecnomotor.thanos.database.cliente.dao.RelatorioAplDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.database.relatorio.RelatorioDatabase
import br.com.tecnomotor.thanos.repository.menu.MontadoraRepository

class RelatorioAplRepository() {

    companion object { // singleton
        @Volatile
        private var instance: RelatorioAplRepository? = null

        //Singleton instance
        fun getInstance(): RelatorioAplRepository {
            return instance ?: synchronized(this) {
                RelatorioAplRepository().also { instance = it }
            }
        }
    }

    private val dao = RelatorioDatabase.relatorioAplDao()

    private val mediador = MediatorLiveData<ResourceC<List<RelatorioAplicacaoEntity>?>>()

    fun buscaTodos(): LiveData<ResourceC<List<RelatorioAplicacaoEntity>?>> {
        mediador.addSource(buscaInterno(), Observer { relatorios ->
            mediador.value = ResourceC(dado = relatorios)
        })
        return mediador
    }

    private fun buscaInterno(): LiveData<List<RelatorioAplicacaoEntity>> {
        return dao.all()
    }

}