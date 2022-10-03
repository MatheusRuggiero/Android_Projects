package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.repository.menu.MotorizacaoRepository
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class MotorizacaoViewModel: ViewModel() {
    private val motorizacaoRepository = MotorizacaoRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()

    fun getMotorizacoes(plataforma: Plataforma, versao: Versao, montadora: Montadora, veiculo: Veiculo) =
        motorizacaoRepository.getMotorizacoes(plataforma.id, versao.id, plataforma.versaoHabilitada, montadora.id, veiculo.id)

    fun getVersaoByMotorizacao(plataforma: Plataforma, montadora: Montadora, veiculo: Veiculo,
                               motorizacao: Motorizacao) =
        versaoRepository.getVersaoByMotorizacao(plataforma.id, montadora.id, veiculo.id, motorizacao.id)
}