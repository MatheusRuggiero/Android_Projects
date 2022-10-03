package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.model.menu.Plataforma
import br.com.tecnomotor.thanos.model.menu.Versao
import br.com.tecnomotor.thanos.repository.menu.FavoritosRepository
import br.com.tecnomotor.thanos.repository.menu.HistoricoRepository
import br.com.tecnomotor.thanos.repository.menu.MontadoraRepository
import br.com.tecnomotor.thanos.repository.menu.VersaoRepository

class MontadoraViewModel(): ViewModel() {
    private val montadoraRepository = MontadoraRepository.getInstance()
    private val versaoRepository = VersaoRepository.getInstance()
    private val historicoRepository = HistoricoRepository()
    private val favoritosRepository = FavoritosRepository()

    fun getMontadoras(plataforma: Plataforma, versao: Versao) =
        montadoraRepository.getMontadoras(plataforma.id, versao.id, plataforma.versaoHabilitada)

    fun getVersaoByMontadora(plataforma: Plataforma, montadora: Montadora) =
        versaoRepository.getVersaoByMontadora(plataforma.id, montadora.id)

    fun getLasHistory() = historicoRepository.ultimoHistorico()

    fun qtdHistoricos() = historicoRepository.qtdHistoricos()

    fun qtdFavoritos() = favoritosRepository.qtdFavoritos()
}