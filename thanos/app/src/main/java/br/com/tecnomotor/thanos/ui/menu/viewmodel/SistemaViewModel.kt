package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.repository.menu.SistemaRepository
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class SistemaViewModel: ViewModel() {
    private val sistemasRepository = SistemaRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()

    fun getSistemas(plataforma: Plataforma, versao: Versao, montadora: Montadora, veiculo: Veiculo,
                    motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema) =
        sistemasRepository.getSistemas(plataforma.id, versao.id, plataforma.versaoHabilitada,
            montadora.id, veiculo.id, motorizacao.id, tipoDeSistema.id)

    fun getVersaoBySistemas(plataforma: Plataforma, montadora: Montadora, veiculo: Veiculo,
                            motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema, sistema: Sistema) =
        versaoRepository.getVersaoBySistemas(plataforma.id, montadora.id, veiculo.id, motorizacao.id,
            tipoDeSistema.id, sistema.id, sistema.anoInicial, sistema.anoFinal)
}