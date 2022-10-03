package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.model.menu.Plataforma
import br.com.tecnomotor.thanos.model.menu.Veiculo
import br.com.tecnomotor.thanos.model.menu.Versao
import br.com.tecnomotor.thanos.repository.menu.VeiculoRepository
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class VeiculoViewModel() : ViewModel() {
    private val veiculoRepository = VeiculoRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()

    fun getVeiculos(plataforma: Plataforma, versao: Versao, montadora: Montadora) =
        veiculoRepository.getVeiculos(
            plataforma.id, versao.id, plataforma.versaoHabilitada, montadora.id)

    fun getVersaoByVeiculo(plataforma: Plataforma, montadora: Montadora, veiculo: Veiculo) =
        versaoRepository.getVersaoByVeiculo(plataforma.id, montadora.id, veiculo.id)
}