package br.com.tecnomotor.thanos.repository.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.model.cliente.toHistorico
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoricoRepository() {
    companion object { // singleton
        @Volatile
        private var instance: HistoricoRepository? = null

        //Singleton instance
        fun getInstance(): HistoricoRepository {
            return instance ?: synchronized(this) {
                HistoricoRepository().also { instance = it }
            }
        }
    }

    private val dao = ClienteDatabase.getInstance().historicosDao()

    fun getHistoricos() = dao.getHistorico()

    @DelicateCoroutinesApi
    fun deletaHistorico(historico: Historico) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deletaHistorico(historico.toHistoricoEntity())
        }
    }


    fun ultimoHistorico() : LiveData<Historico> {
        val source = MutableLiveData<Historico>()
         dao.getUltimoHistoricoInserido().observeForever { historico ->
            source.postValue(historico.toHistorico())
         }
        return source
    }

    @DelicateCoroutinesApi
    fun inserirHistorico(historico: Historico){
        GlobalScope.launch(Dispatchers.IO) {
            dao.insereHistorico(historico.toHistoricoEntity())
        }
    }

    fun qtdHistoricos() = dao.qtdHistoricos()
}