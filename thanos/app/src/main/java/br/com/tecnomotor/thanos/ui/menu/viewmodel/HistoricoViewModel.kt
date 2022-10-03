package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.repository.menu.HistoricoRepository
import kotlinx.coroutines.DelicateCoroutinesApi

class HistoricoViewModel: ViewModel() {
    private val repository = HistoricoRepository.getInstance()

    fun getHistoricos() = repository.getHistoricos()

    @DelicateCoroutinesApi
    fun insereHistorico(historico: Historico) {
        repository.inserirHistorico(historico)
    }
    @DelicateCoroutinesApi
    fun deletaHistorico(historico: Historico) {
        repository.deletaHistorico(historico)
    }

    fun getUltimoHistorico() = repository.ultimoHistorico()
}