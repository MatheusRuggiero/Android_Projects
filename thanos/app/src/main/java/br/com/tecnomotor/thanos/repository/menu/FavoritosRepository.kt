package br.com.tecnomotor.thanos.repository.menu

import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.model.cliente.Favorito
import br.com.tecnomotor.thanos.model.menu.Aplicacao
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritosRepository {
    companion object { // singleton
        @Volatile
        private var instance: FavoritosRepository? = null

        //Singleton instance
        fun getInstance(): FavoritosRepository {
            return instance ?: synchronized(this) {
                FavoritosRepository().also { instance = it }
            }
        }
    }

    private val dao = ClienteDatabase.getInstance().favoritosDao()

    fun getFavoritos() = dao.getFavorito()

    @DelicateCoroutinesApi
    fun deletar(favorito: Favorito) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deletaFavorito(favorito.toFavoritoEntity())
        }
    }

    @DelicateCoroutinesApi
    fun inserir(favorito: Favorito) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.insereFavorito(favorito.toFavoritoEntity())
        }
    }

    fun qtdFavoritos() = dao.qtdFavoritos()

    fun isExist(favorito: Favorito) = dao.isExist(favorito.aplicacaoId)

    fun getFavoritoByAplicacao(aplicacao: Aplicacao) = dao.getFavoritoByAplId(aplicacao.id)
}
