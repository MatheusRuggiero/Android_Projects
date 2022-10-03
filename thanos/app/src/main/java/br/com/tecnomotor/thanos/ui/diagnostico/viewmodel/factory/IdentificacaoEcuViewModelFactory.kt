package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.repository.diagnostico.IdentificacaoEcuRepository
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.IdentificacaoEcuViewModel

class IdentificacaoEcuViewModelFactory(private val ecuRepository: IdentificacaoEcuRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IdentificacaoEcuViewModel(ecuRepository) as T
    }
}