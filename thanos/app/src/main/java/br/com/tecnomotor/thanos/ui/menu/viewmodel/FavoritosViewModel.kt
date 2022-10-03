package br.com.tecnomotor.thanos.ui.menu.viewmodel

import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.model.cliente.Favorito
import br.com.tecnomotor.thanos.repository.menu.FavoritosRepository
import kotlinx.coroutines.DelicateCoroutinesApi

class FavoritosViewModel : ViewModel() {
    private val repository = FavoritosRepository.getInstance()

    fun getFavoritos() = repository.getFavoritos()

    @DelicateCoroutinesApi
    fun insereFavorito(favorito: Favorito) {
        repository.inserir(favorito)
    }
    @DelicateCoroutinesApi
    fun deletaFavorito(favorito: Favorito) {
        repository.deletar(favorito)
    }
}
