package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.repository.menu.TipoSistemasRepository
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class TipoSistemasViewModel: ViewModel() {
    private val tipoSistemasRepository = TipoSistemasRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()

    fun getTipoDeSistemas(plataforma: Plataforma, versao: Versao, montadora: Montadora, veiculo: Veiculo,
                          motorizacao: Motorizacao) = tipoSistemasRepository.getTipoSistemas(
        plataforma.id, versao.id, plataforma.versaoHabilitada, montadora.id, veiculo.id, motorizacao.id)

    fun getVersaoByTipoDeSistemas(plataforma: Plataforma, montadora: Montadora, veiculo: Veiculo,
                                  motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema) =
        versaoRepository.getVersaoByTipoDeSistemas(plataforma.id, montadora.id, veiculo.id,
            motorizacao.id, tipoDeSistema.id)
}