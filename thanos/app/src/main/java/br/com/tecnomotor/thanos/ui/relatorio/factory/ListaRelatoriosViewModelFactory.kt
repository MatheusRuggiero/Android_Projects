package br.com.tecnomotor.thanos.ui.relatorio.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.repository.cliente.RelatorioAplRepository
import br.com.tecnomotor.thanos.ui.relatorio.viewmodel.ListaRelatoriosViewModel

class ListaRelatoriosViewModelFactory(
    private val repository: RelatorioAplRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return ListaRelatoriosViewModel(repository) as T
    }
}