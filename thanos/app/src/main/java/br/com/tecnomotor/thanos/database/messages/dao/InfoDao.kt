package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.InfoEntity

@Dao
interface InfoDao : BaseDao<InfoEntity> {
    @Query("SELECT * FROM INFO_TAB")
    fun all(): LiveData<MutableList<InfoEntity>>
}