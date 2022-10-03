package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.cliente.Favorito
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.repository.menu.AplicacaoRepository
import br.com.tecnomotor.thanos.repository.menu.FavoritosRepository
import br.com.tecnomotor.thanos.repository.menu.HistoricoRepository
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class AplicacaoConectorViewModel (): ViewModel() {
    private val aplicacaoRepository = AplicacaoRepository.getInstance()
    private val historicoRepository = HistoricoRepository.getInstance()
    private val favoritosRepository = FavoritosRepository.getInstance()


    fun insertHistorico(historico: Historico) {
        return historicoRepository.inserirHistorico(historico)
    }

    fun insertFavorito(favorito: Favorito) {
        return favoritosRepository.inserir(favorito)
    }

    fun getAplicacao(plataforma: Plataforma,  versao: Versao, montadora: Montadora, veiculo: Veiculo,
                     motorizacao: Motorizacao, tipoDeSistema: TipoDeSistema, sistema: Sistema,conector: Conector) =
        aplicacaoRepository.getAplicacao(plataforma.id, versao.id, plataforma.versaoHabilitada, montadora.id,
            veiculo.id, motorizacao.id, tipoDeSistema.id, sistema.id, sistema.anoInicial, sistema.anoFinal, conector.id)

}