package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.FavoritoEntity

@Dao
interface FavoritosDao : BaseDao<FavoritoEntity> {
    @Query("SELECT * FROM favorito")
    fun getFavorito(): LiveData<MutableList<FavoritoEntity>>

    @Delete
    fun deletaFavorito(favoritoEntity: FavoritoEntity)

    @Insert
    fun insereFavorito(favoritesEntity: FavoritoEntity)

    @Query("SELECT * FROM FAVORITO F WHERE (F.favaplid=:aplId) LIMIT 1")
    fun getFavoritoByAplId(aplId: Long) : LiveData<FavoritoEntity?>

    @Query("SELECT COUNT(id) FROM favorito")
    fun qtdFavoritos() : LiveData<Int>

    @Query("SELECT (COUNT(id) > 0) as isExist FROM FAVORITO F WHERE (F.favaplid=:aplId)")
    fun isExist(aplId: Long) : LiveData<Boolean>
}