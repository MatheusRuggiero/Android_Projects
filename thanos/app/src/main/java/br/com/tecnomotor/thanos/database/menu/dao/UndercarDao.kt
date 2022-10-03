package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.UndercarEntity

@Dao
interface UndercarDao : BaseDao<UndercarEntity> {
    @Query("SELECT * FROM UNDERCAR")
    fun all(): LiveData<MutableList<UndercarEntity>>
}