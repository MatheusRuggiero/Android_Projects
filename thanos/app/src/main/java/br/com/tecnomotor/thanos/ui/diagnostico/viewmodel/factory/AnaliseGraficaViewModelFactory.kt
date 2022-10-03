package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.repository.diagnostico.AnaliseGraficaRepository
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.AnaliseGraficaViewModel

class AnaliseGraficaViewModelFactory(
    private val AnaliseGraficaRepository: AnaliseGraficaRepository

): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  AnaliseGraficaViewModel(AnaliseGraficaRepository) as T
    }
}