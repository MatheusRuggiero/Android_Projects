package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.IdiomaEntity

@Dao
interface IdiomaDao : BaseDao<IdiomaEntity> {
    @Query("SELECT * FROM IDIOMA")
    fun all(): LiveData<MutableList<IdiomaEntity>>
}