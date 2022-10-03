package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.repository.diagnostico.LeiturasRepositry
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.LeiturasViewModel

class LeiturasViewModelFactory (

    val leiturasRepository: LeiturasRepositry

): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LeiturasViewModel(leiturasRepository) as T
    }
}