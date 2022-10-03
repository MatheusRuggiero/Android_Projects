package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.repository.menu.ConectorRepository
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class ConectorViewModel: ViewModel() {
    private val conectorRepository = ConectorRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()

    fun getConectores(plataforma: Plataforma, versao: Versao, montadora: Montadora, veiculo: Veiculo,
                      motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema,sistema: Sistema) =
        conectorRepository.getConectores(plataforma.id, versao.id, plataforma.versaoHabilitada,
            montadora.id, veiculo.id, motorizacao.id, tipoDeSistema.id, sistema.id,
            sistema.anoInicial, sistema.anoFinal)

    fun getVersaoByConector(plataforma: Plataforma, montadora: Montadora, veiculo: Veiculo,
                            motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema,
                            sistema: Sistema, conector: Conector) =
        versaoRepository.getVersaoByConector(plataforma.id, montadora.id, veiculo.id,
            motorizacao.id, tipoDeSistema.id, sistema.id, sistema.anoInicial, sistema.anoFinal, conector.id)
}